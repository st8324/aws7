import pandas as pd

from sklearn.metrics import mean_absolute_error
from sklearn.model_selection import train_test_split
from sklearn.neighbors import KNeighborsRegressor
from sklearn.preprocessing import StandardScaler
from pathlib import Path

CAR_TYPE = ["경차", "세단", "SUV"] 
FEATURES = ["car_type", "year_age", "mileage_km", "engine_cc", "accident"]

# models 폴더의 위치
BASE_DIR = Path(__file__).resolve().parent
# 학습할 데이터 위치 및 파일명
DATA_PATH = BASE_DIR / "used_cars.csv"
# 저장된 모델 위치 및 파일명
MODEL_PATH = BASE_DIR / "price_modle.joblib"
# 저장된 스케일러 위치 및 파일명
SCALER_PATH = BASE_DIR / "price_scaler.joblib"

def load_data():
	"""1. 데이터 선택 : csv 파일을 읽어서 특징(X)과 정답(y)로 나눔"""
	# csv파일에서 데이터를 읽어서 표로 만듬
	df = pd.read_csv("used_cars.csv", encoding="utf-8")

	# 문제(특징. feature). 특징 선택할 땐 반드시 리스트로 []
	X = df[["car_type", "year_age", "mileage_km", "engine_cc", "accident"]]
	# print(X.head()) # 확인
	# 답(레이벨. label)
	y = df["price"]
	return X, y

# 원핫인코딩
def one_hot(X):
	"""2. 사전 준비 : 차종을 원-핫 인코딩 한다"""
	X2 = X.copy()
	X2["car_type"] = pd.Categorical(X2["car_type"], categories=CAR_TYPE)
	X2 = pd.get_dummies(X2, columns=["car_type"], dtype=int)
	return X2

def train(X_train, y_train):
	"""3. 학습 : 훈련 데이터를 이용하여 학습한다. 결과는 모델과 스케일러"""
	# 스케일링: 기준은 학습용 데이터로 정하고, 시험용은 같은 기준으로 바꾸기만 한다
	scaler = StandardScaler()
	X_train_s = scaler.fit_transform(X_train)

	# KNN, 스케일링 함
	model = KNeighborsRegressor(n_neighbors=5)
	model.fit(X_train_s, y_train)
	return model, scaler

def predict_price(model, scaler, car):
	"""4. 예측 : 중고차 1대 정보로 중고차 시세를 예측한다"""
	# 중고차 정보를 표로 만듬(1줄인)
	X_new = pd.DataFrame([car])

	# 원핫 인코딩
	X_new_oh = one_hot(X_new)

	# 스케일링
	X_new_s = scaler.transform(X_new_oh)
	return model.predict(X_new_s)[0]

def main():
	# 1. 데이터 선택
	X,y = load_data()
	# 2. 원-핫 인코딩
	X2 = one_hot(X)
	# 3. 훈련, 테스트 분리
	X_train, X_test, y_train, y_test = train_test_split(X2, y, test_size=0.2, random_state=42)
	# 4. 학습
	model, scaler = train(X_train, y_train)
	# 5. 평가
	X_test_s = scaler.transform(X_test)
	print(f"KNN 스케일링 O: {mean_absolute_error(y_test, model.predict(X_test_s)):.0f}만원")
	# 6. 예측
	# 예측하려는 자동차 시세 정보
	car = { 
    "car_type" : "SUV", "year_age" : 3, "mileage_km" : 40000, "engine_cc" : 2500, "accident" : 0
	}
	print(f"차종 : {car["car_type"]}, 연식 : {car["year_age"]}, 주행거리 : {car["mileage_km"]}km, ", end="")
	print(f"배기량 : {car["engine_cc"]}cc, 사고유무 : {"있음" if car["accident"] else "없음"}")
	print(f"예측 시세 : {predict_price(model, scaler, car):.0f}만원")


if __name__ == "__main__":
	main()

