package day02;

import java.util.Scanner;

public class Ex11_if문연습예제2 {

	public static void main(String[] args) {
		/* Ex08_콘솔입력연습예제를 참고해서 두 정수와 연산자를 입력해서 연산 결과를 출력하는 코드를 작성하세요.
		 * - 연산자는 +, -, *, /, %만 처리
		 * 
		 * 예시
		 * 정수1 입력 : 1 
		 * 연산자 입력 : / (문자로 입력)
		 * 정수2 입력 : 2
		 * 1 / 2 = 0.5
		 * 
		 * 예시
		 * 정수1 입력 : 1 
		 * 연산자 입력 : ?
		 * 정수2 입력 : 2
		 * ?는 산술 연산자가 아닙니다.
		 * */

		//스캐너 만들기
		Scanner scan = new Scanner(System.in);
		
		System.out.print("정수를 입력하세요 : ");
		int num1 = scan.nextInt();
		
		System.out.print("연산자를 입력하세요: ");
		char op = scan.next().charAt(0);
		
		System.out.print("정수를 입력하세요 : ");
		int num2 = scan.nextInt();
		
		//연산자 op가 +이면 num1 + num2를 콘솔에 출력
		if(op == '+') {
			System.out.println("" + num1 + op + num2 + "=" + (num1 + num2));
		}
		//연산자 op가 -이면 num1 - num2를 콘솔에 출력
		else if(op == '-') {
			System.out.println("" + num1 + op + num2 + "=" + (num1 - num2));
		}
		//연산자 op가 *이면 num1 * num2를 콘솔에 출력
		else if(op == '*') {
			System.out.println("" + num1 + op + num2 + "=" + (num1 * num2));
		}
		//연산자 op가 /이면 num1 / num2를 콘솔에 출력
		else if(op == '/') {
			System.out.println("" + num1 + op + num2 + "=" + (num1 / (double)num2));
		}
		//연산자 op가 %이면 num1 % num2를 콘솔에 출력
		else if(op == '%') {
			System.out.println("" + num1 + op + num2 + "=" + (num1 % num2));
		}
		//연산자 op가 산술연산자가 아니면 op는 산술연산자가 아닙니다를 콘솔에 출력
		else {
			System.out.println(op+"는 산술 연산자가 아닙니다.");
		}
	}

}
