package kr.fast.diary.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.fast.diary.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

	List<Comment> findAllByDiaryIdOrderByCommentIdDesc(Long diaryId);

}
