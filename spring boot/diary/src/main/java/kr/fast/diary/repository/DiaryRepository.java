package kr.fast.diary.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.fast.diary.entity.Diary;

public interface DiaryRepository extends JpaRepository<Diary, Long> {

}
