"""
3단계: 판매글 작성 (AI)

POST /listing/write
  요청: 차 정보 5개 + price + memo + tone
  응답: { "title": "...", "body": "..." }

지금은 받은 값으로 문장을 이어 붙인 고정 형식 글을 돌려준다.
"""

import os
import common as c
import joblib
from fastapi import APIRouter, HTTPException
from schemas import ListingRequest, ListingResponse, CarInfo, CarInfoExtract
from datetime import datetime
from pathlib import Path
from models import train_price_model
from pydantic import ValidationError

router = APIRouter(prefix="/listing", tags=["3 판매글"])

MODEL_PATH = Path(__file__).resolve().parent.parent / "models" / "price_model.joblib"
SCALER_PATH = Path(__file__).resolve().parent.parent / "models" / "price_scaler.joblib"

client = None  # 서버가 켜질 때 load()가 채운다 (LLM API 클라이언트)
model = None  # 서버가 켜질 때 load()가 채운다
scaler = None # 서버가 켜질 때 load()가 채운다

def load():
    """서버가 켜질 때 main.py가 한 번 부른다"""
    global client, model, scaler
    client = c.client
    if MODEL_PATH.exists():
        model = joblib.load(MODEL_PATH)
    if SCALER_PATH.exists():
        scaler = joblib.load(SCALER_PATH)


def is_ready() -> bool:
    # 0단계: 고정값으로라도 응답하니까 True
    return client is not None  # (키가 없으면 False → 화면에 "준비 중")
    


@router.post("/write", response_model=ListingResponse)
def write(req: ListingRequest):
    SYSTEM = """너는 중고차 판매글 작성 전문가야.
    - 차 정보, 가격, 메모, 말투를 참고해서 판매글을 작성 한다.
    - 사고 이력을 숨기거나 없는 사실을 지어내지 말것"""
    year = datetime.now().year
    # 자동차 정보 추출을 위한 요청
    r = c.ask(
        f"올해는 {year}년이다. 다음 판매글에서 차량 정보를 뽑아라. 글에 없는 값은 null로 둔다.\n{req.memo}",
        response_mime_type="application/json",
        response_schema=CarInfoExtract,
        temperature=0,
    )
    car_ex = r.parsed
    if model is None or scaler is None:
        price = "가격 예측 실패"
    elif car_ex is not None:
        car = CarInfo.model_validate(req.model_dump(include=set(CarInfo.model_fields)))
        price = train_price_model.predict_price(model, scaler, car.model_dump())
    else:
        price = "가격 예측 실패"
    prompt = f"""다음 차정보, 희망가격, 중고차 예측가격, 메모, 말투를 참고해서 중고차 판매글을 제목과 내용으로 작성해.
    희망 가격보다 예측가격이 높으면 예측가격으로 작성해줘.
    [차정보] 
    - {car_ex}
    [희망 가격] : {req.price}
    [예측 가격] : {price}
    [메모] : {req.memo}
    [말투] : {req.tone}
    """
    try:
        r = c.ask(
            prompt,
            response_mime_type="application/json",
            response_schema=ListingResponse,
            temperature=1,
            system_instruction=SYSTEM,
        )
        return r.parsed
    except Exception:
        raise HTTPException(status_code=503, detail="판매글을 만들지 못했습니다")

def to_car_info(ext: CarInfoExtract, fallback: CarInfo) -> CarInfo:
    """LLM이 뽑은 값을 우선 쓰고, null이면 fallback(요청값)으로 채운다"""
    data = fallback.model_dump(include=set(CarInfo.model_fields))  # 차 정보 5개만
    extracted = ext.model_dump(exclude_none=True)                  # null 아닌 것만

    if "accident" in extracted:
        extracted["accident"] = int(extracted["accident"])          # True/False -> 1/0

    data.update(extracted)

    try:
        return CarInfo.model_validate(data)
    except ValidationError as e:
        # 범위 밖 값(예: 주행거리 3,000km)을 LLM이 뽑으면 요청값을 그대로 쓴다
        print("추출값 검증 실패, 요청값 사용:", e.errors())
        return CarInfo.model_validate(fallback.model_dump(include=set(CarInfo.model_fields)))