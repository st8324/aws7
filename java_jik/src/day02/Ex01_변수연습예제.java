package day02;

public class Ex01_변수연습예제 {

	public static void main(String[] args) {
		//1학년 1반 11번 성이 '김'인 학생의 정보를 저장하기 위한 모든 변수를 선언하고, 저장하세요.
		int grade = 1;
		int classNum = 1;
		int num = 1;
		int grade2 = '1';
		char familyName = '김';
		
		System.out.println("" + grade + "학년 " + classNum + "반 " + num + "번 " 
				+  familyName);
		System.out.println("" + grade2 + "학년 " + classNum + "반 " + num + "번 " 
				+  familyName);
		System.out.println("" + (char)grade2 + "학년 " + classNum + "반 " + num + "번 " 
				+  familyName);
	}

}
