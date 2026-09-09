import { useState } from "react";
import "./Ex5Menu.css";

const MENUS = [
	{ title : "메뉴1", link : "#"},
	{ title : "메뉴2", link : "#"},
	{ title : "메뉴3", link : "#"},
]

function Ex5Menu(){
	const [seletedcMenu, setSelectedMenu] = useState(MENUS[0].title);
	return (
		<div>
			<ul className="menu-list">
				{
					MENUS.map(menu=>{
						return (
							<li 
								className={`menu-item ${seletedcMenu === menu.title ? "active" : ""}`}
								onClick={()=>setSelectedMenu(menu.title)}
							>
								<a href={menu.link} className="menu-link">{menu.title}</a>
							</li>
						)
					})
				}
			</ul>
			<ul className="box-list">
				{
					MENUS.map(menu=>{
						return (
							<div className={`box-item ${seletedcMenu === menu.title ? "active" : ""}`}>{menu.title}</div>
						)
					})
				}
			</ul>
		</div>
	)
}
export default Ex5Menu;