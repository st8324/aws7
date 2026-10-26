# main.py
from fastapi import FastAPI, Query
from typing import Literal
from pydantic import BaseModel, Field

from routers import users

app = FastAPI(title="AI Server")
app.include_router(users.router)

class UserCreate(BaseModel):
	name: str = Field(min_length=2, max_length=20)
	age: int = Field(ge=0, le=150)
	email: str | None = None
	tags: list[str] = []
    

@app.get("/health")
def health() -> dict:
  return {"status": "ok"}

# @app.get("/users/{user_id}")
# def get_user(user_id:int) ->dict:
# 	return {"user_id" : user_id}

@app.get("/search")
def search(q: str, page:int = 1, keword : str | None = None)->dict:
	return { "q" : q, "page" : page, "keyowrd" : keword}

@app.get("/products")
def list_products(
	page : int = Query(default=1, ge=1)
) -> dict:
	return {"page" : page}

@app.get("/posts")
def list_posts(sort: Literal["latest", "popular"] = "latest") -> dict:
    return {"sort": sort}

@app.post("/users")
def create_user(user: UserCreate) -> dict:
    return {"received": user.model_dump()}