package day03;

public class Ex03_while문 {

	public static void main(String[] args) {
		/* while문 문법
		 * 
		 * 	while(조건식){
		 * 		실행문;
		 * 	}
		 * 
		 * for문과 비교를 위해 while문 추가. 실제 문법은 위를 참고
		 * 	초기화;
		 * 	while(조건식){
		 * 		실행문;
		 * 		증감식;//실제 while문에서 증감식 위치는 없음. 그냥 실행문
		 * 	}
		 * 
		 * 실행순서
		 * - 조건식(거짓) => 반복문 종료
		 * - 조건식(참) => 실행문
		 * => 조건식(참) => 실행문
		 * => 조건식(거짓) => 반복문 종료
		 * 
		 * while문에서 조건식은 생략할 수 없음
		 * */

		for(int i = 1; i <= 5; i++) {
			System.out.println(i);
		}
		//1부터 5까지 while문으로 출력
		int i = 1;
		while(i <= 5) {
			System.out.println(i);
			i++;
		}
		
		//1부터 5까지 while문으로 출력
		//이 예제는 증감식 위치를 제거한 방법
		i = 1;
		while( i <= 5) {
			System.out.println(i++);
		}
	}

}
