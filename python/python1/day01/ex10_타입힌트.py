from typing import Literal, Any
# 타입 힌트
# 변수 : 타입 = 값
name: str = "홍길동"
age: int = 25

# 함수 타입힌트
# def 함수명(변수:힌트, 변수:힌트)->리터타입 힌트:
	# 구현

def add(a: int, b: int) -> int:
	return a + b

def greet(name: str, greeting: str = "안녕하세요") -> str:
	return f"{greeting}, {name}님"

def log(message: str) -> None:
	print(f"[LOG] {message}")

print(greet("홍길동", "하이"))

users: dict[int, str] = {1: "김철수", 2: "이영희"}

# 값이 타입 뿐이라 없다는 의미의 None이 올수 있는 경우
def find_user(user_id: int) -> str | None:
  return users.get(user_id)

result = find_user(2)
if result is None: # None is로 비교
	print("사용자 없음")
else:
	print(result.upper())

# 매개변수의 타입이 None일 수 있고, 기본값이 None
def search(keyword: str, limit: int | None = None) -> list[str]:
	data = ["python 입문", "python 심화", "java 입문"]
	# data에 있는 단어에 keyword가 포함되어 있으면 found 리스트에 추가
	found = [d for d in data if keyword in d]
	# 제한이 있으면 찾은 리스트에서 제한된 개수까지 잘라서 반환
	if limit is not None:
		found = found[:limit]
	return found

print(search("python"))
print(search("python", 1))

# Literal은 정해진 값들만 허용할 때 사용
# Any는 아무 타입이나 받을 때 사용

def set_level(level: Literal["low", "mid", "high"]) -> str:
	return f"레벨: {level}"

def to_text(value: int | float | str) -> str:
  return str(value)

def debug(value: Any) -> None:
	print(type(value), value)

debug("123")
debug(123)
# 타입 별칭
Student = dict[str, str | int]
students: list[Student] = [{"name": "김철수", "score": 92}]