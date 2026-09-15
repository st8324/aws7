package kr.fast.diary.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.fast.diary.dto.MessageResponse;
import kr.fast.diary.dto.SignupDTO;
import kr.fast.diary.service.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
	
	private final UserService userService;
	
	@PostMapping("/users")
	public ResponseEntity<Object> users(@RequestBody SignupDTO dto){
		MessageResponse mr;
		try {
			boolean result = userService.signup(dto);
			mr = new MessageResponse(result, "회원 가입을 했습니다.");
		}catch (Exception e) {
			mr = new MessageResponse(false, e.getMessage());
		}
		return ResponseEntity.ok(mr);
	}
}
