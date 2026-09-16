import { Container, Nav, Navbar } from "react-bootstrap";
import {Link} from "react-router-dom";

export function MyNav(){
	return(
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
						<Nav.Link as={Link} to="/diary/insert">
							일기 작성
						</Nav.Link>
					</Nav>
				</Navbar.Collapse>
			</Container>
		</Navbar>
	)
}