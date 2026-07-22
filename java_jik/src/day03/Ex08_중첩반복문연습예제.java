package day03;

public class Ex08_중첩반복문연습예제 {

	public static void main(String[] args) {
		/* Ex06예제에서 했던 1~16출력하는 예제를 중첩 반복문으로 작성 
		 * i가 1일때 시작숫자가 1
		 * i가 2일때 시작숫자가 5
		 * 4 * i - 4 + 1
		 * */
		//4줄
		for(int i = 1; i <= 4; i++) {
			//숫자 4개를 출력
			for(int j = 1; j <= 4; j++) {
				System.out.print(4*(i-1) + j + " ");
			}
			//줄바꿈(엔터)
			System.out.println();
		}
	}

}
