package kr.fast.diary.utils;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

public class FileUtils {
	
	/**
	 * uploadFilePath에 파일(file)을 저장하고 저장된 경로 및 새로 변경된 파일명을 반환
	 * @param uploadFilePath 저장할 폴더 경로
	 * @param file 저장할 파일
	 * @return 서버에 저장된 폴더경로와 파일 명
	 */
	public static String saveFile(String uploadFilePath, MultipartFile file) {
		if(file == null || file.getOriginalFilename().isEmpty()) {
			throw new RuntimeException("첨부파일이 없습니다.");
		}
		
		//서버에 업로드할 경로가 없으면 경로를 생성
		java.io.File dir = new java.io.File(uploadFilePath);
		//해당 경로가 없으면 
		if(!dir.exists()) {
			//해당 경로에 필요한 폴더들을 만듬
			dir.mkdirs();
		}
			
		try {
			String oriFileName = file.getOriginalFilename();
			String savedFileName = UUID.randomUUID().toString()+"_" + oriFileName;
			
			File dest = new File(uploadFilePath + savedFileName);
			file.transferTo(dest);
			return savedFileName;
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("저장 중 예외가 발생했습니다.");
		}
		
	}
}
