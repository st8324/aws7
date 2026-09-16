package kr.fast.diary.entity;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@Data
public class DiaryEmotionId implements Serializable {

	private Long diaryId;
	private Long emotionTagId;
	
	public DiaryEmotionId(Long emoId, Long diaryId) {
		this.diaryId = diaryId;
		this.emotionTagId = emoId;
	}
}
