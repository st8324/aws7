import {BrowserRouter, Link, Route, Routes} from "react-router-dom";
import { Signup } from "./component/page/user/Signup";
import { Login } from "./component/page/user/Login";

function App() {
  
  

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
  return (
    <BrowserRouter>
      <ul>
        <li>
          <Link to={"/"}>홈</Link>
        </li>
        <li>
          <Link to={"/signup"}>회원가입</Link>
        </li>
        <li>
          <Link to={"/login"}>로그인</Link>
        </li>
      </ul>
      <Routes>
        <Route path="/" element={<Home/>}></Route>
        <Route path="/signup" element={<Signup/>}></Route>
        <Route path="/login" element={<Login/>}></Route>
      </Routes>
    </BrowserRouter>
  );
}
function Home(){
  return <div><h1>홈</h1></div>
}


export default App;
