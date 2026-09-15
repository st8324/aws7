package kr.fast.diary.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.fast.diary.dto.LoginResponse;
import kr.fast.diary.dto.MessageResponse;
import kr.fast.diary.dto.UserDTO;
import kr.fast.diary.service.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
	
	private final UserService userService;
	
	@PostMapping("/users")
	public ResponseEntity<Object> users(@RequestBody UserDTO dto){
		MessageResponse mr;
		try {
			boolean result = userService.signup(dto);
			mr = new MessageResponse(result, "회원 가입을 했습니다.");
		}catch (Exception e) {
			mr = new MessageResponse(false, e.getMessage());
		}
		return ResponseEntity.ok(mr);
	}
	
	@PostMapping("/login")
	public ResponseEntity<Object> login(@RequestBody UserDTO dto){
		LoginResponse lr;
		try {
			String accessToken = userService.login(dto);
			lr = new LoginResponse(true, "로그인을 했습니다.", accessToken);
		}catch (Exception e) {
			lr = new LoginResponse(false, e.getMessage(), null);
		}
		return ResponseEntity.ok(lr);
	}
}
