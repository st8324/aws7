package kr.fast.diary.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.fast.diary.dto.DiaryDTO;
import kr.fast.diary.entity.Diary;
import kr.fast.diary.repository.DiaryRepository;
import kr.fast.diary.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DiaryService {

	private final DiaryRepository diaryRepository;

	@Transactional
	public boolean insertDiary(DiaryDTO dto, CustomUserDetails userDetails) {
		//사용자 체크
		if(userDetails == null) {
			throw new RuntimeException("로그인이 필요한 서비스입니다.");
		}
		//일기 항목 체크
		
		//다이어리 엔티티 객체 생성
		Diary diary = 
				new Diary(userDetails.getUserId(), dto.title(), dto.content(),
						dto.date(), dto.isPublic());
		//저장
		diaryRepository.save(diary);
		return true;
	}
	
}
