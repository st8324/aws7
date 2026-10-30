from fastapi import FastAPI, Query
from routers import products
import uvicorn


app = FastAPI(title="AI Server")
app.include_router(products.router)

# 기본 사용방법 설명
@app.get("/")
def home() : 
	return { "message" : "fastapi 연결 성공"}

if __name__ == "__main__":
	uvicorn.run("main:app", port=8000, reload=True)