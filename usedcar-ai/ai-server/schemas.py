"""
요청과 응답의 모양 (명세: 「중고차 AI 서비스: FastAPI 명세」)

Pydantic 모델에 타입과 범위를 적어 두면
- 명세와 다른 요청이 오면 FastAPI가 알아서 422로 거절한다
- /docs 화면에 요청, 응답 예시가 자동으로 나온다

필드 이름은 snake_case. Spring이 보내는 JSON 이름과 똑같아야 한다.
"""

from typing import Literal

from pydantic import BaseModel, Field
from typing import Optional

CarType = Literal["경차", "세단", "SUV"]
Tone = Literal["친근하게", "전문적으로", "짧게"]


# ---------- 공통: 차 정보 ----------
class CarInfo(BaseModel):
    """차 정보 5개. 허용 범위 = 학습 데이터(used_cars.csv)의 범위"""

    car_type: CarType = Field(examples=["SUV"])
    year_age: int = Field(ge=0, le=15, description="출고 후 몇 년", examples=[3])
    mileage_km: int = Field(ge=5_000, le=200_000, description="주행거리 (km)", examples=[40000])
    engine_cc: int = Field(ge=1_000, le=3_500, description="배기량 (cc)", examples=[2500])
    accident: Literal[0, 1] = Field(description="사고 이력 (1=있음)", examples=[0])


# ---------- GET /health ----------
class Features(BaseModel):
    price: bool
    car_type: bool
    listing: bool


class HealthResponse(BaseModel):
    status: str = "ok"
    features: Features


# ---------- 1단계: POST /price/predict ----------
class PriceResponse(BaseModel):
    price: int = Field(description="예상 가격 (만 원)", examples=[1850])


# ---------- 2단계: POST /car-type/classify ----------
class CarTypeResponse(BaseModel):
    car_type: CarType
    confidence: float = Field(ge=0, le=1, examples=[0.87])
    scores: dict[str, float] = Field(examples=[{"경차": 0.05, "세단": 0.08, "SUV": 0.87}])


# ---------- 3단계: POST /listing/write ----------
class ListingRequest(CarInfo):
    price: int = Field(ge=1, description="판매 가격 (만 원)", examples=[1850])
    memo: str = Field(default="", max_length=200, examples=["금연차, 정기 점검 받음"])
    tone: Tone = "친근하게"


class ListingResponse(BaseModel):
    title: str
    body: str

class CarInfoExtract(BaseModel):
    """Gemini 추출용"""
    car_type: Optional[CarType] = None
    year_age: Optional[int] = Field(default=None, description="출고 후 몇년")
    mileage_km: Optional[int] = Field(default=None, description="주행거리(km)")
    engine_cc: Optional[int] = Field(default=None, description="배기량(cc)")
    accident: Optional[bool] = Field(default=None, description="사고 이력 여부")