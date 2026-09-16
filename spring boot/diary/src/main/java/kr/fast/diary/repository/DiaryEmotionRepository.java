package kr.fast.diary.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.fast.diary.entity.DiaryEmotion;

public interface DiaryEmotionRepository 
	extends JpaRepository<DiaryEmotion, Long> {

}
