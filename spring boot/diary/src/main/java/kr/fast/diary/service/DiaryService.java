package kr.fast.diary.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import kr.fast.diary.dto.DiaryDTO;
import kr.fast.diary.entity.Diary;
import kr.fast.diary.entity.DiaryEmotion;
import kr.fast.diary.entity.EmotionTag;
import kr.fast.diary.repository.DiaryEmotionRepository;
import kr.fast.diary.repository.DiaryRepository;
import kr.fast.diary.repository.EmotionTagRepository;
import kr.fast.diary.security.CustomUserDetails;
import kr.fast.diary.utils.FileUtils;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DiaryService {

	private final DiaryRepository diaryRepository;
	private final EmotionTagRepository emotionTagRepository;
	private final DiaryEmotionRepository diaryEmotionRepository;
	
	@Value("${file.path}")
	private String filePath;
	
	@Transactional
	public boolean insertDiary(DiaryDTO dto, CustomUserDetails userDetails, List<MultipartFile> files) {
		//사용자 체크
		if(userDetails == null) {
			throw new RuntimeException("로그인이 필요한 서비스입니다.");
		}
		//일기 항목 체크
		
		//이미지 업로드 후 업로드한 이미지 가져옴
		String savedFilename = "";
		if(files != null) {
			for(MultipartFile file : files) {
				if(file != null && !file.getOriginalFilename().isEmpty()) {
					String userFolder = "" + userDetails.getUserId()+"/";
					savedFilename += 
							FileUtils.saveFile(filePath+userFolder, file);				
					break; //왜?? 최대 1장이기 때문에 					
				}
			}
		}
		//다이어리 엔티티 객체 생성
		Diary diary = 
				new Diary(userDetails.getUserId(), dto.title(), dto.content(),
						dto.date(), dto.isPublic(), savedFilename);
		//저장
		Diary savedDiary;
		try {
			savedDiary = diaryRepository.save(diary);
		}catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException("이미 등록된 날짜의 일기이거나 서버에 이상이 있습니다.");
		}

		//일기의 감정 태그를 추가
		for(Long emoId : dto.emotions()) {
			DiaryEmotion diaryEmotion = new DiaryEmotion(emoId, savedDiary.getDiaryId());
			diaryEmotionRepository.save(diaryEmotion);
		}
		return true;
	}

	@Transactional
	public List<EmotionTag> getEmotionTags() {

		return emotionTagRepository.findAllByOrderByDisplayOrder();
	}

	public List<Diary> getDiaries(CustomUserDetails userDetails) {
		if(userDetails == null) {
			throw new RuntimeException();
		}
		return diaryRepository.findAllByUserIdOrderByDiaryDateDesc(userDetails.getUserId());
	}

	public List<Diary> getPublicDiaries(CustomUserDetails userDetails) {
		if(userDetails == null) {
			throw new RuntimeException();
		}
		return diaryRepository.findAllByIsPublicTrue();
	}
	
}
