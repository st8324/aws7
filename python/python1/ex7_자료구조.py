# 리스트. 순서 O, 변경 O, 중복 허용
# []
nums = [3, 1, 4]
nums.append(1)                # 1을 리스트 뒤에 추가 
nums.insert(0, 9)             # 0번지에 9를 추가
print(nums)                   # [9, 3, 1, 4, 1]
print(len(nums))              # 5
print(nums[0], nums[-1])      # 9 1

print(nums[1:3])              # 1번지에서 3번지 앞까지 [3, 1]
print(nums[::-1])             # 뒤집기

nums.remove(1)                # 첫 번째 1 삭제. (1번지 삭제하는게 아님) 9 3 4 1
last = nums.pop()             # 마지막 요소를 꺼내면서 삭제 9 3 4

print(4 in nums)              # 리스트에 4가 있는지 확인
print(sorted(nums))           # 정렬된 새 리스트
print(nums)
nums.sort(reverse=True)       # 원본 정렬
print(nums)
print(sum(nums), max(nums), min(nums))

# 튜플 : 수정 불가능한 리스트
point = (10, 20)
# point[0] = 5               # TypeError

x, y = point                  # 언패킹
print(f"x={x}, y={y}")
a, b = 1, 2
print(f"a={a}, b = {b}")
a, b = b, a                   # 값 교환 # 두 수 교환
print(f"a={a}, b = {b}")

# 딕셔너리. 자바스크립트의 객체. 자바의 Map
user = {"name": "홍길동", "age": 25}

user["email"] = "hong@test.com" # 딕셔너리의 새 키를 추가
print(user["name"])
# print(user["phone"])               # []를 이용한 접근은 키값이 없으면 예외가 발생할생해서 중단
print(user.get("phone"))             # get()을 이용한 접근은 키값이 없으면 None을 반환
print(user.get("phone", "없음"))      # get()을 이용했을 때 키값이 일치하는게 없을 때 값을 지정
print("age" in user)                 # user 딕셔너리에 키값이 age가 있는지 확인

# 딕셔너리에 있는 값들을 반복문을 이용해서 꺼냄
for key, value in user.items():
	print(key, value)

del user["email"] # 딕셔너리의 키를 삭제

response = {
	"status": "ok",
	"data": {"users": [{"id": 1, "name": "김철수"}, {"id": 2, "name": "이영희"}]},
}
print(response["data"]["users"][0]["name"])   # 김철수

# set
tags = {"ai", "python", "ai"}
print(tags)                  # {'ai', 'python'}
tags.add("spring")           # set에 'spring'을 추가
print(tags)                  # {'ai', 'python'}

a = {1, 2, 3}
b = {2, 3, 4}
# 집합
print(a & b)                 # {2, 3} # 교집합. 두 집합에 공통으로 있는 원소들
print(a | b)                 # {1, 2, 3, 4} # 합집합. 두 지합의 모든 원소들
print(a - b)                 # {1} # 차집합 : a에만 있는 원소들
# 집합을 사용하는 경우. 자료구조를 듣는 학생들 있고, 알고리즘을 듣는 학생들이 있을 때
# 자료구조만 듣는 학생들 : 차집합
# 자료구조와 알고리즘을 모두 듣는 학생들 : 교집합
# 자료구조 또는 알고리즘을 듣는 학생들 : 합집합
print(list(set([1, 1, 2, 3, 3])))   # 리스트의 중복 제거해서 리스트로 만듬

empty = set() # 빈 셋 생성