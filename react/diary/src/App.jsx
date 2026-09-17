import {BrowserRouter, Route, Routes} from "react-router-dom";
import { Signup } from "./component/page/user/Signup";
import { Login } from "./component/page/user/Login";

import { useAuth } from "./provider/AuthContext";
import { MyNav } from "./component/layout/MyNav";
import { DiaryInsert } from "./component/page/diary/DiaryInsert";
import { DiaryList } from "./component/page/diary/DiaryList";

function App() {
  
  /*
  //확인용
  const submitHandler = async ()=>{
    const accessToken = localStorage.getItem("accessToken");
		try{
			const response = await fetch("/a", {
				method : "GET",
				headers : {
					"Authorization" : "Bearer " + accessToken
				}
			});
			const result = await response.json();
			console.log(result);
		}catch(e){
			console.error(e);
		}
	}
  submitHandler();
  */
  return (
    <BrowserRouter>
      <MyNav/>
      <Routes>
        <Route path="/" element={<Home/>}></Route>
        <Route path="/signup" element={<Signup/>}></Route>
        <Route path="/login" element={<Login/>}></Route>
        <Route path="/diary/insert" element={<DiaryInsert/>}></Route>
        <Route path="/diary/list" element={<DiaryList/>}></Route>
      </Routes>
    </BrowserRouter>
  );
}
function Home(){
  const {user} = useAuth();
  return (
    <div>
      <h1>홈</h1>
      {user && user.nickname ? <h2>{user.nickname}님 환영합니다.</h2> : <></>}
    </div>
  )
}


export default App;
