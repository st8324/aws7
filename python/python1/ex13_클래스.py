# 파이썬에서 변수명은 snake case를 사용
# studnet + score 
# camel case : studentScore
# snake case : student_case

# 파이썬에서 클래스의 생성자와 함수들은 매개변수로 self를 반드시 사용
class Student:
	school = "AI 아카데미"                       # 클래스 변수(모든 객체가 공통으로 사용하는 변수)

	# 생성자 : __init__
	# self.변수명 : 인스턴스 변수
	def __init__(self, name: str, score: int) -> None:
		self.name = name
		self.score = score

	def is_pass(self) -> bool:
		return self.score >= 60

	def add_bonus(self, point: int) -> None:
		self.score += point

	# __str__ : toString
	def __str__(self) -> str:
		return f"{self.school} {self.name}({self.score}점)"

s = Student("김철수", 55)
print(s)                         # 김철수(55점)
print(s.is_pass())               # False
s.add_bonus(10)
print(s, s.is_pass())            # 김철수(65점) True

s2 = Student("홍길동", 90)
print(s2)
Student.school = "FAST 캠퍼스"
print(s)
print(s2)

class Animal:
	def __init__(self, name: str) -> None:
		self.name = name

	def speak(self) -> str:
		return "..."

	def introduce(self) -> str:
		return f"{self.name}: {self.speak()}"

# Animal 클래스를 상속 받음
class Dog(Animal):
	def __init__(self, name: str, breed: str) -> None:
		# super() 부모 클래스의 객체
		super().__init__(name)
		self.breed = breed

	def speak(self) -> str:
		return "멍멍"

class Cat(Animal):
	def speak(self) -> str:
		return "야옹"


for a in [Dog("바둑이", "진돗개"), Cat("나비")]:
	print(a.introduce())  

#isinstance(객체, 클래스) : 객체가 클래스로 형변환이 가능한지를 확인
print(isinstance(Cat("나비"), Animal))  # instanceof