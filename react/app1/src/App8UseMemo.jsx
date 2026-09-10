import { useMemo, useState } from "react";

//useMemo 설명 예제
function App8UseMemo(){

	const [num, setNum] = useState(0);
	const [page, setPage] = useState(0);

	//렌더링 버튼을 클릭하면 num이 바뀌기 때문에 렌더링은 되지만 계산을 다시하지 않음
	//페이지 변경 버튼을 클릭하면 page가 바뀌기 때문에 렌더링도 되고, 의존성 배열에 page를 추가 했기 때문에
	//계산을 다시 함
	const calc = useMemo(()=>{
		console.log("page를 이용해서 계산 중...\n시간이 오래걸리는 계산 중...(20초 걸림)")
		return page;
	}, [page])

	const arr1 = {
		name : "홍길동",
		age : 21
	}; //렌더링될때마다 
	let name = "홍길동";
	const arr2 = useMemo(()=>{
		return {
			name : name,
			age : 21
		}
	}, [name]); //name이 바뀔때마다 새 객체를 생성

	return(
		<div>
			<button onClick={()=>setNum(num+1)}>렌더링 버튼</button>
			<button onClick={()=>setPage(page+1)}>페이지 변경 버튼</button>
		</div>
	)
}

export default App8UseMemo;