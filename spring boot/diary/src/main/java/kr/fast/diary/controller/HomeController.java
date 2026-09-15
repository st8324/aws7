package kr.fast.diary.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.fast.diary.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class HomeController {
	
	@GetMapping("/a")
	public ResponseEntity<Object> home(@AuthenticationPrincipal CustomUserDetails userDetails){
		
		return ResponseEntity.ok(userDetails);
	}
}
