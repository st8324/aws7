for i in range(6) : # 0~5
	print(i)

print("=============")

for i in range(0, 6, 2): # 0부터 5까지 2씩 증가
	print(i)

print("=============")

count = 1
while count <= 3: # 1부터 3까지
	print(count)
	count += 1

fruits = ["사과", "바나나", "포도"]
for fruit in fruits:
    print(fruit)
# 번지의 값을 가져오려면 enumerate를 사용
for idx, fruit in enumerate(fruits):
    print(idx, fruit)