package day02;

public class Ex13_switch문 {

public static void main(String[] args) {
		
		/* switch문(p.98)
		 * - 조건문 중 하나
		 * - 모든 조건문을 처리할 수 없음
		 * - 특정 값과 비교하는 경우 사용
		 * - break가 없으면 다음 case의 실행문으로 이동하니 주의
		 * - 값은 문자, 정수, 문자열만 올 수 있음
		 * 
		 * 문법
		 * switch(식 또는 변수){
		 * 	case 값1: //변수(식) == 값1면 실행문1을 실행
		 * 		실행문1;
		 * 		break;//switch문을 빠져 나옴
		 * 	case 값2: //변수가 값1과 다르고 변수 == 값2이면 실행문2를 실행
		 * 		실행문2;
		 * 		break;//switch문을 빠져 나옴
		 * 		...
		 * 	default://else : 변수가 값1,값2가 아니면 실행문3을 실행
		 * 		실행문3;
		 * }
		 * - case 옆에 값으로 정수, 문자, 문자열 상수(리터럴 포함)만 가능
		 * */
		
		//산술연산자이면 산술연산을 하는 예제
		int num1 = 1, num2 = 2;
		char op = '+';
		
		switch(op) {
		case '+': //if(op == '+')
			System.out.println(num1 + num2);
			break;
		case '-': 
			System.out.println(num1 - num2);
			break;
		case '*': 
			System.out.println(num1 * num2);
			break;
		case '/': 
			System.out.println(num1 / (double)num2);
			break;
		case '%': 
			System.out.println(num1 % num2);
			break;
		default:
			System.out.println(op+"는 산술 연산자 아님");
		}
	}
}
