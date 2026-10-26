# if elif else
num = 1
if num > 0: # if문 뒤에 :
	print(f"{num}는 양수")	# if문 실행문은 들여쓰기 한번을 해야 한다
elif num < 0: # else if가 아닌 elif
	print(f"{num}는 음수")
else:
	print("0")

# 자바에서 조건선택 연산자 : 조건식 ? 참 : 거짓
score = 90
result = "합격" if score >= 60 else "불합격"
print(f"{score}점은 {result}")

# 자바의 switch문과 비슷한 match(파이썬 3.10부터 사용 가능)
# match는 break가 필요없음. 
# match는 default가 없고 _을 이용
num1 , op, num2 = 1, "/", 2
match op:
	case "+":
		print("더하기")
	case "/":
		print("나누기")
	case _:
		print("잘못된 연산자")