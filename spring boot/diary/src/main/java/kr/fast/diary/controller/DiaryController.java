package kr.fast.diary.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.fast.diary.dto.DiaryDTO;
import kr.fast.diary.dto.MessageResponse;
import kr.fast.diary.security.CustomUserDetails;
import kr.fast.diary.service.DiaryService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/diaries")
public class DiaryController {
	
	private final DiaryService diaryService;

	@PostMapping("")
	public ResponseEntity<Object> post(
			@AuthenticationPrincipal CustomUserDetails userDetails,
			@RequestBody DiaryDTO dto){
		MessageResponse mr;
		try{
			boolean isInsert = diaryService.insertDiary(dto, userDetails);
			mr = new MessageResponse(isInsert, "일기를 등록했습니다.");
		}catch(Exception e) {
			mr = new MessageResponse(false, e.getMessage());
		}
		return ResponseEntity.ok(mr);
	}
}
