# main.py
from fastapi import FastAPI, Query
from typing import Literal
from pydantic import BaseModel, Field
from router import products

app = FastAPI(title="AI Server")
app.include_router(products.router)

# 기본 설명
@app.get("/")
def health() -> dict:
    return {"status": "ok"}

# 스프링의 @PathVariable 처리를 fastAPI에서는 어떻게 하는지
@app.get("/users/{user_id}")
def get_user(user_id:int) -> dict:
    
	return { "userId" : user_id}

# url에 ?변수1=값&변수2=값 형태로 오는 경우
@app.get("/search")
def search(q:str, page:int = 1, keyword:str | None = None ) -> dict:

	return {"query" : q, "page" : page, "keyword" : keyword}


# 변수의 값이 지정된 값들 중 하나를 선택할 때 사용하는 방법
@app.get("/posts")
def list_posts(sort: Literal["latest", "popular"] = "latest") -> dict:
    return {"sort": sort}


class Search(BaseModel):
    q: str = Field(min_length=1)
    page: int = Field(ge=1)
    keyword: str | None = None

@app.get("/search2")
def search(search:Search ) -> dict:

	return {search.model_dump()}

class UserResponse(BaseModel):
	id : int
	email : str
	name : str
# response_model을 설정하면 return으로 보내는 값들을 제한할 수 있음
# => return으로 이것저것 보내도 response_model로 설정된 클래스에 있는 필드들만 보내짐
@app.get("/users2/{user_id}", response_model=UserResponse, status_code=201)
def get_user2(user_id:int) -> dict:
	return {
		"id" : 1, "email" : "xxx@naver.com", "name" : "ghdrlfehd",
		"phone" : "011-1111-2222"
	}
