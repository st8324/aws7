import { useState } from "react";
import Button from "react-bootstrap/esm/Button";
import Container from "react-bootstrap/esm/Container";
import Form from 'react-bootstrap/Form';
import { useNavigate } from "react-router-dom";

function Signup(){

	const [data, setData] = useState({id :'', pw : '', pw2: '', email : ''})

	const naviage = useNavigate();

	const inputChange = e =>{
		const {name, value} = e.target;
		setData({...data, [name] : value})
	}
	
	//회원가입 버튼을 눌렀을 때 서버로 데이터를 전송하고 받는 코드
	const submitHander = async e=>{
		e.preventDefault();

		try{
			const response = await fetch("/api/auth/signup", {
				method : "POST",
				headers : {
					"Content-Type" : "application/json"
				},
				body : JSON.stringify(data)
			})
			const result = await response.json();
			alert(result.message);
			if(result.success){
				//메인페이지로 이동
				naviage("/")
			}
		}catch(e){
			console.error(e);
		}

	}

	return (
		<Container>
			<h1>회원가입</h1>
			<Form onSubmit={submitHander}>
				<Form.Group className="mb-3" >
					<Form.Label>아이디</Form.Label>
					<Form.Control type="text" name="id" onChange={inputChange}/>
				</Form.Group>
				<Form.Group className="mb-3" >
					<Form.Label>비번</Form.Label>
					<Form.Control type="password" name="pw" onChange={inputChange}/>
				</Form.Group>
				<Form.Group className="mb-3" >
					<Form.Label>비번확인</Form.Label>
					<Form.Control type="password" name="pw2" onChange={inputChange}/>
				</Form.Group>
				<Form.Group className="mb-3" >
					<Form.Label>이메일</Form.Label>
					<Form.Control type="email" name="email" onChange={inputChange}/>
				</Form.Group>
				<Button variant="outline-success" type="submit">회원가입</Button>
			</Form>
		</Container>
	)
}

export default Signup;