package day03;

public class Ex06_반복문연습예제 {

	public static void main(String[] args) {
		/* 다음과 같이 출력되도록 코드를 작성하세요.
		 * 1 2 3 4
		 * 5 6 7 8
		 * 9 10 11 12
		 * 13 14 15 16
		 * 힌트 
		 * System.out.print()와System.out.println()을 적절히 활용
		 * 
		 * print()로 숫자를 출력하고 
		 * 숫자가 4의 배수가 아니면 ' '을 출력하고 4의 배수이면 println()을 출력
		 * 
		 * 반복횟수 : i는 1부터 16까지 1씩증가
		 * 실행문 :
		 * 1. i를 출력(print()로)
		 * 2. i가 4의 배수이면 println()을 실행하고 아니면 print(' ')을 실행
		 * 
		 * A의 배수 : 어떤수를 A로 나누었을 때 나머지가 0이 되는 수
		 */
		for(int i = 1; i<= 16; i++) {
			System.out.print(i + " ");
			if(i % 4 == 0) {
				System.out.println();
			}
			
		}
		//조건 연산자를 이용한 방법
		for(int i = 1; i<= 16; i++) {
			System.out.print("" + i + (i % 4 == 0 ? '\n' : ' '));
		}

	}

}
