"""
2단계: 사진으로 차종 분류 (딥러닝)

POST /car-type/classify   (multipart/form-data, 필드 이름: file)
  응답: { "car_type": "SUV", "confidence": 0.87, "scores": { "경차": 0.05, "세단": 0.08, "SUV": 0.87 } }

지금은 사진을 열어 보기만 하고 고정값을 돌려준다.
"""
# pip install torch, torchvision

import io

from fastapi import APIRouter, File, HTTPException, UploadFile
from PIL import Image, UnidentifiedImageError

from schemas import CarTypeResponse

from models import train_car_type_model
from fastapi.concurrency import run_in_threadpool

router = APIRouter(prefix="/car-type", tags=["2 사진 분류"])

LABELS = ["경차", "세단", "SUV"]
ALLOWED_TYPES = {"image/jpeg", "image/png"}

model = None  # 서버가 켜질 때 load()가 채운다


def load():
    """서버가 켜질 때 main.py가 한 번 부른다"""
    global model
    model = train_car_type_model.load()


def is_ready() -> bool:
    # 0단계: 고정값으로라도 응답하니까 True
    return model is not None
    

def read_image(data: bytes) -> Image.Image:
    """받은 파일이 정말 이미지인지 열어 본다. 아니면 400"""
    try:
        image = Image.open(io.BytesIO(data))
        image.load()
    except (UnidentifiedImageError, OSError):
        raise HTTPException(status_code=400, detail="이미지 파일을 열 수 없습니다")
    return image.convert("RGB")


@router.post("/classify", response_model=CarTypeResponse)
async def classify(file: UploadFile = File(...)):
    if file.content_type not in ALLOWED_TYPES:
        raise HTTPException(status_code=400, detail="jpg 또는 png 사진만 올릴 수 있습니다")

    if model is None:
        raise HTTPException(status_code=503, detail="차종 분류 모델이 아직 준비되지 않았습니다.")
    if file.content_type not in ALLOWED_TYPES:
        raise HTTPException(status_code=400, detail="jpg 또는 png 사진만 올 수 있습니다.")

    image = read_image(await file.read())

    # TODO(2단계): 고정값 대신 모델로 분류한다
    scores = await run_in_threadpool(train_car_type_model.predict_car_type, model, image)
    best = max(scores, key=scores.get)
    return CarTypeResponse(car_type=best, confidence=scores[best], scores=scores)
    
