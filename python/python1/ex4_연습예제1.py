num1 , op, num2 = 1, "/", 2

# 연산자(op)에 따라 연산 결과를 출력하는 코드를 작성하세요.
# 연산자 종류는 +, -, *, /
if op == "+":
	print(f"{num1} {op} {num2} = {num1 + num2}")
elif op == "-":
	print(f"{num1} {op} {num2} = {num1 - num2}")
elif op == "*":
	print(f"{num1} {op} {num2} = {num1 * num2}")
elif op == "/":
	print(f"{num1} {op} {num2} = {num1 / num2}")
else:
	print("잘못된 연산자입니다.")
