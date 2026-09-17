package kr.fast.diary.entity;

import java.time.LocalDate;
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
@Table(name = "diary")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Diary {
	
	
	@Id
	@Column(name = "diary_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long diaryId;
	@Column(name = "user_id")
	Long userId; 
	@Column(name = "diary_date")
	LocalDate diary_date; 
	String title; 
	String content; 
	@Column(name = "image_url")
	String imageUrl; 
	@Column(name = "is_public")
	boolean isPublic; 
	@Column(name = "created_at")
	LocalDateTime createdAt = LocalDateTime.now(); 
	@Column(name = "updated_at")
	LocalDateTime updatedAt = LocalDateTime.now();
	
	public Diary(Long userId, String title, String content, LocalDate date, boolean isPublic, String savedFilename) {
		this.userId = userId;
		this.title = title;
		this.content = content;
		this.diary_date = date;
		this.isPublic = isPublic;
		this.imageUrl = savedFilename;
	}

	public Diary(Long diaryId) {
		this.diaryId = diaryId;
	}
}
