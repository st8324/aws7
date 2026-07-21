package day02;

public class Ex05_연산자2 {

	public static void main(String[] args) {
		/* 관계 연산자(비교 연산자) p.75
		 * - 숫자 크기 비교
		 * - 결과가 true 또는 false
		 * - >, <, <=, >=, ==, !=
		 * - js와 차이점
		 *   - !==, ===가 없음
		 *   - 문자열과 정수, 문자열과 문자를 비교할 수 없음
		 *     - js : "1" == 1 (비교 가능)
		 *     - 자바: "1" == 1 (에러 발생)
		 */
		
		System.out.println("1 < 2 : " + (1 < 2));
		//System.out.println("\"1\" < 2 : " + ("1" < 2)); //에러 발생:문자열과 숫자는 비교 불가

		/* 논리 연산자 p.76
		 * - &&, ||, !
		 * - && : ~이고
		 *   - 둘 다 참이면 참. 하나라도 거짓이면 거짓
		 *   - 성적이 80점이상이고 90점미만이면 학점 B
		 * - || : ~이거나
		 *   - 둘 다 거짓이면 거짓. 하나라도 참이면 참
		 *   - 면허증이 있거나 민증이 있거나 여권이 있으면 토익 응시가 가능
		 * - ! : 반대
		 *   - 참이면 거짓, 거짓이면 참
		 *   - 성인이 아니다 => 미성년자다 
		 * - 주의사항
		 *   - js와 약간 다름
		 *   - js는 1 || 0 => true || false로 인식 => 가능
		 *   - 자바는 무조건 연산자를 기준으로 오른쪽과 왼쪽에 true, false만 와야 함
		 * 
		 * */
		//성적이 90이상 100이하이면 A일 때 &&, ||, ! 중에 어떤 연산을 써야 할까요?
		//정답은 &&. 성적이 90이상(이고) 100이하이다라고 표현
		
		//졸업하려면 토익이 600이상이거나 영어회화 과목을 이수해야 한다.
		//정답은 || 
		int score = 85;
		//이상은 크거나 같다. 이하는 작거나 같다로 표현
		System.out.println(score + "점은 A? " + (score >= 90 && score <= 100));
		
		int toeic = 550;
		boolean passEnglish = false;//영어 회화 이수 여부
		System.out.println("토익 성적 : " + 650);
		System.out.println("영어 회화 이수 : " + passEnglish);
		System.out.println("졸업이 가능? " + (toeic >= 600 || passEnglish));
		
		int age = 20;
		System.out.println(age + "살은 성인입니까? " + (age >= 19));
		System.out.println(age + "살은 미성년자입니까? " + !(age >= 19));
		System.out.println(age + "살은 미성년자입니까? " + (age < 19));
		
		/* 단락 회로 평가(SCE : Short Circuit Evaluation) p.77
		 * - 논리 연산자로 연산할 때 발생
		 * - 속도 향상
		 * - || 연산 : 하나라도 참이면 참 => 앞 식이 참이면 뒤에거 볼 필요 없음
		 * - && 연산 : 하나라도 거짓이면 거짓 => 앞 식이 거짓이면 뒤에거 볼 필요 없음
		 */
		
		
		int num1 = 70, num2 = 70;
		//|| 앞에 식이 참이어서 뒤에 num2 = 60이 실행되지 않아 num2의 값이 바뀌지 않음
		System.out.println("num1과 num2 중 하나라도 60이상입니까? "
			+ (num1 >= 60 || (num2 = 60) >= 60));
		System.out.println(num1 + ", " + num2);
		
		/* 복합 대입 연산자 p.78
		 * - A = A 연산자 B로 되어 있을 때 사용
		 * - A 연산자= B;
		 * - 왜?
		 *   => 간단히 입력하려고
		 *   - a = a + b와 a+=b를 입력했을 때 속도 차이
		 * box.innerHTML = box.innerHTML + str;
		 * box.innerHTML +=  str;
		 * */
		int num3 = 10;
		//num3 = num3 + 20;
		num3 += 20;
		System.out.println(num3);
		
		/* 조건 연산자 p.79
		 * - 조건식 ? 참 : 거짓;
		 * - 조건문을 짧게 줄여 처리할 수 있는 연산자
		 * - 몰라도 상관 없지만 알면 한줄로 깔끔하게 작성할 수 있는 경우가 있음
		 * */
		//짝수 홀수 예제
		int num4 = 10;
		
		boolean isEven = num4 % 2 == 0 ? true : false;
		String even = num4 % 2 == 0 ? "짝수" : "홀수";
		
		System.out.println(num4 + "은 짝수? " + isEven);
		System.out.println(num4 + "은 " + even);
		
		/* 비트 연산자 p.81
		 * - 비트 논리 연산자
		 *   - 결과가 어떤 정수 값이 나옴
		 *   - 정수를 2진수로 표현한 후 각 비트를 나열하여 논리 연산
		 *   - 1 : true, 0 : false
		 *   - |, &, ~, ^(xor)
		 *     - | : or
		 *     - & : and
		 *     - ~ : not
		 *     - ^ : 베타 논리. 같으면 0, 다르면 1
		 * - 비트 쉬프트 연산자
		 *   - 정수를 2진수로 표현한 후 각 비트를 지정된 수만큼 이동(왼쪽, 오른쪽)
		 *   - a << n : n비트씩 왼쪽으로 이동, 2^n 곱한 효과
		 *     - 4 << 3 : 4 * 2^3 => 4 * 8 => 32
		 *   - a >> n : n비트씩 오른쪽으로 이동, 2^n 나눈 효과. 이동후 빈 왼쪽은 부호 비트로 채움
		 *   - a >>> n : n비트씩 오른쪽으로 이동, 이동후 빈 왼쪽은 0으로 채움
		 */
		/* 12 : 00001100
		 * 10 : 00001010
		 *      00001110
		 *  
		 */
		System.out.println(12 | 10);
		/* 15:  00001111
		 * 257: 11111111
		 * 0  : 00000000
		 * */
	}

}
