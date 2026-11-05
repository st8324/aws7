"""
1단계: 시세 예측 (머신러닝)

POST /price/predict
  요청: { "car_type": "SUV", "year_age": 3, "mileage_km": 40000, "engine_cc": 2500, "accident": 0 }
  응답: { "price": 1850 }

지금은 고정값(1000)을 돌려준다. 화면에 1,000만 원이 뜨면 연결 성공이다.
"""

from pathlib import Path

from fastapi import APIRouter, HTTPException

from schemas import CarInfo, PriceResponse

router = APIRouter(prefix="/price", tags=["1 시세 예측"])

MODEL_PATH = Path(__file__).resolve().parent.parent / "models" / "price_model.joblib"

model = None  # 서버가 켜질 때 load()가 채운다


def load():
    """서버가 켜질 때 main.py가 한 번 부른다"""
    global model
    # TODO(1단계): notebooks/에서 학습하고 저장한 모델을 불러온다
    #   import joblib
    #   if MODEL_PATH.exists():
    #       model = joblib.load(MODEL_PATH)


def is_ready() -> bool:
    """/health에 보낼 값. True면 화면에서 이 기능이 열린다"""
    # 0단계: 고정값으로라도 응답하니까 True
    # TODO(1단계): return model is not None
    return True


@router.post("/predict", response_model=PriceResponse)
def predict(car: CarInfo):
    # TODO(1단계): 고정값 대신 모델로 예측한다
    #   if model is None:
    #       raise HTTPException(status_code=503, detail="시세 모델이 아직 준비되지 않았습니다")
    #   X = pd.DataFrame([car.model_dump()])   # 학습 때와 같은 열 이름, 같은 사전 준비
    #   pred = model.predict(X)[0]
    #   return PriceResponse(price=round(pred))
    return PriceResponse(price=1000)

