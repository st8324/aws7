package kr.fast.diary.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "emotion_tag")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmotionTag {
	

	@Id
	@Column(name = "emotion_tag_id")
	Long id; 
	
	String name; 
	String emoji; 
	
	@Column(name = "display_order")
	int displayOrder;
	

	public EmotionTag(Long emoId) {
		this.id = emoId;
	}
}
