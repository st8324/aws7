package day02;

public class Ex04_상수와리터럴 {

	public static void main(String[] args) {
		/* 상수
		 *  - 변하지 않은 수
		 *  - 초기화를 1번만 할 수 있는 변수
		 *  - final 키워드를 이용
		 *  - 선언 방법
		 *    - final 자료형 변수명 = 초기값;
		 *  - 상수명은 대문자로, 문자 사이는 _로 구분
		 * 리터럴
		 *  - 값 : 1, 'a',
		 *  - 변수에 값을 초기화할 때 사용하는 값 
		 * 상수풀
		 *  - 상수와 리터럴은 상수풀에 저장되어 사용
		 */
		
		final int MAX_STUDENT; //MAX_STUDENT : 상수
		
		MAX_STUDENT = 10;//리터럴 10을 이용하여 상수 MAX_STUDENT의 값을 초기화
		//MAX_STUDENT = 20;//재할당 시도시 에러 발생
		System.out.println(MAX_STUDENT);

	}

}
