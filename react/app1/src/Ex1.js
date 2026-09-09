import { useState } from "react";


/* 
input 태그에 입력을 하고 확인 버튼을 누르거나 엔터를 치면
입력한 내용이 alert으로 띄도록 작업
- 단, 입력한 내용이 없으면 내용을 입력하세요가 alert으로 뜨도록 작업
- 이벤트 등록하는 방법
- 값을 입력했을 때 변수에 저장하는 방법(state)
*/
function Ex1(){

	const [datas, setDatas] = useState({text : ''});
	
	const submit = e =>{
		e.preventDefault();
		if(!datas.text || datas.text.length === 0){
			alert("내용을 입력하세요.");
			return;
		}
		alert(datas.text);
		//입력한 내용 지우기
		setDatas({...datas, text : ''})
	}
	const inputChange = e =>{
		const {name , value} = e.target;
		setDatas({...datas, [name] : value})
	}

	return (
		<form onSubmit={submit}>
			<input type="text" name="text" onChange={inputChange} value={datas.text} />
			<button>확인</button>
		</form>
	)
}

export default Ex1;