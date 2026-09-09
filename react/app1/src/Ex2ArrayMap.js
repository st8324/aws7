
function Ex2ArrayMap(){
	const arr = ["사과", "배", "포도"];

	return (
		<div>
			<ul>
				{arr.map((value, index)=>{
					return (
						<li key={index}>{value}</li>
					)
				})}
			</ul>
		</div>
	)
}

export default Ex2ArrayMap;