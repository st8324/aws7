package kr.fast.diary.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.fast.diary.entity.Diary;

public interface DiaryRepository extends JpaRepository<Diary, Long> {

	List<Diary> findAllByUserIdOrderByDiaryDateDesc(Long userId);

	List<Diary> findAllByIsPublicTrue();

}
