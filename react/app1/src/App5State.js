import { useState } from "react";
import "./App5State.css"

function App5State(){
	const [isReadOnly, setReadOnly] = useState(false);
	return (
		<div>
			<div>
				<button onClick={()=> setReadOnly(false)}>활성화</button>
				<button onClick={()=> setReadOnly(true)}>비활성화</button>
			</div>
			<div>
				<input type="text" disabled={isReadOnly} />
			</div>
			<div className={`box ${isReadOnly? "hidden" : ""}`}></div>
		</div>
	)
}

export default App5State;