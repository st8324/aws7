from fastapi import APIRouter, HTTPException, status, Query
from pydantic import BaseModel, Field

# PostController처럼 /posts로 시작하는 경로에 대한 처리를 함

router = APIRouter(prefix="/products", tags=["products"])

class ProductCreate(BaseModel):
	id:int = Field(ge=1)
	title:str = Field(min_length=1)
	price:int = Field(ge=0)

products= [
	{ "id" : 1, "title" : "목걸이", "price" : 10000},
	{ "id" : 2, "title" : "반지", "price" : 100000},
	{ "id" : 3, "title" : "바지", "price" : 50000},
]

# 경로에 있는 필요할 때 사용방법(스프링에서 @PathVariable)
@router.get("/{product_id}") 
def get_product(product_id:int):
	product = [p for p in products if p["id"] == product_id]

	return product[0] if len(product) != 0 else None

# 매개변수에 조건을 거는 방법을 설명(Query)
@router.get("")
def get_products(page:int = Query(default=1, ge=1)):

	return products[page-1:page] if page <= len(products) else None

# 여러 정보를 받을 때 클래스를 이용하는 방법
# BaseModel을 상속 받은 클래스를 추가하여 가져옴
@router.post("")
def create_products(product: ProductCreate):
	print(product)
	return { "message" : "제품을 등록했습니다."}