import { Route, Routes } from "react-router-dom";
import Login from "./Login";
import Logout from "./Logout";
import Signup from "./Signup";
import PostList from "./PostList";
import Home from "./Home";

function Main(){
	return (
		<main>
			<Routes>
				<Route path="/" element={<Home/>}/>
				<Route path="/login" element={<Login/>}/>
				<Route path="/logout" element={<Logout/>}/>
				<Route path="/signup" element={<Signup/>}/>
				<Route path="/post/list" element={<PostList/>}/>
			</Routes>
		</main>
	)
}

export default Main;