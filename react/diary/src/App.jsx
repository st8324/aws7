import {BrowserRouter, Link, Route, Routes} from "react-router-dom";
import { Signup } from "./component/page/user/Signup";

function App() {
  
  return (
    <BrowserRouter>
      <ul>
        <li>
          <Link to={"/"}>홈</Link>
        </li>
        <li>
          <Link to={"/signup"}>회원가입</Link>
        </li>
      </ul>
      <Routes>
        <Route path="/" element={<Home/>}></Route>
        <Route path="/signup" element={<Signup/>}></Route>
      </Routes>
    </BrowserRouter>
  );
}
function Home(){
  return <div><h1>홈</h1></div>
}


export default App;
