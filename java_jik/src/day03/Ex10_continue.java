package day03;

public class Ex10_continue {

	public static void main(String[] args) {
		/* continue
		 * - 반복문에서 continue를 만나면 지정 위치로 이동
		 * - 지정 위치
		 *   - for문 : 증감식
		 *   - while, do while문 : 조건식
		 * - if문과 함께 사용
		 */

		//1부터 10사이의 짝수를 출력 예제를 continue를 이용 => 홀수이면 스킵
		for(int i = 1; i <= 10; i++) {
			//짝수가 아니면 => 홀수이면 스킵
			if(i % 2 != 0) {
				continue;
			}
			System.out.println(i);
		}
		

	}

}
