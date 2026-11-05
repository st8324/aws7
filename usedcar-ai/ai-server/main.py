"""
중고차 AI 서비스 - FastAPI 서버

실행:  uvicorn main:app --reload --port 8000
시험:  http://localhost:8000/docs

이 파일은 거의 고칠 일이 없다. 기능은 routers/ 안의 파일에서 만든다.
    routers/price.py     1단계 시세 예측 (머신러닝)
    routers/car_type.py  2단계 사진 분류 (딥러닝)
    routers/listing.py   3단계 판매글   (AI)
"""

from contextlib import asynccontextmanager

from dotenv import load_dotenv
from fastapi import FastAPI

import uvicorn

# .env 파일의 값(API 키 등)을 환경 변수로 읽는다. 라우터보다 먼저 해야 한다.
load_dotenv()

from routers import car_type, listing, price  # noqa: E402
from schemas import Features, HealthResponse  # noqa: E402


@asynccontextmanager
async def lifespan(app: FastAPI):
    # 서버가 켜질 때 한 번만 실행된다. 모델은 여기서 불러온다.
    # 요청마다 불러오면 느려서 Spring의 대기 시간(3초)을 넘긴다.
    price.load()
    car_type.load()
    listing.load()
    yield
    # 서버가 꺼질 때 정리할 것이 있으면 여기에 쓴다


app = FastAPI(
    title="중고차 AI 서버",
    description="Spring(8080)이 부르는 AI 서버. 명세: 「중고차 AI 서비스: FastAPI 명세」",
    version="0.0.1",
    lifespan=lifespan,
)

app.include_router(price.router)
app.include_router(car_type.router)
app.include_router(listing.router)


@app.get("/health", response_model=HealthResponse, tags=["0 상태"])
def health():
    """Spring이 이 값을 보고 화면에 '사용 가능 / 준비 중'을 표시한다"""
    """화면에서 AI서버 연겸됨 뒤에 나오는 사용 가능 여부를 여기서 확인한다"""
    return HealthResponse(
        features=Features(
            price=price.is_ready(),
            car_type=car_type.is_ready(),
            listing=listing.is_ready(),
        )
    )

if __name__ == "__main__":
	uvicorn.run("main:app", port=8000, reload=True)