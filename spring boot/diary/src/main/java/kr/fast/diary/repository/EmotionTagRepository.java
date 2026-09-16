package kr.fast.diary.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.fast.diary.entity.EmotionTag;

public interface EmotionTagRepository extends JpaRepository<EmotionTag, Long> {

	List<EmotionTag> findAllByOrderByDisplayOrder();

}
