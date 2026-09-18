package kr.fast.diary.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "comment")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Comment {
	
	@Id
	@Column(name = "comment_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long commentId;
	
	@Column(name = "diary_id")
	Long diaryId;
	
	@Column(name = "user_id")
	Long userId;
	
	String content;
	
	@Column(name = "created_at")
	LocalDateTime createdAt = LocalDateTime.now();
	
	public Comment(Long diaryId, Long userId, String content) {
		this.diaryId = diaryId;
		this.userId = userId;
		this.content = content;
	}
}
