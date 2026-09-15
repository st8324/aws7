import { createContext, useContext, useEffect, useState } from "react";

const AuthContext = createContext(null);

function AuthProvider({children}){

	const [user, setUser] = useState({})

	const getMeAndSetUser = async()=>{
		try{
			const response = await fetch("/api/auth/me", {
				method : "GET",
				headers : {
					"Authorization" : "Bearer " + localStorage.getItem("accessToken")
				}
			});

			const result = await response.json();
			setUser(result);
		}catch(e){
			console.error(e);
		}
	}

	useEffect(()=>{
		getMeAndSetUser();
	}, [])

	return (
		<AuthContext.Provider value={{
				user, getMeAndSetUser
			}}>
			{children}
		</AuthContext.Provider>
	)
}

const useAuth = () =>useContext(AuthContext);

export {AuthProvider, useAuth}