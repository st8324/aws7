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
from models import train_price_model

import joblib
import pandas as pd


router = APIRouter(prefix="/price", tags=["1 시세 예측"])

MODEL_PATH = Path(__file__).resolve().parent.parent / "models" / "price_model.joblib"
SCALER_PATH = Path(__file__).resolve().parent.parent / "models" / "price_scaler.joblib"

model = None  # 서버가 켜질 때 load()가 채운다
scaler = None # 서버가 켜질 때 load()가 채운다

def load():
    """서버가 켜질 때 main.py가 한 번 부른다"""
    global model, scaler

    if MODEL_PATH.exists():
        model = joblib.load(MODEL_PATH)
    if SCALER_PATH.exists():
        scaler = joblib.load(SCALER_PATH)


def is_ready() -> bool:
    """/health에 보낼 값. True면 화면에서 이 기능이 열린다"""
    return model is not None and scaler is not None
    

# 부트가 /price/predict 요청하면(post) 예측된 시세를 반환
@router.post("/predict", response_model=PriceResponse)
def predict(car: CarInfo):

    if model is None or scaler is None:
        raise HTTPException(status_code=503, detail="시세 모델이 아직 준비되지 않았습니다")
    # from models import train_price_model
    pred = train_price_model.predict_price(model, scaler, car.model_dump())
    return PriceResponse(price=round(pred))

