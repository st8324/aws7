package day02;

import java.util.Scanner;

public class Ex10_if문연습예제 {

	public static void main(String[] args) {
		/* Scanner를 이용하여 정수를 입력받아 정수가 양수인지, 0인지, 음수인지를 판별하는 코드를 작성하세요.
		 * 예시1
		 * 정수 입력 : 10
		 * 10은 양수
		 * 
		 * 예시2
		 * 정수 입력 : -3
		 * -3은 음수
		 * 
		 * 예시3
		 * 정수 입력 : 0
		 * 0
		 * */
		
		//정수 num 입력
		Scanner scan = new Scanner(System.in);
		System.out.print("정수 입력 : ");
		int num = scan.nextInt();
		
		//num이 0보다 크면 양수라고 콘솔에 출력하고
		if(num > 0) {
			System.out.println(num + "는 양수");
		}
		//num이 0보다 작으면 음수라고 콘솔에 출력하고
		else if(num < 0) {
			System.out.println(num + "는 음수");
		}
		//아니면 0이라고 출력하세요.
		else {
			System.out.println(num);
		}
	}

}
