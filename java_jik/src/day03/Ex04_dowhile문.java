package day03;

public class Ex04_dowhile문 {

	public static void main(String[] args) {
		/* do while문
		 * - 무조건 한번은 실행
		 * - 실행 후 조건을 검사
		 * - while()옆에 ;를 반드시 붙여야 함
		 * 문법
		 * 	do{
		 * 		실행문;
		 * 	}while(조건식);
		 * */

		int num = 10;
		//do while문은 실행하고 조건식을 확인하기 때문에 조건이 맞지 않아도 실행
		do {
			System.out.println(num + "는 0보다 작습니다.(do while문)");
			num--;
		}while(num < 0);
		
		//while문은 조건식을 먼저 확인하기 때문에 실행이 안됨
		num = 10;
		while(num < 0) {
			System.out.println(num + "는 0보다 작습니다.(while)");
			num--;
		}
	}

}
