import { Link } from "react-router-dom"
import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import NavDropdown from 'react-bootstrap/NavDropdown';

const NAV_LINKS = [
	{ label : "홈", href : "/", role : ["GUEST", "USER"]},
	{ label : "게시글", href : "/post/list", role : ["GUEST", "USER"]},
	{ label : "로그인", href : "/login", role : ["GUEST"]},
	{ label : "회원가입", href : "/signup", role : ["GUEST"]},
	{ label : "로그아웃", href : "/logout", role : ["USER"]},
]

function Header(){
	const currentRole = "GUEST";
	const filteredLinks = NAV_LINKS.filter(nav => {
		return nav.role.includes(currentRole)
	});

	return (
		<Navbar expand="lg" className="bg-body-tertiary">
      <Container>
        <Navbar.Brand as={Link} to={"/"}>Home</Navbar.Brand>
        <Navbar.Toggle aria-controls="basic-navbar-nav" />
        <Navbar.Collapse id="basic-navbar-nav">
          <Nav className="me-auto">
						{
							filteredLinks.map(link=>{
								return (
									<Nav.Link as={Link} to={link.href} key={link.label}>{link.label}</Nav.Link>
								)
							})
						}
            <NavDropdown title="Dropdown" id="basic-nav-dropdown">
              <NavDropdown.Item href="#action/3.1">Action</NavDropdown.Item>
              <NavDropdown.Item href="#action/3.2">
                Another action
              </NavDropdown.Item>
              <NavDropdown.Item href="#action/3.3">Something</NavDropdown.Item>
              <NavDropdown.Divider />
              <NavDropdown.Item href="#action/3.4">
                Separated link
              </NavDropdown.Item>
            </NavDropdown>
          </Nav>
        </Navbar.Collapse>
      </Container>
    </Navbar>
		// <header>
		// 	<nav>
		// 		<ul>
		// 			{
		// 				NAV_LINKS.map(nav=>{
		// 					return (
		// 						<li key={nav.label}>
		// 							<Link to={nav.href}>{nav.label}</Link>
		// 						</li>
		// 					)
		// 				})
		// 			}
		// 		</ul>
		// 	</nav>
		// </header>
	)
}

function Footer(){
	return (
		<footer>

		</footer>
	)
}

export {Header, Footer}