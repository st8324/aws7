# pip install mypy
# 위 라이브러리가 설치 되어 있어야 함

def add(a:int, b:int)->int:
	return a+b;

add("1", "2") # 타입이 달라 에러 발생

# 파워쉘에 mypy 파일명 실행