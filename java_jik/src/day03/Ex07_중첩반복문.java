package day03;

public class Ex07_중첩반복문 {

	public static void main(String[] args) {
		
		/* 중첩 반복문
		 * - 반복문의 실행문으로 반복문이 오는 경우
		 * - 반복문 안에 반복문이 오는 경우
		 * for( ; ; ){
		 * 		반복문;
		 * }
		 * */
		//구구단 2단을 출력하는 코드
		
		int num = 2;
		
		//구구단 2단 코드 => num단 코드
		//num단을 출력 => num값에 따라 출력되는 단이 바뀜
		for(int i = 1; i <= 9; i++) {
			System.out.println(num + " X " + i + " = " + num * i);
		}
		
		
		//구구단 전체를 출력하는 코드(2단~9단)
		//num를 2에서 9까지 1씩 증가후 => num단을 출력
		for(num = 2; num <= 9; num++) {
			System.out.println(num+"단 출력");
			//num단 출력 코드를 가져오면 됨
			//num단을 출력
			for(int i = 1; i <= 9; i++) {
				System.out.println(num + " X " + i + " = " + num * i);
			}
		}
	}
}
