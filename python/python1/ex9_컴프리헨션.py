# 1에서 5의 제곱들을 리스트에 저장하는 코드
squares = []
for n in range(1, 6):
	squares.append(n * n)
print(squares)

# 컴프리헨션을 이용한 1에서 5의 제곱들을 리스트에 저장하는 코드
# for 앞 부분의 값을 저장
squares = [n * n for n in range(1, 6)]           # [1, 4, 9, 16, 25]
print(squares)

evens = [n for n in range(10) if n % 2 == 0]     # [0, 2, 4, 6, 8]
print(evens)

labels = ["짝" if n % 2 == 0 else "홀" for n in range(5)]
print(labels)

# 컴프리헨션을 이용한 딕셔너리 생성
words = ["ai", "python", "spring"]
lengths = {w: len(w) for w in words}             # {'ai': 2, 'python': 6, 'spring': 6}
print(lengths)
first_chars = {w[0] for w in words}
print(first_chars)