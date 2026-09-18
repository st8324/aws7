package kr.fast.diary.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import kr.fast.diary.dto.CommentDTO;
import kr.fast.diary.dto.DiaryDTO;
import kr.fast.diary.dto.MessageResponse;
import kr.fast.diary.entity.Diary;
import kr.fast.diary.security.CustomUserDetails;
import kr.fast.diary.service.CommentService;
import kr.fast.diary.service.DiaryService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/diaries")
public class DiaryController {
	
	private final DiaryService diaryService;
	private final CommentService commentService;

	@PostMapping("")
	public ResponseEntity<Object> post(
			@AuthenticationPrincipal CustomUserDetails userDetails,
			@RequestPart("diary") DiaryDTO dto,
			@RequestPart(value="files", required = false) List<MultipartFile>files){
		MessageResponse mr;
		try{
			boolean isInsert = diaryService.insertDiary(dto, userDetails, files);
			mr = new MessageResponse(isInsert, "일기를 등록했습니다.");
		}catch(Exception e) {
			mr = new MessageResponse(false, e.getMessage());
		}
		return ResponseEntity.ok(mr);
	}
	
	@GetMapping("")
	public ResponseEntity<Object> get(
			@AuthenticationPrincipal CustomUserDetails userDetails){
		MessageResponse mr;
		try{
			List<Diary> diaries = diaryService.getDiaries(userDetails);
			return ResponseEntity.ok(diaries);
		}catch(Exception e) {
			return ResponseEntity.ok("[]");
		}
	}
	@GetMapping("/public")
	public ResponseEntity<Object> publicGet(){
		MessageResponse mr;
		try{
			List<Diary> diaries = diaryService.getPublicDiaries();
			return ResponseEntity.ok(diaries);
		}catch(Exception e) {
			return ResponseEntity.ok("[]");
		}
	}
	@GetMapping("/public/{id}")
	public ResponseEntity<Object> publicIdGet(@PathVariable("id") Long id){
		MessageResponse mr;
		try{
			Diary diary = diaryService.getDiary(id);
			return ResponseEntity.ok(diary);
		}catch(Exception e) {
			return ResponseEntity.ok("[]");
		}
	}
	@GetMapping("/{id}")
	public ResponseEntity<Object> idGet(
			@PathVariable("id") Long id,
			@AuthenticationPrincipal CustomUserDetails userDetails){
		MessageResponse mr;
		try{
			Diary diary = diaryService.getDiary(id, userDetails);
			return ResponseEntity.ok(diary);
		}catch(Exception e) {
			return ResponseEntity.ok("{}");
		}
	}
	
	@PostMapping("/{id}/comments")
	public ResponseEntity<Object> idCommentsPost(
			@AuthenticationPrincipal CustomUserDetails userDetails,
			@RequestBody CommentDTO dto){
		MessageResponse mr;
		try {
			commentService.insertComment(dto, userDetails);
			mr = new MessageResponse(true, "댓글을 등록했습니다.");
		}catch(Exception e) {
			e.printStackTrace();
			mr = new MessageResponse(false, e.getMessage());
		}
		return ResponseEntity.ok(mr);
	}
}
