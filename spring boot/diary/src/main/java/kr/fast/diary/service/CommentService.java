package kr.fast.diary.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.fast.diary.dto.CommentDTO;
import kr.fast.diary.entity.Comment;
import kr.fast.diary.entity.Diary;
import kr.fast.diary.repository.CommentRepository;
import kr.fast.diary.repository.DiaryRepository;
import kr.fast.diary.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {
	
	private final CommentRepository commentRepository;
	private final DiaryRepository diaryRepository;

	@Transactional
	public void insertComment(CommentDTO dto, CustomUserDetails userDetails) {
		if(userDetails == null) {
			throw new RuntimeException("로그인이 필요한 서비스입니다.");
		}
		if(dto == null || dto.content() == null || dto.content().isEmpty()) {
			throw new RuntimeException("댓글을 입력하세요.");
		}
		//공개 일기가 맞는지 확인
		Diary diary = diaryRepository.findByDiaryId(dto.diaryId());
		if(diary == null || !diary.isPublic()) {
			throw new RuntimeException("일기가 없거나 공개 일기가 아닙니다.");
		}
		//엔티티 생성
		Comment comment = new Comment(dto.diaryId(), userDetails.getUserId(), dto.content());
		commentRepository.save(comment);
	}

}
