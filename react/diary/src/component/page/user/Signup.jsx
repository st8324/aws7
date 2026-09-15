import { useState } from "react";
import {useNavigate} from "react-router-dom";

export function Sginup(){

	const [data, setData] = useState({email :'', pw : '', pw2 : '', nickname : ''})

	const inputChange = (e) => setData({...data, [e.target.name] : e.target.value});

	const navigate = useNavigate();

	const submitHandler = async e=>{
		e.preventDefault();
		try{
			const response = await fetch("/api/auth/users", {
				method : "POST",
				headers : {
					"Content-Type" : "application/json"
				},
				body : JSON.stringify(data)
			});

			const result = await response.json();
			alert(result.message);
			if(result.success){
				navigate("/");
			}

		}catch(e){
			console.error(e);
		}
	}

	return (
		<div>
			<h1>회원가입</h1>
			<form onSubmit={submitHandler}>
				<input type="text" placeholder="이메일" name="email" onChange={inputChange} /> <br />
				<input type="password" placeholder="비번" name="pw" onChange={inputChange}/> <br />
				<input type="password" placeholder="비번 확인" name="pw2" onChange={inputChange}/> <br />
				<input type="text" placeholder="닉네임" name="nickname" onChange={inputChange}/> <br />
				<button>회원가입</button>
			</form>
		</div>
	)
}
