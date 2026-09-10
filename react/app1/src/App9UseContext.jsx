import { AuthProvider, useAuth } from "./App9Context";

const style ={
	border : "1px solid black",
	padding : "20px"
}

function App9UseContext(){
	return(
		<AuthProvider>
			<A/>
		</AuthProvider>
	)
}

function A(){
	return( <div style={style}><B/></div>)
}
function B(){
	return( <div style={style}><C/></div>)
}
function C(){
	return( <div style={style}><D/></div>)
}
function D(){
	return( <div style={style}><E/></div>)
}
function E(){
	const {user, name} = useAuth(); 
	console.log(user, name);
	return( <div style={style}>
		<h1>E컴포넌트</h1>
		<h2>{user ? user.nickname : "익명"}님 환영합니다.</h2>
	</div>)
}



export {App9UseContext};