package day02;

public class Ex02_연산자 {

	public static void main(String[] args) {
		/* 연산자
		 * 목표
		 * - 연산자 종류를 알고 결과를 예측할 수 있다.
		 * 
		 * 1. 대입 연산자(p.70) 
		 * - =
		 * - 오른쪽 값을 왼쪽에 저장
		 * - 오른쪽에는 식(연산자가 포함된)이 올수 있지만 왼쪽은 안됨
		 *   => x = 1 + y; //O
		 *   => 1 + y = x; //X
		 * 
		 * 2. 산술 연산자(p.72)
		 * - +(덧셈), -(뺄셈), *(곱셈), /(나눗셈), %(나머지)
		 * - 정수 연산자 정수 => 정수
		 *   - /을 조심. 1/2 => 0.5가 아닌 0
		 * - 정수 연산자 실수 => 실수
		 *   1.0 / 2 => 0.5  
		 * - 실수 연산자 정수 => 실수
		 * - 실수 연산자 실수 => 실수
		 * - A % B : A를 B로 나누었을 때 나머지
		 *   - 홀짝, 배수 판별, 약수 판별
		 * */
		
		int num = 10;
		num = num + 10;
		//위 연산이 끝나면 num는 20

		int num1 = 1, num2 = 2;
		//산술연산 결과
		System.out.println(num1 + " + " + num2 + " = " + (num1 + num2));
		System.out.println(num1 + " - " + num2 + " = " + (num1 - num2));
		System.out.println(num1 + " * " + num2 + " = " + (num1 * num2));
		// 1 / 2를 해서 결과가 0
		System.out.println(num1 + " / " + num2 + " = " + (num1 / num2));
		// 1 / 2.0을해서 결과가 0.5(자료형변환)
		System.out.println(num1 + " / " + (double)num2 + " = " + (num1 / (double)num2));
		System.out.println(num1 + " % " + num2 + " = " + (num1 % num2));
		
		/* 증감연산자(p.75)
		 * - 최종적으로 1증가/1감소
		 * - ++ : 1증가, --: 1감소
		 * - 전위형
		 *   - ++변수, --변수
		 *   - 변수를 증가시키고 일은 함
		 * - 후위형
		 *   - 변수++, 변수--
		 *   - 일을 하고 변수를 증가 시킴
		 *   
		 * */
		int num3 = 3, num4 = 3;
		System.out.println("--------------------");
		System.out.println("num3 : " + num3);
		System.out.println("num4 : " + num4);
		//num3이 증가를 한후, 증가한 num3의 값을 num5에 저장
		int num5 = ++num3;
		//num4의 값을 num6에 저장한 후, num4의 값을 증가
		int num6 = num4++;
		
		//예상되는 num5 : 4, num6 : 3
		System.out.println("++num3 : " + num5);
		System.out.println("num4++ : " + num6);
		System.out.println("num3 : " + num3);
		System.out.println("num4 : " + num4);
		System.out.println("--------------------");
		//num5,6처럼 결과가 되도록 작업
		num3 = 3;
		num4 = 3;
		int num7 = num3, num8 = num4;//일
		
		//num7 = ++num3; 이코드를 2줄로 나눈 코드
		++num3;//증가 시키고
		num7 = num3;//일
		
		//num8 = num4++; 이 코드를 2줄로 나눈 코드
		num8 = num4; //일하고
		++num4;//증가
		System.out.println("num7 : " + num7);
		System.out.println("num8 : " + num8);

	}

}
