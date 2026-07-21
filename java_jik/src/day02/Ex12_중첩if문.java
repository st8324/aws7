package day02;

public class Ex12_중첩if문 {

	public static void main(String[] args) {
		/* 중첩 if문
		 * - if문의 실행문으로 if문이 오는 경우
		 * - 중첩 if문 문법
		 * if(조건식1){
		 * 		if(조건식2){
		 * 			실행문1;
		 * 		}
		 * 		else{
		 * 			실행문2;
		 * 		}
		 * }
		 * - 중첩 if문을 if문으로 표현
		 * if(조건식1 && 조건식2){
		 * 		실행문1;
		 * }
		 * else if(조건식1){
		 * 		실행문2;
		 * }
		 * 
		 * if(조건식){
		 * 		if문;
		 * }
		 * 
		 * */
		int num1 = 1, num2 = 0;
		char op = '%';
		if(op == '/') {
			if(num2 != 0) {
				System.out.println(num1 / num2);
			}
			else {
				System.out.println("0으로 나눌 수 없습니다.");
			}
		}else if(op == '%') {
			if(num2 != 0) {
				System.out.println(num1 % num2);
			}
			else {
				System.out.println("0으로 나눌 수 없습니다.");
			}
		}
		

	}

}
