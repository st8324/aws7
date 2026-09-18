import axios from "axios";

const api = axios.create({
	baseURL : "/api",
	withCredentials : true, //모든 요청에 쿠키 자동 포함
});
//헤더에 토큰을 실어 보냄
api.interceptors.request.use((config)=>{
	const token = localStorage.getItem("accessToken");
	if(token){
		config.headers.Authorization = "Bearer " + token;
	}
	return config;
})

export default api;