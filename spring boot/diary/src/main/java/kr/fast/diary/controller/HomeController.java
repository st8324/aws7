package kr.fast.diary.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class HomeController {
	
	@GetMapping("/a")
	public ResponseEntity<Object> home(){
		
		return ResponseEntity.ok("home");
	}
}
