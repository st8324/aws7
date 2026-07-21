package day02;

public class Ex06_문자열 {
	
	public static void main(String[] args) {
		/* String(문자열)
		 * - 참조변수
		 * - "" : 문자열, '': 문자
		 *   - js에서는 ''와 "" 모두 문자열
		 * - "" 안에 있는 글자들
		 * - 참조형 => 주소를 저장 => ==와 !=를 이용하여 비교하지 말자!
		 * - + 연산자를 이용하여 문자열들을 이어 붙일 수 있음
		 *   - 문자열 + 문자열 => 문자열
		 *   - 문자열 + 정수 => 문자열
		 *   - 정수 + 문자열 => 문자열
		 * */
		System.out.println("안녕하세요 : " + 6 + "번 예제입니다.");
		
		//참조형에는 ==와 !=를 사용하면 안되는 이유를 보여주는 예제
		
		//리터럴 홍길동은 상수풀에 저장이 되고, 상수풀에 저장된 홍길동의 주소를 str1에게 저장
		String str1 = "홍길동";
		//상수풀에서 홍길동을 찾아 있으면 주소를 가져와서 str2에 저장. 
		//만약 없으면 상수풀에 홍길동을 저장 후 주소를 가져와서 저장. 
		String str2 = "홍길동";
		//new String("홍길동")을 이용하면 new String()을 통해 홍길동 주소를 저장한 후,
		//홍길동 주소가 저장된 공간의 주소를 가져옴
		String str3 = new String("홍길동");
		
		System.out.println("str1 : " + str1);
		System.out.println("str2 : " + str2);
		System.out.println("str3 : " + str3);
		
		System.out.println("str1과 str2가 같습니까? " + (str1 == str2));
		System.out.println("str2과 str3가 같습니까? " + (str2 == str3));
		System.out.println("str1과 str3가 같습니까? " + (str1 == str3));
	
		String str4 = "";// 빈 문자열 가능
		System.out.println(str4);
		//char ch1 = '';//빈 문자는 없음. 공백문자는 가능
	}

}
