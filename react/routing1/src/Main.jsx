import { Route, Routes } from "react-router-dom";
import Login from "./Login";
import Logout from "./Logout";
import Signup from "./Signup";
import PostList from "./PostList";
import Home from "./Home";
import { Detail } from "./component/post/Detail";

function Main(){
	return (
		<main>
			<Routes>
				<Route path="/" element={<Home/>}/>
				<Route path="/login" element={<Login/>}/>
				<Route path="/logout" element={<Logout/>}/>
				<Route path="/signup" element={<Signup/>}/>
				<Route path="/post/list" element={<PostList/>}/>
				<Route path="/post/detail/:id" element={<Detail/>}/>
			</Routes>
		</main>
	)
}

export default Main;