import { useState } from "react";

//입력태그의 값을 가져오는 예제
function App4(){
	const [user, setUser] = useState({ id : '', pw : ''});
	const inputChange = e => {
		const {name, value} = e.target;
		setUser({...user, [name] : value})
	}
	
	const submit = e =>{
		e.preventDefault();
		console.log("서버에 데이터 전송중입니다....");
		console.log("전송 데이터", user);
	}
	return (
		<div>
			<form onSubmit={submit}>
				<input type="text" name="id" placeholder="아이디를 입력하세요." onChange={inputChange} />
				<br/>
				<input type="password" name="pw" placeholder="비밀번호를 입력하세요." onChange={inputChange}/>
				<br/>
				<button>로그인</button>
			</form>
		</div>
	)
}

export default App4;