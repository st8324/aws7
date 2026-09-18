package kr.fast.diary.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.fast.diary.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
