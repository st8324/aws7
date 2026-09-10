import { useEffect, useState } from "react";
import MyPagination from "./page/MyPagination";
import Container from "react-bootstrap/esm/Container";
import Spinner from 'react-bootstrap/Spinner';
import Table from 'react-bootstrap/Table';

async function getPosts(data, setPm, setIsLoading){
	try{
		const queryString = '?' + new URLSearchParams(data).toString();
		const response = await fetch("/api/posts" + queryString);
		if(!response.ok){
			return;
		}
		const result = await response.json();
		setPm(result);
		setIsLoading(false);
		
	}catch(e){
		console.error(e);
	}
}

function PostList(){
	//부트에서 dto.PageResponse클래스
	const [pm, setPm] = useState({});
	const [data, setData] = useState( {
		type : 'all', //검색 타입
		keyword : '', //검색어
		page : 0, // 현재 페이지 번호 -1
		size : 3, //한 페이지의 게시글 수
		sort : 'id,desc' //정렬 방법
	});
	const [isLoading, setIsLoading] = useState(true);

	useEffect(()=>{
		getPosts(data, setPm, setIsLoading);
	}, [data]);


	const clickHandler = (page)=>{
		page = page-1;
		setData({...data, page});
	}

	return (
		<Container>
			<h1>게시글</h1>
			{
				isLoading ?
				<Spinner animation="border" role="status">
					<span className="visually-hidden">Loading...</span>
				</Spinner> : 
				<>
					<PostsTable pm={pm}/>
					<MyPagination 
						startPage={pm.startPage} 
						endPage={pm.endPage}
						page={pm.page}
						hasNext={pm.hasNext}
						hasPrev={pm.hasPrev}
						click={clickHandler}
						/>
				</>
			}
			
		</Container>
	)
}

function PostsTable({pm}){
	return (
		<Table>
			<thead>
				<tr>
					<th>번호</th>
					<th>제목</th>
					<th>작성자</th>
					<th>작성일</th>
					<th>조회수</th>
					<th>추/비추</th>
				</tr>
			</thead>
			<tbody>
				{
					!pm || !pm.content|| pm.content.length === 0 ? 
						<tr>
							<th colSpan={6}>등록된 게시글이 없습니다.</th>
						</tr> 
						:
						pm.content.map(post=>{
							return (
								<tr key={post.id}>
									<td>{post.id}</td>
									<td>{post.title}</td>
									<td>{post.memberId}</td>
									<td>{post.createdAt}</td>
									<td>{post.view}</td>
									<td>{post.upCount}/{post.downCount}</td>
								</tr>
							)
						})
				}
			</tbody>	
		</Table>
	)
}

export default PostList;