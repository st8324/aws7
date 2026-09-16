package kr.fast.diary.dto;

import java.time.LocalDate;
import java.util.List;

//import com.fasterxml.jackson.annotation.JsonFormat;

public record DiaryDTO(
	
	//@JsonFormat(pattern = "yyyy-MM-dd")
	LocalDate date, 
	String title, 
	String content, 
	boolean isPublic,
	List<Long> emotions
){}
