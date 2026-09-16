package kr.fast.diary.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "diary_emotion")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DiaryEmotion {
	
	@EmbeddedId
	DiaryEmotionId id;
	
	@JoinColumn(name = "diary_Id")
	@MapsId("diaryId") //DiaryEmotionId에 있는 diaryId
	@ManyToOne
	Diary diary;
	
	@JoinColumn(name = "emotion_tag_id")
	@MapsId("emotionTagId") //DiaryEmotionId에 있는 emotionTagId
	@ManyToOne
	EmotionTag emotionTag;
	
	public DiaryEmotion(Long emoId, Long diaryId) {
		id = new DiaryEmotionId(emoId, diaryId);
		diary = new Diary(diaryId);
		emotionTag = new EmotionTag(emoId);
	}
}
