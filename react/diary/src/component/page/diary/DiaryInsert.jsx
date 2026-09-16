import { useState } from "react"

export function DiaryInsert(){
	const [emotions, setEmotions] = useState([
		{id : '1', name : '행복', emoji : '😊' }
	])
	const [data, setData] = useState({date :'', title : '', content : '', isPublic : ''})
	
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
			console.log(result);
		}catch(e){

		}
	}

	const intpustChange = e => setData({...data, [e.target.name] : e.target.value})

	return(
		<div>
			<h1>일기 작성</h1>
			<form onSubmit={submitHandler}>
				<input type="date" name="date" onChange={intpustChange}/> <br />
				<input type="text" name="title" onChange={intpustChange} /> <br />
				<textarea name="content" onChange={intpustChange}></textarea> <br />
				<input type="file" name="files"/> <br />
				<div>
					{
						emotions.map(e=>{
							return (
								<label>
									<input type="checkbox" name="emotions" value={e.id} /> {e.emoji}{e.name}
								</label>
							)
						})
					}
				</div>
				<label>
					<input type="checkbox" name="isPublic" value={true} onChange={intpustChange} />공개여부
				</label>
				<br />
				<button type="submit">일기 등록</button>
			</form>
		</div>
	)
}