import { Link } from "react-router-dom"

const NAV_LINKS = [
	{ label : "홈", href : "/", role : ["GUEST", "USER"]},
	{ label : "게시글", href : "/post/list", role : ["GUEST", "USER"]},
	{ label : "로그인", href : "/login", role : ["GUEST"]},
	{ label : "회원가입", href : "/signup", role : ["GUEST"]},
	{ label : "로그아웃", href : "/logout", role : ["USER"]},
]

function Header(){
	return (
		<header>
			<nav>
				<ul>
					{
						NAV_LINKS.map(nav=>{
							return (
								<li key={nav.label}>
									<Link to={nav.href}>{nav.label}</Link>
								</li>
							)
						})
					}
				</ul>
			</nav>
		</header>
	)
}

function Footer(){
	return (
		<footer>

		</footer>
	)
}

export {Header, Footer}