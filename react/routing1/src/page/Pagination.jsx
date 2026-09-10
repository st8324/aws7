
function Pagination({startPage, endPage, page, hasNext, hasPrev}){
	//startPage~endPage사이의 숫자를 배열로 생성
	//Array.from({length:숫자}, 함수) : 숫자번만큼 함수를 실행해서 나온 return값으로 배열을 만듬
	const pages = Array.from({length : endPage - startPage + 1}, (_, index) => startPage + index);
	
	return (
		<ul className="pagination">
			{
				hasPrev ? //hasPrev가 참이면 Page 컴포넌트를 배치, 아니면 null(없음)을 배치
					<Page label={"이전"} page={startPage-1} click={alert} active={false}/> 
					: 
					null
			}
			{
				pages.map(p=>{
					return(<Page label={p} page={p} click={alert} active={page == p}/>)
				})
			}
			{hasNext ? <Page label={"다음"} page={endPage-1} click={alert} active={false}/> : null}
		</ul>
	)
}

function Page({label, page, click, active}){
	return (
		<li onClick={()=>click(page)} className={`${active ? "active":""}`}>
			<a href="#" onClick={e=>e.preventDefault()}>{label}</a>
		</li>
	)
}

export default Pagination;