from fastapi import APIRouter
from pathlib import Path
import joblib
import pandas as pd

# 현재 파일 위치에서 부모/부모 폴더를 선택(fastapi_ml폴더)
BASE_DIR = Path(__file__).resolve().parent.parent
MODEL_PATH = BASE_DIR / "ml" / "models" / "score_model1.joblib"

model1 = joblib.load(MODEL_PATH)

router = APIRouter(prefix="/ml/score", tags=["ml"])

@router.get("/s1/predict")
def s1_pridct(study_hour:int)->dict:
	X = pd.DataFrame({"study_hours" : [study_hour]})
	score = float(model1.predict(X)[0])
	return { "score" : score }

