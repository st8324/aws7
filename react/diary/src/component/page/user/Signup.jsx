import { useState } from "react";
import {useNavigate} from "react-router-dom";
import { Container, Card, Form, Button } from "react-bootstrap";

export function Signup(){

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
    <Container className="d-flex justify-content-center align-items-center min-vh-100">
      <Card className="shadow-sm" style={{ width: "100%", maxWidth: "420px" }}>
        <Card.Body className="p-4">
          <Card.Title as="h1" className="h3 mb-4 text-center fw-bold">
            회원가입
          </Card.Title>

          <Form onSubmit={submitHandler}>
            <Form.Group className="mb-3" controlId="email">
              <Form.Label>이메일</Form.Label>
              <Form.Control
                type="text"
                placeholder="이메일을 입력하세요"
                name="email"
                value={data.email}
                onChange={inputChange}
              />
            </Form.Group>

            <Form.Group className="mb-3" controlId="pw">
              <Form.Label>비밀번호</Form.Label>
              <Form.Control
                type="password"
                placeholder="비밀번호를 입력하세요"
                name="pw"
                value={data.pw}
                onChange={inputChange}
              />
            </Form.Group>

            <Form.Group className="mb-3" controlId="pw2">
              <Form.Label>비밀번호 확인</Form.Label>
              <Form.Control
                type="password"
                placeholder="비밀번호를 다시 입력하세요"
                name="pw2"
                value={data.pw2}
                onChange={inputChange}
              />
            </Form.Group>

            <Form.Group className="mb-4" controlId="nickname">
              <Form.Label>닉네임</Form.Label>
              <Form.Control
                type="text"
                placeholder="닉네임을 입력하세요"
                name="nickname"
                value={data.nickname}
                onChange={inputChange}
              />
            </Form.Group>

            <Button type="submit" variant="primary" className="w-100">
              회원가입
            </Button>
          </Form>
        </Card.Body>
      </Card>
    </Container>
  );
}
