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
//배열을 이용하여 화면에 배치하는 예제
function App3(){
	const [posts, setPosts] = useState([]);
	const loadDatas = ()=> setPosts(datas);
	return (
		<div>
			<button onClick={loadDatas}>게시글 불러오기</button>
			<table border={1}>
				<thead>
					<th>번호</th>
					<th>제목</th>
					<th>작성자</th>
					<th>작성일</th>
					<th>조회수</th>
					<th>추/비추</th>
				</thead>
				<tbody>
					{
						posts.length == 0 ? 
							<tr>
								<th colSpan={6}>등록된 게시글이 없습니다.</th>
							</tr> 
							:
							posts.map(post=>{
								return (
									<tr>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
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