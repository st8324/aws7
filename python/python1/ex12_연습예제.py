students: list[dict] = [
	{"name": "김민준", "scores": {"국어": 92, "수학": 88, "영어": 95}},
	{"name": "이서연", "scores": {"국어": 85, "수학": 91, "영어": 78}},
	{"name": "박지호", "scores": {"국어": 70, "수학": 65, "영어": 82}},
	{"name": "최수아", "scores": {"국어": 98, "수학": 94, "영어": 90}},
	{"name": "정하준", "scores": {"국어": 60, "수학": 72, "영어": 68}},
	{"name": "강예린", "scores": {"국어": 88, "수학": 79, "영어": 85}},
	{"name": "조도윤", "scores": {"국어": 75, "수학": 83, "영어": 71}},
	{"name": "윤서아", "scores": {"국어": 81, "수학": 96, "영어": 89}},
]

# 학생별 평균을 구하는 코드를 작성
for student in students: # 학생들 중 학생 한명 정보를 꺼냄
	scores:list[int] = []
	for subject, score in student["scores"].items(): # 학생의 성적들을 하나씩 꺼냄
		scores.append(score)
	# 위 3줄을 컴프리헨션으로 1줄로 줄임
	# scores:list[int] = [score for _, score in student["scores"].items()]
	print(f"{student.get("name")} : {sum(scores)/len(scores):.2f}점")

# 학생들의 국어 성적을 가지고 있는 딕셔너리를 생성해서 print로 출력해보세요.(이름은 중복되지 않음)
# kor_scores = {"김민준" : 92, "이서연" : 85}
kor_scores = {}
for student in students:
	# {"name": "김민준", "scores": {"국어": 92, "수학": 88, "영어": 95}},
	name = student["name"]
	kor_score = student["scores"]["국어"]
	kor_scores[name] = kor_score
print(kor_scores)

kor_scores = {student["name"]:student["scores"]["국어"] for student in students}
print(kor_scores)

for student in students: # 학생들 중 학생 한명 정보를 꺼냄
	scores:list[int] = []
	for subject, score in student["scores"].items(): # 학생의 성적들을 하나씩 꺼냄
		scores.append(score)
	# 위 3줄을 컴프리헨션으로 1줄로 줄임
	# scores:list[int] = [score for _, score in student["scores"].items()]
	print(f"{student.get("name")} : {sum(scores)/len(scores):.2f}점")

"""성적 리스트의 평균을 구하는 함수"""
def average(scores:list[int])->float:
	return sum(scores)/len(scores)

Student = dict[str, dict]

"""학생 정보 중 성적들을 리스트로 반환하는 함수"""
def get_scores(student:Student)->list[int]:
	scores:list[int] = []
	for _, score in student["scores"].items(): # 학생의 성적들을 하나씩 꺼냄
		scores.append(score)
	return scores

for student in students:
	scores = get_scores(student)
	avg = average(scores)
	print(f"{student["name"]} : {avg:.2f}")
