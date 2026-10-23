x, y, z= 1, 2, 3;
print(x, y , z)
x = "홍길동"
print(x)
print(type(x)) # 변수 타입 확인

name = "홍길동"
print("제 이름은 " + name + "입니다.")
print(f"제 이름은 {name}입니다.") # 포맷을 이용하여 변수값을 문자열 중간에 추가
print("-".join(["a", "b", "c"])) # 하나의 문자열로 만듬
print(name[0]) 
print(name[-1]) # -1 : 뒤에서 첫번째
# [0:2] 0번지부터 2번지 전까지. 
# [2:] 2번지부터 끝까지
# [:2] 2번지 전까지
print(name[0:2], name[2:], name[:2])
print("홍" in name) # name에 홍이 있는지
