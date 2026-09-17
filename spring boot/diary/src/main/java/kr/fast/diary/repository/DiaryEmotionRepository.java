package kr.fast.diary.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.fast.diary.entity.DiaryEmotion;
import kr.fast.diary.entity.DiaryEmotionId;

public interface DiaryEmotionRepository 
	extends JpaRepository<DiaryEmotion, DiaryEmotionId> {

}
