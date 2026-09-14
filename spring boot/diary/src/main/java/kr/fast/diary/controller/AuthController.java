package kr.fast.diary.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.fast.diary.dto.SignupDTO;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@PostMapping("/users")
	public ResponseEntity<Object> users(@RequestBody SignupDTO dto){
		
		return ResponseEntity.ok("{}");
	}
}
