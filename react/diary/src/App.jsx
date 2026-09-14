import {BrowserRouter, Link, Route, Routes} from "react-router-dom";

function App() {
  
  return (
    <BrowserRouter>
      <ul>
        <li>
          <Link to={"/"}>홈</Link>
        </li>
        <li>
          <Link to={"/test"}>테스트</Link>
        </li>
      </ul>
      <Routes>
        <Route path="/" element={<Home/>}></Route>
        <Route path="/test" element={<Test/>}></Route>
      </Routes>
    </BrowserRouter>
  );
}
function Home(){
  return <div><h1>홈</h1></div>
}
function Test(){
  return <div><h1>테스트</h1></div>
}


export default App;
