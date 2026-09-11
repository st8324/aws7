import { useEffect, useState } from "react";
import Container from "react-bootstrap/esm/Container";
import Form from 'react-bootstrap/Form';
import { Link, useParams } from "react-router-dom";

async function getPost(id, successFunc) {
	try{
		const response = await fetch(`/api/posts/${id}`);

		if(!response.ok){
			const result = await response.text();
			alert(result);
			return;
		}
		const result = await response.json();
		if(successFunc){
			successFunc(result);
		}
	}catch(e){
		console.error(e);
	}
}

export function Detail(){
	const {id} = useParams();
	const [post, setPost] = useState({
		title :'', memberId :'', createdAt : '', viewCount : '', upCount : '',
		downCount : '', content : '' , board : { name : ''}
	});
	const [files, setFiles] = useState([]);
	
	//서버에 게시글을 요청해서 게시글을 가져와 화면에 출력(/api/posts/게시글번호, GET)
	//1. fetch를 이용해 서버에 데이터 요청
	useEffect(()=>{
		getPost(id,(result)=>{
			//2. 서버에서 보낸 데이터를 state 변수에 저장
			setPost(result.post);
			setFiles(result.files);
		});
	}, []);

	async function fileDownload(savedName, originalName) {
		try{
			//서버에 첨부파일을 요청
			const response = await fetch(`/api/upload/${savedName}`);

			if(!response.ok){
				alert("파일을 불러오는데 실패했습니다.")
				return;
			}
			//첨부파일을 이용하여 다운로드 링크를 생성
			const blob = await response.blob();
			const downloadURL = window.URL.createObjectURL(blob);
			
			//a태그 생성. 다운로드 기능이 포함된
			const linkEl = document.createElement('a');

			linkEl.href = downloadURL;
			linkEl.download = originalName;
			document.body.appendChild(linkEl); //body태그 제일 마지막에 a태그 추가

			//a태그 클릭
			linkEl.click();

			//a태그 제거
			linkEl.remove();
			window.URL.revokeObjectURL(downloadURL);
		}	catch(e){
			console.error(e);
		}		
	}

	return (
		<Container>
			<h1>게시글 상세</h1>
			<Form>
				<Form.Group className="mb-3" >
					<Form.Label>제목</Form.Label>
					<Form.Control type="text" value={post.title} readOnly/>
				</Form.Group>
				<Form.Group className="mb-3" >
					<Form.Label>작성자</Form.Label>
					<Form.Control type="text" value={post.memberId} readOnly/>
				</Form.Group>
				<Form.Group className="mb-3" >
					<Form.Label>게시판</Form.Label>
					<Form.Control type="text" value={post.board.name} readOnly/>
				</Form.Group>
				<Form.Group className="mb-3" >
					<Form.Label>조회수</Form.Label>
					<Form.Control type="text" value={post.viewCount} readOnly/>
				</Form.Group>
				<Form.Group className="mb-3" >
					<Form.Label>작성일</Form.Label>
					<Form.Control type="text" value={post.createdAt} readOnly/>
				</Form.Group>
				<Form.Group className="mb-3">
					<Form.Label>내용</Form.Label>
					<Form.Control as="textarea" rows={10} value={post.content} readOnly/>
				</Form.Group>
			</Form>
			<Form.Group className="mb-3">
				{
					files.length === 0 ? 
					<Form.Control type="text" value={"등록된 첨부파일이 없습니다."} readOnly/>
					:
					files.map(file=>{
						return (
							<a href="#" key={file.savedName}
								//함수에 필요한 정보가 따로 있을 때 
								onClick={()=>fileDownload(file.savedName, file.originalName)} 
								// onClick={fileDownload} //함수에 필요한 정보가 없거나 이벤트 객체일 때
							>
								{file.originalName}
							</a>
						)
					})
				}
			</Form.Group>

			
		</Container>
	)
}
