import { createContext, useContext, useEffect, useState } from "react";

const AuthContext = createContext(null);

/*====================상수====================== */
//로컬 스토리지에 있는 토큰을 저장하거나 불러올때 사용할 이름
const ACCESS_TONEK_NAME = "accessToken"; 
/*============================================== */

function AuthProvider({children}){

	const [user, setUser] = useState({})

	const getMeAndSetUser = async()=>{
		try{
			const response = await fetch("/api/auth/me", {
				method : "GET",
				headers : {
					"Authorization" : "Bearer " + localStorage.getItem(ACCESS_TONEK_NAME)
				}
			});

			const result = await response.json();
			setUser(result);
		}catch(e){
			console.error(e);
		}
	}

	const authFetch = async (url, options = {}) =>{
		//로컬 스토리지에 저장된 accessToken명
		

		//매개변수로 넘겨준 option에서 headers를 복사
		const headers = {	...options.headers }

		const accessToken = localStorage.getItem(ACCESS_TONEK_NAME);

		if(accessToken){
			headers["Authorization"] = "Bearer " + accessToken;
		}
		//기존 옵션에 인증이 추가된 헤더로 수정
		const config = {
			...options,
			headers
		}
		const response = await fetch(url, config);
		if(response.status === 401 || response.status === 403){
			//리프레쉬 토큰을 이용하여 어세스토큰 재발급

			//어세스토큰을 재발급한 경우 authFetch 재실행

		}
		return response;
	}

	useEffect(()=>{
		getMeAndSetUser();
	}, [])

	return (
		<AuthContext.Provider value={{
				user, getMeAndSetUser, authFetch
			}}>
			{children}
		</AuthContext.Provider>
	)
}

const useAuth = () =>useContext(AuthContext);

export {AuthProvider, useAuth}