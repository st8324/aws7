import {BrowserRouter, Link, Route, Routes} from "react-router-dom";
import { Signup } from "./component/page/user/Signup";
import { Login } from "./component/page/user/Login";
import { Container, Nav, Navbar } from "react-bootstrap";

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
      <Navbar bg="light" expand="lg" className="shadow-sm mb-4">
        <Container>
          <Navbar.Brand as={Link} to="/">
            MyApp
          </Navbar.Brand>
          <Navbar.Toggle aria-controls="main-nav" />
          <Navbar.Collapse id="main-nav">
            <Nav className="ms-auto">
              <Nav.Link as={Link} to="/signup">
                회원가입
              </Nav.Link>
              <Nav.Link as={Link} to="/login">
                로그인
              </Nav.Link>
            </Nav>
          </Navbar.Collapse>
        </Container>
      </Navbar>
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
