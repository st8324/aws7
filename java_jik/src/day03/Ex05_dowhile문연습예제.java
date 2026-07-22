package day03;

import java.util.Scanner;

public class Ex05_dowhile문연습예제 {

	public static void main(String[] args) {
		/* Scanner를 이용해서 숫자를 입력받고, 입력받은 숫자를 출력하는데 0을 입력하면 종료되도록
		 * 코드를 작성하세요.
		 * 예시
		 * 입력 : 1
		 * 1이 입력됐습니다.
		 * 
		 * 입력 : -1
		 * -1이 입력됐습니다.
		 * 
		 * 입력 : 0
		 * 0이 입력됐습니다.
		 * 프로그램을 종료합니다.
		 * */
		
		Scanner scan = new Scanner(System.in);
		
		int num;
		do {
			System.out.print("입력 : ");
			num = scan.nextInt();
			System.out.println(num + "이 입력됐습니다.");		
		}while(num != 0);
		
		System.out.println("프로그램을 종료합니다.");
	}

}
