from fastapi import FastAPI
from routers import ml_score
import uvicorn

app = FastAPI(title="머신/딥 러닝")
app.include_router(ml_score.router)

@app.get("/")
def home():
	return { "status" : "ok"}

if __name__ == "__main__":
	uvicorn.run("main:app", port=8000, reload=True)