# pip install joblib scikit-learn pandas
# python .\ml_model_score1.py
from pathlib import Path
 
import joblib
import pandas as pd
from sklearn.linear_model import LinearRegression
from sklearn.metrics import mean_absolute_error, r2_score
from sklearn.model_selection import train_test_split
 
# 1. 데이터 불러오기
df = pd.read_csv("study_scores.csv", encoding="utf-8")
 
# 2. 특징(X)과 정답(y): 공부 시간 하나만 쓴다
X = df[["study_hours"]]
y = df["score"]
 
# 3. 학습용 80%, 시험용 20%
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)
 
# 4. 학습
model = LinearRegression()
model.fit(X_train, y_train)
print(f"찾은 규칙: 점수 = {model.coef_[0]:.2f} × 공부 시간 + {model.intercept_:.2f}")
 
# 5. 평가
pred = model.predict(X_test)
print(f"MAE: {mean_absolute_error(y_test, pred):.1f}점")
print(f"R²:  {r2_score(y_test, pred):.2f}")
 
# 6. 저장
# 저장 경로 폴더 생성
Path("models").mkdir(exist_ok=True)
# 모델을 저장
joblib.dump(model, "models/score_model1.joblib")
# 로그
print("저장 완료: models/score_model1.joblib")
