import { useState } from "react";

const datas = [
	{
		num : 2, 
		title : "공지입니다",
		memberId : "admin",
		createdAt : "2026-09-08",
		view : 0,
		upCount : 0, 
		downCount : 0
	},
	{
		num : 1, 
		title : "안녕하세요",
		memberId : "admin",
		createdAt : "2026-09-07",
		view : 10,
		upCount : 1, 
		downCount : 1
	}
]
// 배열을 이용하여 화면에 배치하는 예제
// 기존 js는 문자열로된 html코드를 만든 후 innerHTML로 했음
// 리액트에서는 배열의 map을 이용하여 화면 배치 

function App3(){
	const [posts, setPosts] = useState([]);
	const loadDatas = ()=> setPosts(datas);
	return (
		<div>
			<button onClick={loadDatas}>게시글 불러오기</button>
			<table border={1}>
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
						posts.length === 0 ? 
							<tr>
								<th colSpan={6}>등록된 게시글이 없습니다.</th>
							</tr> 
							:
							posts.map(post=>{
								return (
									<tr key={post.num}>
										<td>{post.num}</td>
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
			</table>
		</div>
	)
}

export default App3;