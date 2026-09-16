package kr.fast.diary.dto;

import java.time.LocalDate;

//import com.fasterxml.jackson.annotation.JsonFormat;

public record DiaryDTO(
	
	//@JsonFormat(pattern = "yyyy-MM-dd")
	LocalDate date, 
	String title, 
	String content, 
	boolean isPublic
){}
