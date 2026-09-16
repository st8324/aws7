package kr.fast.diary.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.fast.diary.entity.EmotionTag;
import kr.fast.diary.service.DiaryService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class HomeController {
	
	private final DiaryService diaryService;
	
	@GetMapping("/api/emotion-tags")
	public ResponseEntity<Object> emotionTagsGet(){
		List<EmotionTag> list = diaryService.getEmotionTags();
		return ResponseEntity.ok(list);
	}
}
