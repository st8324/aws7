import { useEffect, useState } from "react"
import { Container, Card, Form, Row, Col, Button, ButtonGroup } from "react-bootstrap";
import { useNavigate } from "react-router-dom";

export function DiaryInsert(){

	const [emotions, setEmotions] = useState([
		{id : '1', name : '행복', emoji : '😊' }
	])
	const [data, setData] = useState({
		date :'', 
		title : '', 
		content : '', 
		isPublic : false,
		emotions : []
	})

	const navigate = useNavigate();
	
	const submitHandler = async (e)=>{
		e.preventDefault();
		//입력 안한 값 체크

		//서버에 전송. accessToken도 같이 보냄
		try{
			const response = await fetch("/api/diaries", {
				method : "POST", 
				headers : {
					"Content-Type" : "application/json",
					"Authorization" : "Bearer " + localStorage.getItem("accessToken")
				},
				body : JSON.stringify(data)
			})
			const result = await response.json();
			alert(result.message);
			if(result.success){
				navigate("/diary/list");
			}
		}catch(e){

		}
	}

	const loadEmotionTags = async ()=>{
		try{
			const response = await fetch("/api/emotion-tags");
			if(!response.ok){
				return;
			}
			const result = await response.json();
			setEmotions(result);
		}catch(e){
			console.error(e);
		}
	}

	const inputChange = e => setData({...data, [e.target.name] : e.target.value})
	const isPublicChange = e => setData({...data, [e.target.name] : e.target.checked})
	//감정 태그 클릭했을 때 처리
	const emotionChange = id => {
		let tmpEmotions = [...data.emotions];
		
		//선택된 감정을 클릭하면(해제)
		if(tmpEmotions.includes(id)){
			tmpEmotions = tmpEmotions.filter(emoId=> emoId !== id);
		}
		//선택 안된 감정을 클릭하면(추가)
		else{
			tmpEmotions.push(id);
		}
		setData({...data, emotions : tmpEmotions});
		
	}
	useEffect(()=>{
		loadEmotionTags();
	}, []);

	return(
		<Container className="py-4" style={{ maxWidth: "600px" }}>
			<Card className="shadow-sm">
				<Card.Body className="p-4">
					<Card.Title as="h1" className="mb-4 text-center fs-3">
						일기 작성
					</Card.Title>

					<Form onSubmit={submitHandler}>
						<Form.Group as={Row} className="mb-3" controlId="date">
							<Form.Label column sm={3}>날짜</Form.Label>
							<Col sm={9}>
								<Form.Control
									type="date"
									name="date"
									value={data.date}
									onChange={inputChange}
								/>
							</Col>
						</Form.Group>

						<Form.Group className="mb-3" controlId="title">
							<Form.Label>제목</Form.Label>
							<Form.Control
								type="text"
								name="title"
								placeholder="제목을 입력하세요"
								value={data.title}
								onChange={inputChange}
							/>
						</Form.Group>

						<Form.Group className="mb-3" controlId="content">
							<Form.Label>내용</Form.Label>
							<Form.Control
								as="textarea"
								name="content"
								rows={6}
								placeholder="오늘 하루는 어땠나요?"
								value={data.content}
								onChange={inputChange}
							/>
						</Form.Group>

						<Form.Group className="mb-3" controlId="files">
							<Form.Label>사진 첨부</Form.Label>
							<Form.Control type="file" name="files" />
							<Form.Text className="text-muted">
								최대 1장까지 첨부할 수 있습니다.
							</Form.Text>
						</Form.Group>

						<Form.Group className="mb-3">
							<Form.Label className="d-block">오늘의 감정</Form.Label>
							<ButtonGroup className="flex-wrap">
								{emotions.map((emo) => (
									<Button
										key={emo.id}
										variant={
											data.emotions.includes(emo.id) ? 
											"primary" :
											"outline-secondary"
										}
										onClick={() => {emotionChange(emo.id)}}
										className="me-2 mb-2 rounded-pill"
										type="button"
									>
										{emo.emoji} {emo.name}
									</Button>
								))}
							</ButtonGroup>
						</Form.Group>

						<Form.Group className="mb-4">
							<Form.Check
								type="switch"
								id="isPublic"
								name="isPublic"
								label="공개여부"
								checked={data.isPublic}
								onChange={isPublicChange}
							/>
						</Form.Group>

						<div className="d-grid">
							<Button type="submit" variant="primary" size="lg">
								일기 등록
							</Button>
						</div>
					</Form>
				</Card.Body>
			</Card>
		</Container>
	)
}