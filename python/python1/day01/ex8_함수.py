def 함수명(변수1, 변수2):
	# 구현
	pass

# 두 수를 더하는 함수
def add(a, b):
	return a + b

num1, num2 = 1, 2
print(f"{num1} + {num2} = {add(num1, num2)}")

# 기본값을 설정. greeting의 기본값은 안녕하세요
# 기본값 언제 들어감? 해당 변수의 값을 넣지 않을 때
def greet(name, greeting="안녕하세요"):
    return f"{greeting}, {name}님"

print(greet("길동"))
print(greet("길동", "반갑습니다"))
# 함수를 호출할 때 매개변수 이름을 이용하면 매개변수의 순서가 바뀌어도 됨
# => 키워드 인자
print(greet(greeting="반갑습니다", name="길동"))
# print(greet("반갑습니다", "길동")) # 함수 선언 순서대로 반값습니다가 이름으로, 길동이 인사문구로
print(greet(name="길동")) # 정상 실행
# print(greet(greeting="반갑습니다")) # 에러가 발생. name은 기본값이 없어서 생략이 불가능

# 리턴값이 여러개인 경우
def min_max(nums):
    return min(nums), max(nums)

lo, hi = min_max([3, 1, 4])
print(f"{[3,1,4]}에서 최소값은 {lo}, 최대값은 {hi}")

# min_max가 반환 값 중 첫번째 값은 버림
_, hi = min_max([3, 1, 4])
print(f"{[3,1,4]}에서 최대값은 {hi}")

def say_hi():
	print("hi")          # return이 없으면 None을 돌려준다

print(say_hi())

# 여러 수들의 합을 구하는 함수
# 매개변수의 개수가 정해지지 않고, 값들만 필요할 때 *args를 사용
def total(*args):          # 위치 인자들을 tuple로 받는다
    return sum(args)

print(total(1, 2, 3))      # 6
print(total(1, 2))         # 3


def show(**kwargs):        # 매개변수 이름이 고정이 아닌 함수에서 키워드 인자들 dict로 받는다
	for k, v in kwargs.items():
		print(k, v)

show(name="길동", age=25)
show(phone="010-1111-2222", address="서울시")

# 기본값 설정 시, 리스트와 딕셔너리는 사용하지 않음
# 기능 : 리스트에 item을 추가
# 목적 : 리스트가 없으면 빈 리스트에 item을 추가해서 반환
# 실제 : 실행할때마다 빈 리스트에 item을 추가하는게 아니라 처음만 빈 리스트에 item을 추가하고
#       이후에는 추가된 리스트에 이어서 추가
def add_item(item, bucket=[]):
	bucket.append(item)
	return bucket

print(add_item("a"))  # ['a']
print(add_item("b"))  # ['a', 'b']

def add_item2(item, bucket=None):
	if bucket is None:
		bucket = []
	bucket.append(item)
	return bucket

print(add_item2("a"))  # ['a']
print(add_item2("b"))  # ['b']