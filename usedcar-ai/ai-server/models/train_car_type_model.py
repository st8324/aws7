"""
2단계: 중고차 사진으로 차종(경차 / 세단 / SUV) 분류 - 전이학습 (딥러닝)

실행 (ai-server 폴더에서):
    python models/collect_car_images.py     # 1) 사진 모으기 (한 번만)
    python models/train_car_type_model.py   # 2) 학습 → models/car_type_model.pt

전이학습 (Transfer Learning)
  ImageNet(사진 약 128만 장, 1000종류)으로 이미 학습된 EfficientNet-B0를 가져온다.
  이 모델은 '바퀴, 창문, 차체 윤곽' 같은 그림 특징을 이미 볼 줄 안다.
  우리는 마지막 분류층만 1000종류 → 3종류(경차, 세단, SUV)로 바꿔서 다시 가르친다.

  1단계 특징 추출 (feature extraction)
      앞부분(features)을 얼려(freeze) 두고 새 분류층만 학습한다.
      학습할 값이 3,843개뿐이라 CPU에서도 빠르고, 사진이 수백 장이어도 된다.
  2단계 미세 조정 (fine-tuning)
      마지막 블록 3개를 풀어서 아주 작은 학습률로 같이 학습한다. (학습하는 값 약 316만 개)
      ImageNet 특징을 '차 모양 구분'에 조금 더 맞춘다. 학습률이 크면 배운 것을 망가뜨린다.
"""

import copy
import json
import time
from pathlib import Path

import torch
from PIL import Image
from torch import nn
from torch.utils.data import DataLoader
from torchvision import datasets, transforms
from torchvision.models import EfficientNet_B0_Weights, efficientnet_b0

LABELS = ["경차", "세단", "SUV"]           # 응답에 나가는 이름 (schemas.py 의 CarType)
FOLDERS = ["light", "sedan", "suv"]        # data/car_images 안의 폴더 이름 (LABELS 와 같은 순서)

# models 폴더의 위치
BASE_DIR = Path(__file__).resolve().parent
# 학습할 사진 위치 (collect_car_images.py 가 만든다)
DATA_DIR = BASE_DIR.parent / "data" / "car_images"
# 저장할 모델 위치 및 파일명
MODEL_PATH = BASE_DIR / "car_type_model.pt"
# 평가 결과 (정확도, 혼동 행렬)
METRICS_PATH = BASE_DIR / "car_type_metrics.json"

IMAGE_SIZE = 224
# ImageNet으로 학습할 때 쓴 평균, 표준편차. 사전학습 모델에는 같은 기준으로 바꿔서 넣어야 한다
MEAN = [0.485, 0.456, 0.406]
STD = [0.229, 0.224, 0.225]

HEAD_EPOCHS = 6        # 1단계: 분류층만
HEAD_LR = 1e-3
FINE_EPOCHS = 10       # 2단계: 마지막 블록 3개 + 분류층
FINE_LR = 1e-4         # 1단계보다 10배 작게 시작해서, 바퀴마다 점점 줄인다 (코사인 스케줄)
UNFREEZE_BLOCKS = 3
BATCH_SIZE = 32
SEED = 42

# 학습용: 사진을 조금씩 바꿔서 보여 준다 (데이터 증강). 사진 수가 적을 때 외우는 것을 막는다
train_transform = transforms.Compose([
    transforms.RandomResizedCrop(IMAGE_SIZE, scale=(0.5, 1.0)),  # 일부만 잘라 보기, 크기 바꾸기
    transforms.RandomHorizontalFlip(),                            # 좌우 반전: 반전해도 같은 차종
    transforms.ColorJitter(brightness=0.3, contrast=0.3, saturation=0.3),  # 차 색, 날씨, 밤낮
    transforms.ToTensor(),
    transforms.Normalize(MEAN, STD),
])
# 검증, 시험, 서버 예측용: 항상 같은 모양으로만 바꾼다
eval_transform = transforms.Compose([
    transforms.Resize(IMAGE_SIZE),        # 짧은 쪽을 224로
    transforms.CenterCrop(IMAGE_SIZE),    # 가운데 224 x 224
    transforms.ToTensor(),
    transforms.Normalize(MEAN, STD),
])


def load_data():
    """1. 데이터 선택: 폴더 이름이 곧 정답(라벨)이다. train/light/*.jpg → 경차"""
    sets = {
        "train": datasets.ImageFolder(DATA_DIR / "train", transform=train_transform),
        "val": datasets.ImageFolder(DATA_DIR / "val", transform=eval_transform),
        "test": datasets.ImageFolder(DATA_DIR / "test", transform=eval_transform),
    }
    for name, ds in sets.items():
        # ImageFolder는 폴더 이름 가나다(abc)순으로 번호를 붙인다. 순서가 LABELS와 같은지 확인
        assert ds.classes == FOLDERS, f"{name} 폴더 구성이 다릅니다: {ds.classes}"
    loaders = {
        name: DataLoader(ds, batch_size=BATCH_SIZE, shuffle=(name == "train"))
        for name, ds in sets.items()
    }
    return sets, loaders


def build_model(pretrained=True):
    """2. 모델 만들기: ImageNet 사전학습 EfficientNet-B0 + 새 분류층(1280 → 3)"""
    weights = EfficientNet_B0_Weights.IMAGENET1K_V1 if pretrained else None
    model = efficientnet_b0(weights=weights)
    # 원래 분류층: Dropout → Linear(1280 → 1000). 1000종류 대신 우리 3종류로 바꾼다
    in_features = model.classifier[1].in_features
    model.classifier = nn.Sequential(
        nn.Dropout(p=0.3),
        nn.Linear(in_features, len(LABELS)),
    )
    return model


def freeze_backbone(model):
    """특징 추출기(features) 전체를 얼린다. 분류층만 학습된다"""
    for p in model.features.parameters():
        p.requires_grad = False


def unfreeze_last_blocks(model, n):
    """특징 추출기의 마지막 n개 블록을 다시 학습할 수 있게 푼다"""
    for block in model.features[-n:]:
        for p in block.parameters():
            p.requires_grad = True


def set_train_mode(model):
    """학습 모드. 단, 얼린 블록은 BatchNorm 통계도 ImageNet 값 그대로 둔다"""
    model.train()
    for block in model.features:
        if not any(p.requires_grad for p in block.parameters()):
            block.eval()


def count_trainable(model):
    return sum(p.numel() for p in model.parameters() if p.requires_grad)


def run_epoch(model, loader, criterion, optimizer=None):
    """한 바퀴. optimizer가 있으면 학습, 없으면 평가만"""
    training = optimizer is not None
    set_train_mode(model) if training else model.eval()
    total, correct, loss_sum = 0, 0, 0.0
    with torch.set_grad_enabled(training):
        for x, y in loader:
            out = model(x)
            loss = criterion(out, y)
            if training:
                optimizer.zero_grad()
                loss.backward()
                optimizer.step()
            loss_sum += loss.item() * len(y)
            correct += (out.argmax(dim=1) == y).sum().item()
            total += len(y)
    return loss_sum / total, correct / total


def fit(model, loaders, criterion, optimizer, epochs, stage, history, best, scheduler=None):
    """여러 바퀴 학습하고, 검증 정확도가 가장 좋았던 상태를 best에 기억한다"""
    for epoch in range(1, epochs + 1):
        start = time.time()
        tr_loss, tr_acc = run_epoch(model, loaders["train"], criterion, optimizer)
        va_loss, va_acc = run_epoch(model, loaders["val"], criterion)
        if scheduler is not None:
            scheduler.step()
        history.append({"stage": stage, "epoch": epoch, "train_loss": round(tr_loss, 4),
                        "train_acc": round(tr_acc, 4), "val_loss": round(va_loss, 4),
                        "val_acc": round(va_acc, 4)})
        mark = ""
        if va_acc > best["val_acc"]:
            best.update(val_acc=va_acc, state=copy.deepcopy(model.state_dict()), stage=stage, epoch=epoch)
            mark = "  ← 지금까지 최고"
        print(f"  [{stage}] {epoch}/{epochs}  train loss {tr_loss:.3f} acc {tr_acc:.3f}"
              f" | val loss {va_loss:.3f} acc {va_acc:.3f}  ({time.time() - start:.0f}초){mark}")


def train(sets, loaders):
    """3. 학습: 1단계(분류층만) → 2단계(마지막 블록까지)"""
    torch.manual_seed(SEED)
    model = build_model(pretrained=True)

    # 차종별 사진 수가 조금 달라도 한쪽으로 쏠리지 않게, 적은 차종의 오답에 더 큰 벌점을 준다
    counts = torch.bincount(torch.tensor(sets["train"].targets), minlength=len(LABELS)).float()
    class_weight = counts.sum() / (len(LABELS) * counts)
    criterion = nn.CrossEntropyLoss(weight=class_weight, label_smoothing=0.1)

    history = []
    best = {"val_acc": -1.0}

    # 1단계: 특징 추출 - 특징 추출기는 얼리고 분류층만 학습
    freeze_backbone(model)
    print(f"\n1단계 특징 추출: 학습하는 값 {count_trainable(model):,}개 "
          f"(전체 {sum(p.numel() for p in model.parameters()):,}개)")
    optimizer = torch.optim.AdamW(model.classifier.parameters(), lr=HEAD_LR)
    fit(model, loaders, criterion, optimizer, HEAD_EPOCHS, "특징추출", history, best)

    # 2단계: 미세 조정 - 마지막 블록 몇 개를 풀고 작은 학습률로
    unfreeze_last_blocks(model, UNFREEZE_BLOCKS)
    print(f"\n2단계 미세 조정: 학습하는 값 {count_trainable(model):,}개")
    optimizer = torch.optim.AdamW([p for p in model.parameters() if p.requires_grad],
                                  lr=FINE_LR, weight_decay=1e-4)
    scheduler = torch.optim.lr_scheduler.CosineAnnealingLR(optimizer, T_max=FINE_EPOCHS)
    fit(model, loaders, criterion, optimizer, FINE_EPOCHS, "미세조정", history, best, scheduler)

    model.load_state_dict(best["state"])
    print(f"\n가장 좋았던 상태: {best['stage']} {best['epoch']}번째 (검증 정확도 {best['val_acc']:.3f})")
    return model, history, best


def evaluate(model, loader):
    """4. 평가: 한 번도 보지 않은 시험(test) 사진으로 정확도와 혼동 행렬을 본다"""
    from sklearn.metrics import classification_report, confusion_matrix

    model.eval()
    y_true, y_pred = [], []
    with torch.inference_mode():
        for x, y in loader:
            y_true += y.tolist()
            y_pred += model(x).argmax(dim=1).tolist()

    acc = sum(t == p for t, p in zip(y_true, y_pred)) / len(y_true)
    cm = confusion_matrix(y_true, y_pred, labels=range(len(LABELS)))
    report = classification_report(y_true, y_pred, labels=range(len(LABELS)), target_names=LABELS,
                                   output_dict=True, zero_division=0)
    print(f"\n시험 정확도: {acc:.3f}  ({len(y_true)}장)")
    print("혼동 행렬 (행: 정답, 열: 예측)")
    print("          " + "".join(f"{label:>6}" for label in LABELS))
    for label, row in zip(LABELS, cm):
        print(f"  {label:>6}  " + "".join(f"{v:>6}" for v in row))
    for label in LABELS:
        r = report[label]
        print(f"  {label}: 정밀도 {r['precision']:.2f}, 재현율 {r['recall']:.2f}, f1 {r['f1-score']:.2f}")
    return {"accuracy": round(acc, 4), "confusion_matrix": cm.tolist(),
            "per_class": {label: {k: round(v, 4) for k, v in report[label].items()} for label in LABELS}}


def predict_car_type(model, image):
    """5. 예측: 사진 한 장(PIL.Image) → {"경차": 0.05, "세단": 0.08, "SUV": 0.87}"""
    x = eval_transform(image.convert("RGB")).unsqueeze(0)  # 1장짜리 묶음(batch)으로 만든다
    with torch.inference_mode():
        prob = model(x).softmax(dim=1)[0]
    return {label: round(float(p), 4) for label, p in zip(LABELS, prob)}


def save(model, extra=None):
    """저장하기: 가중치(state_dict)와 라벨 순서를 함께 저장한다"""
    torch.save({"arch": "efficientnet_b0", "labels": LABELS, "image_size": IMAGE_SIZE,
                "state_dict": model.state_dict(), **(extra or {})}, MODEL_PATH)


def load():
    """불러오기: 저장된 모델이 없으면 None. 서버(routers/car_type.py)가 이 함수를 쓴다"""
    if not MODEL_PATH.exists():
        return None
    ckpt = torch.load(MODEL_PATH, map_location="cpu", weights_only=True)
    if ckpt.get("labels") != LABELS:
        raise ValueError(f"모델의 라벨 순서가 다릅니다: {ckpt.get('labels')}")
    # 구조만 만들고(인터넷에서 ImageNet 가중치를 받지 않는다) 우리가 학습한 가중치를 넣는다
    model = build_model(pretrained=False)
    model.load_state_dict(ckpt["state_dict"])
    model.eval()
    return model


def main():
    start = time.time()
    # 1. 데이터 선택
    sets, loaders = load_data()
    for name, ds in sets.items():
        counts = torch.bincount(torch.tensor(ds.targets), minlength=len(LABELS)).tolist()
        print(f"{name:>5}: {len(ds):>4}장  " + ", ".join(f"{l} {c}" for l, c in zip(LABELS, counts)))
    # 2~3. 모델 만들기, 학습
    model, history, best = train(sets, loaders)
    # 4. 평가
    test = evaluate(model, loaders["test"])
    # 저장
    save(model, {"val_acc": round(best["val_acc"], 4), "test_acc": test["accuracy"]})
    metrics = {
        "model": "EfficientNet-B0 (ImageNet-1K 사전학습) + 전이학습",
        "data": {name: len(ds) for name, ds in sets.items()},
        "best": {"stage": best["stage"], "epoch": best["epoch"], "val_acc": round(best["val_acc"], 4)},
        "test": test,
        "history": history,
        "train_seconds": round(time.time() - start),
    }
    METRICS_PATH.write_text(json.dumps(metrics, ensure_ascii=False, indent=2), encoding="utf-8")
    print(f"\n저장 완료: {MODEL_PATH.name}, {METRICS_PATH.name}  (총 {time.time() - start:.0f}초)")

    # 5. 예측: 저장한 파일을 다시 불러와서 시험 사진 몇 장으로 확인
    print("\n........테스트........")
    loaded = load()
    for folder in FOLDERS:
        path = next((DATA_DIR / "test" / folder).glob("*.jpg"))
        scores = predict_car_type(loaded, Image.open(path))
        best_label = max(scores, key=scores.get)
        print(f"  {path.name:<28} 정답 {LABELS[FOLDERS.index(folder)]:<3} → 예측 {best_label:<3} {scores}")


if __name__ == "__main__":
    main()