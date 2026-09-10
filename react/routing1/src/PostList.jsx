import { useState } from "react";
import Pagination from "./page/Pagination";

function PostList(){
	const posts = [
		{	num : 2, title : "공지2", memberId : "admin", createdAt : "2026-09-10", 
			view : 10, upCount:0, downCount:0},
		{	num : 1, title : "공지1", memberId : "admin", createdAt : "2026-09-09", 
			view : 100, upCount:0, downCount:0},
	];
	//부트에서 dto.PageResponse클래스
	const [pm, setPm] = useState({
		content : posts,
		page : 1, 
		pageSize : 10, 
		totalContentSize : 2, 
		totalPages : 1,
		startPage : 1,
		endPage : 1, 
		hasNext : false,
		hasPrev : false, 
		pageBlockSize : 10,
	})
	return (
		<div>
			<h1>게시글</h1>
			<table>
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
			<Pagination 
				startPage={pm.startPage} 
				endPage={pm.endPage}
				page={pm.page}
				hasNext={pm.hasNext}
				hasPrev={pm.hasPrev}
				/>
		</div>
	)
}

export default PostList;