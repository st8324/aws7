import { useState } from "react";
import {useNavigate} from "react-router-dom";
import { Container, Card, Form, Button } from "react-bootstrap";

export function Login(){

	const [data, setData] = useState({email :'', pw : ''})

	const inputChange = (e) => setData({...data, [e.target.name] : e.target.value});

	const navigate = useNavigate();

	const submitHandler = async e=>{
		e.preventDefault();
		try{
			const response = await fetch("/api/auth/login", {
				method : "POST",
				headers : {
					"Content-Type" : "application/json"
				},
				body : JSON.stringify(data)
			});

			const result = await response.json();
			alert(result.message);
			if(result.success){
        localStorage.setItem("accessToken", result.accessToken);
				navigate("/");
			}

		}catch(e){
			console.error(e);
		}
	}

	return (
    <div>
      <h1>로그인</h1>
      <form onSubmit={submitHandler}>
        <input type="text" name="email" onChange={inputChange} /> <br />
        <input type="password" name="pw" onChange={inputChange} /> <br />
        <button>로그인</button>
      </form>
    </div>
  );
}
