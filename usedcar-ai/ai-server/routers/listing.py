"""
3단계: 판매글 작성 (AI)

POST /listing/write
  요청: 차 정보 5개 + price + memo + tone
  응답: { "title": "...", "body": "..." }

지금은 받은 값으로 문장을 이어 붙인 고정 형식 글을 돌려준다.
"""

import os

from fastapi import APIRouter, HTTPException

from schemas import ListingRequest, ListingResponse

router = APIRouter(prefix="/listing", tags=["3 판매글"])

client = None  # 서버가 켜질 때 load()가 채운다 (LLM API 클라이언트)


def load():
    """서버가 켜질 때 main.py가 한 번 부른다"""
    global client
    api_key = os.getenv("LLM_API_KEY")
    # TODO(3단계): 키가 있으면 LLM API 클라이언트를 만든다
    #   if api_key:
    #       client = ...


def is_ready() -> bool:
    # 0단계: 고정값으로라도 응답하니까 True
    # TODO(3단계): return client is not None   (키가 없으면 False → 화면에 "준비 중")
    return True


@router.post("/write", response_model=ListingResponse)
def write(req: ListingRequest):
    # TODO(3단계): 프롬프트를 만들어 LLM API를 부른다
    #   - 차 정보, 가격, 메모, 말투를 넣는다
    #   - "사고 이력을 숨기거나 없는 사실을 지어내지 말 것"
    #   - 제목과 본문을 나눠 받을 수 있게 출력 형식(JSON)을 정해 준다
    #   - 실패하면 raise HTTPException(status_code=503, detail="판매글을 만들지 못했습니다")
    accident = "사고 이력이 있습니다" if req.accident else "무사고 차량입니다"
    title = f"{req.year_age}년차 {req.car_type}, 주행 {req.mileage_km:,}km"
    body = (
        f"출고 {req.year_age}년 된 {req.car_type}입니다. "
        f"배기량 {req.engine_cc:,}cc, 주행거리 {req.mileage_km:,}km이고 {accident}.\n"
        f"판매 가격은 {req.price:,}만 원입니다."
    )
    if req.memo:
        body += f"\n{req.memo}"
    return ListingResponse(title=title, body=body)

