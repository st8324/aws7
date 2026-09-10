import { Link } from "react-router-dom"
import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import NavDropdown from 'react-bootstrap/NavDropdown';

const NAV_LINKS = [
	{ type: "link", label : "홈", href : "/", role : ["GUEST", "USER"]},
	{ type: "link", label : "게시글", href : "/post/list", role : ["GUEST", "USER"]},
	{ type: "dropdown", label : "마이페이지", role : ["GUEST", "USER"], menus : [
		{ label : "로그인", href : "/login", role : ["GUEST"]},
		{ label : "회원가입", href : "/signup", role : ["GUEST"]},
		{ label : "로그아웃", href : "/logout", role : ["USER"]},
	]},
]

function Header(){
	const currentRole = "GUEST";
	const filteredLinks = NAV_LINKS.filter(nav => {
			return nav.role.includes(currentRole);
	});

	return (
		<Navbar expand="lg" className="bg-body-tertiary">
      <Container>
        <Navbar.Brand as={Link} to="/">Home</Navbar.Brand>
        <Navbar.Toggle aria-controls="basic-navbar-nav" />
        <Navbar.Collapse id="basic-navbar-nav">
          <Nav className="me-auto">
            {
              filteredLinks.map(link => {
                if (link.type === "link") {
                  return (
                    <Nav.Link as={Link} to={link.href} key={link.label}>
                      {link.label}
                    </Nav.Link>
                  );
                }
                
                if (link.type === "dropdown") {
                  // 2. 서브메뉴 중 currentRole에 맞는 항목만 필터링
                  const filteredSubMenus = link.menus.filter(menu => 
                    menu.role.includes(currentRole)
                  );

                  // 만약 해당 권한으로 표시할 서브메뉴가 없으면 드롭다운 표시 안 함
                  if (filteredSubMenus.length === 0) {
                    return null;
                  }

                  return (
                    <NavDropdown title={link.label} id={`dropdown-${link.label}`} key={link.label}>
                      {
                        filteredSubMenus.map(menu => (
                          <NavDropdown.Item as={Link} to={menu.href} key={menu.label}>
                            {menu.label}
                          </NavDropdown.Item>
                        ))
                      }
                    </NavDropdown>
                  );
                }
                return null;
              })
            }
          </Nav>
        </Navbar.Collapse>
      </Container>
    </Navbar>
	)
}

function Footer(){
	return (
		<footer>

		</footer>
	)
}

export {Header, Footer}