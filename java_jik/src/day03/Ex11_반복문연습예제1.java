package day03;

public class Ex11_반복문연습예제1 {

	public static void main(String[] args) {
		/* 두 정수의 최대 공약수를 구하는 코드를 작성하세요.
		 * 약수 : 나누어 떨어지는 수
		 * 12의 약수 : 1 2 3 4 6 12
		 * 8의 약수 : 1 2 4 8 
		 * 
		 * 공약수 : 약수들 중 공통으로 있는 수들
		 * 8과 12의 공약수 : 1 2 4
		 * 
		 * 최대 공약수 : 공약수 중 가장 큰 수
		 * 8과 12의 공약수 : 4
		 * 
		 * 반복횟수 : i는 1부터 num1까지 1씩 증가
		 * 실행문 : i가 num1의 약수이고 i가 num2의 약수이면 i를 gcd변수에 저장
		 * => num1를 i로 나누었을 때 나머지가 0과 같고 num2를 i로 나누었을 때 나머지가 0과 같다면 i를 gcd에 저장
		 * 반복문 종료 후 : gcd 출력
		 * 
		 * */
		int num1 = 8, num2 = 12;
		int gcd = 1;
		for(int i = 1; i<=num1; i++) {
			if(num1 % i == 0 && num2 % i == 0) {
				gcd = i;
			}
		}
		System.out.println(num1 + "과 " + num2 + "의 최대 공약수 : " + gcd);
		
		System.out.println("----------------------------");
		
		/* 두 정수의 최소 공배수를 구하는 코드를 작성하세요.
		 * 8의 배수 : 8, 16, 24, 32, 40, 48,...
		 * 12의 배수 : 12, 24, 36, 48, 60, ...
		 * 8과 12의 공배수 : 24, 48, 72, ...
		 * 8과 12의 최소 공배수 : 24
		 * 반복횟수 : i는 num1부터 1씩 증가
		 * 실행문 : i가 num1의 배수이고, i가 num2의 배수이면 i를 출력하고 반복문 종료
		 * => i를 num1로 나누었을 때 나머지가 0과 같고, i를 num2로 나누었을 때 나머지가 0과 같다면
		 *    i를 출력하고 반복문 종료
		 * */
		for(int i = num1; ; i++) {
			if(i % num1==  0 && i % num2 == 0) {
				System.out.println(num1 + "과 " + num2 + "의 최소 공배수 : " + i);
				break;
			}
		}
		System.out.println("----------------------------");
		//반복횟수가 적고, 비교횟수가 적음
		for(int i = num1; ; i = i + num1) {
			if(i % num2 == 0) {
				System.out.println(num1 + "과 " + num2 + "의 최소 공배수 : " + i);
				break;
			}
		}
		System.out.println("----------------------------");
		/* 정수 num의 약수를 출력하는 코드를 작성하세요.
		 * 반복횟수 : i는 1부터 num까지 1씩 증가
		 * 실행문 : i가 num의 약수이면 i를 출력
		 * => num를 i로 나누었을 때 나머지가 0과 같다면 i를 출력
		 * */
		int num = 3;
		for(int i = 1 ; i <= num ; i++ ) {
			if(num % i == 0) {
				System.out.println(num + "의 약수 : " + i);
			}
		}
		System.out.println("----------------------------");
		/* 정수 num가 소수인지 아닌지 판별하는 코드를 작성하세요.
		 * 소수 : 약수가 1과 자기 자신인 수
		 * 2의 약수 : 1, 2 => 소수
		 * 3의 약수 : 1, 3 => 소수
		 * 4의 약수 : 1, 2, 4 => 소수 아님
		 * 5의 약수 : 1, 5 => 소수
		 * 6의 약수 : 1, 2, 3, 6 => 소수 아님 
		 * 
		 * 힌트 : 약수의 개수가 2개면 소수, 2개가 아니면 소수가 아님
		 * 반복횟수 : i는 1부터 num까지 1씩 증가
		 * 실행문 : i가 num의 약수이면 약수의 개수(count)를 1증가
		 * 반복문 종료 후 : count가 2이면 소수라고 출력하고 아니면 소수가 아님이라고 출력
		 * */
		int count = 0;
		for(int i = 1 ; i <= num ; i++ ) {
			if(num % i == 0) {
				++count;
			}
		}
		if(count == 2) {
			System.out.println(num +"는 소수");
		}
		else {
			System.out.println(num +"는 소수가 아님");
		}
		System.out.println("----------------------------");
		/* 반복횟수 : i는 2부터 num까지 1씩 증가
		 * 실행문 : i가 num의 약수이면 약수를 d에 저장후 반복문 종료
		 * 반복문 종료 후 : d와 num이 같으면 소수, 아니면 소수가 아님
		 * */
		int d = 1;
		for(int i = 2; i <= num; i++) {
			if(num % i == 0) {
				d = i;
				break;
			}
		}
		if(d == num) {
			System.out.println(num +"는 소수");
		}
		else {
			System.out.println(num +"는 소수가 아님");
		}
	}

}
