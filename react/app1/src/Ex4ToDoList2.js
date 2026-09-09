import { useState } from "react";

function Ex4ToDoList2(){
	//밑에 todo는 input태그의 name과 같아야 함
	const [datas, setDatas] = useState({todo : ''});
	const [num, setNum] = useState(0);
	
	const submit = e =>{
		e.preventDefault();
		if(!datas.todo || datas.todo.length === 0){
			alert("내용을 입력하세요.");
			return;
		}

		setTodos([...todos, {
			num : num+1,
			todo : datas.todo
		}])
		setNum(num+1);
		//입력한 내용 지우기
		setDatas({...datas, todo : ''})
	}
	const inputChange = e =>{
		const {name , value} = e.target;
		setDatas({...datas, [name] : value})
	}

	const [todos, setTodos] = useState([]);
	return (
		<div>
			<form onSubmit={submit}>
				<input type="text" name="todo" onChange={inputChange} value={datas.todo}/>
				<button>등록</button>
			</form>
			<h1>오늘의 할일</h1>
			<ul>
				{
					todos.map((value, index)=>{
						return (
							<li key={value.num}>{value.todo}</li>
						)
					})
				}
			</ul>
		</div>
	)
}

export default Ex4ToDoList2;