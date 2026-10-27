from fastapi import APIRouter, HTTPException, status, Query


router = APIRouter(prefix="/products", tags=["products"])

@router.get("/{product_id}")
def get_product(product_id:int) -> dict:
    return { "id" : product_id, "name" : "컴퓨터"}

@router.get("")
def list_products(
		# 인자 값을 검사. ge: 크거나 같다, le : 작거나 같다
    page: int = Query(default=1, ge=1),
    size: int = Query(default=10, ge=1, le=100),
) -> dict:
	return {"page": page, "size": size}