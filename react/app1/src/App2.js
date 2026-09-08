import { useState } from "react";

// useState를 이용한 state 변수 설명 예제
function App2(){
	
	const arr = [1, 2, 3];
	let [num1, num2, num3] = arr;
	// let num1 = arr[0];
	// let num2 = arr[1];
	// let num3 = arr[2];
	console.log(num1, num2, num3);

	//useState는 배열을 리턴
	//0번지에는 변수값을, 1번지는 setter를 넣어줌
	//setter는 해당하는 변수의 값을 바꾸고 랜더링을 해줌
	//랜더링을 효율적으로 하기위해 state의 setter가 여러개인 경우
	//모아서 한번에 바꿈
	const [num, setNum] = useState(1);
	const [name, setName] = useState("");

	const changeNum = amount => {
		if(amount > 0){
			setNum(num=>num+1);
			setName("1");
		}
		else{
			setNum(num-1);
			setName("-1");
		}
		console.log(num);
	}

	return (
		<div>
			<button onClick={()=>changeNum(-1)}>-</button>
			<span>{num}</span>
			<button onClick={()=>changeNum(+1)}>+</button>
		</div>
	)
}

export {App2};