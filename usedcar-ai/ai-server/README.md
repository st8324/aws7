# ai-server (FastAPI)

Spring(8080)이 부르는 AI 서버. 명세: 노션 「중고차 AI 서비스: FastAPI 명세」

지금은 **0단계** 상태다. 네 API가 모두 고정값으로 응답한다.
단계마다 `TODO(1단계)`, `TODO(2단계)`, `TODO(3단계)` 자리를 채운다. 주소와 JSON 모양은 바꾸지 않는다.

## 처음 설정 (한 번만)

Python 3.11 이상이 필요하다.

```bash
cd ai-server

# 가상환경 만들기
python -m venv .venv

# 가상환경 켜기
.venv\Scripts\activate          # Windows
source .venv/bin/activate       # Mac

# 패키지 설치
pip install -r requirements.txt

# .env 만들기 (3단계 API 키를 넣을 파일)
copy .env.example .env          # Windows
cp .env.example .env            # Mac
```

## 실행

```bash
uvicorn main:app --reload --port 8000
```

- http://localhost:8000/docs 에서 React 없이 API를 바로 시험할 수 있다
- `--reload`: 코드를 저장하면 서버가 알아서 다시 켜진다

## 폴더 구조

```
ai-server/
├─ main.py            앱 만들기, 라우터 등록, 서버 켜질 때 모델 불러오기, /health
├─ schemas.py         요청, 응답 모양 (Pydantic). 명세와 같아야 한다
├─ routers/
│  ├─ price.py        1단계 시세 예측     POST /price/predict
│  ├─ car_type.py     2단계 사진 분류     POST /car-type/classify
│  └─ listing.py      3단계 판매글        POST /listing/write
├─ models/            학습된 모델 파일 (price_model.joblib)
├─ notebooks/         학습용 노트북 (서버와 분리)
├─ data/              used_cars.csv 를 여기에 넣는다
├─ requirements.txt   패키지 버전 고정
├─ .env.example       .env 견본
└─ .env               API 키 (직접 만든다, git에 올리지 않는다)
```

## 라우터 파일 하나의 모양

세 라우터가 모두 같은 모양이다.

| 함수 | 언제 실행되나 | 하는 일 |
| --- | --- | --- |
| `load()` | 서버가 켜질 때 한 번 (`main.py`의 `lifespan`) | 모델, API 클라이언트 불러오기 |
| `is_ready()` | `/health` 요청마다 | 이 기능을 쓸 수 있으면 `True` |
| `predict()` 등 | 요청마다 | 불러온 모델로 답하기 |

- 모델은 `load()`에서 **한 번만** 불러온다. 요청마다 불러오면 느려서 Spring이 기다리다 포기한다
- 지금은 `is_ready()`가 모두 `True`다. 진짜 모델로 바꿀 때 `return model is not None`으로 고친다

## 0단계에서 확인할 것

1. 세 서버를 모두 켜고 http://localhost:3000 을 연다
2. 상태 표시줄에 `AI 서버 연결됨`, 세 기능 모두 `사용 가능`이 보인다
3. 시세 예측 → `1,000만 원`, 사진 분류 → `SUV`, 판매글 → 고정 형식 글이 나온다
4. `/docs`에서 연식을 30으로 보내 본다 → 422. Pydantic이 범위를 검사한다

## 진행 순서

| 단계 | 파일 | 할 일 |
| --- | --- | --- |
| 1 | `notebooks/` → `models/` → `routers/price.py` | 시세 모델 학습, `Pipeline`으로 저장, 불러와서 예측 |
| 2 | `routers/car_type.py` | 사전학습 이미지 모델 불러와서 분류 |
| 3 | `.env`, `routers/listing.py` | API 키 넣고, 프롬프트 만들어 LLM 호출 |
