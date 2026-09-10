import Pagination from 'react-bootstrap/Pagination';

function MyPagination({startPage, endPage, page, hasNext, hasPrev, click}){
	//startPage~endPage사이의 숫자를 배열로 생성
	//Array.from({length:숫자}, 함수) : 숫자번만큼 함수를 실행해서 나온 return값으로 배열을 만듬
	const pages = Array.from({length : endPage - startPage + 1}, (_, index) => startPage + index);
	
	return (
		<>
			<Pagination>
				<Pagination.Prev onClick={()=>click(startPage-1)} disabled={!hasPrev}/>
				{
					pages.map(p=>{
						return(
							<Pagination.Item key={p} active={page === p} onClick={()=>click(p)}>{p}</Pagination.Item>
						)
					})
				}		
				<Pagination.Next onClick={()=>click(endPage+1)} disabled={!hasNext}/>
			</Pagination>
		</>
	)
}

function Page({label, page, click, active}){
	return (
		<li onClick={()=>click(page)} className={`${active ? "active":""}`}>
			<a href="#" onClick={e=>e.preventDefault()}>{label}</a>
		</li>
	)
}

export default MyPagination;