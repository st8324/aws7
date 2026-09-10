import { useEffect, useState } from "react";

//useEffect 설명 예제
function App7UseEffect(){
	//useEffect(함수, 의존성 배열);
	//의존성 배열=> 언제 실행할 건지를 정함
	//의존성 배열이 없음 => 렌더링 할때마다 실행
	//의존성 빈 배열이 있음([]) => 처음 마운트할 때만 실행 => 컴포넌트가 처음 렌더링될때
	//의존성 배열에 변수가 있음 => 변수의 값이 바뀔때만 실행
	const [num, setNum] = useState(0);
	const [num2, setNum2] = useState(0);

	useEffect(()=>{
		console.log("의존성 배열이 없음");
	});

	useEffect(()=>{
		console.log("의존성 배열이 있음([])");
	},[]);

	useEffect(()=>{
		console.log("의존성 배열이 있음([num])");
	},[num]);

	return (
		<div>
			<button onClick={()=>setNum(num+1)} >렌더링1</button>
			<button onClick={()=>setNum2(num2+1)} >렌더링2</button>
		</div>
	)
}

export default App7UseEffect;