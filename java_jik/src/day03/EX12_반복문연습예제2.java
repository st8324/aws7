package day03;

public class EX12_반복문연습예제2 {

	public static void main(String[]args) {
		/* 다음과 같이 출력되도록 코드를 작성하세요.
		 * 1
		 * 12
		 * 123
		 * 1234
		 * 12345
		 * i=1 1부터 1까지 출력 후 엔터
		 * i=2 1부터 2까지 출력 후 엔터
		 * i=3 1부터 3까지 출력 후 엔터
		 * ..
		 * i=5 1부터 5까지 출력 후 엔터
		 * 반복횟수 : i는 1부터 5까지 1씩 증가
		 * 실행문: 1부터 i까지 출력 후 엔터
		 * 		반복횟수 : j는 1부터 i까지 1씩 증가
		 * 		실행문 : j를 출력(print)
		 * */
		for(int i = 1; i<=5; i++) {
			//1부터 i까지 출력
			for(int j = 1; j <= i; j++) {
				System.out.print(j);				
			}
			//엔터
			System.out.println();
		}
		/* a
		 * ab
		 * abc
		 * abcd
		 * ...
		 * abcdefg...xyz
		 * */
		for(int i = 'a'; i<='z'; i++) {
			//1부터 i까지 출력
			for(int j = 'a'; j <= i; j++) {
				System.out.print((char)j);				
			}
			//엔터
			System.out.println();
		}
		//char 변수를 이용하여 자료형변환 X
		for(char i = 'a'; i<='z'; i++) {
			//1부터 i까지 출력
			for(char j = 'a'; j <= i; j++) {
				System.out.print(j);				
			}
			//엔터
			System.out.println();
		}
	}
}
