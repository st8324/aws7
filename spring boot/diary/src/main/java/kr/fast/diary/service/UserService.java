package kr.fast.diary.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.fast.diary.dto.SignupDTO;
import kr.fast.diary.entity.Users;
import kr.fast.diary.repository.UsersRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	
	private final BCryptPasswordEncoder encoder;
	private final UsersRepository usersRepository;
	
	@Transactional
	public boolean signup(SignupDTO dto) {
		//유효성 체크는 생략
		
		//이메일 중복 체크
		boolean isExists = usersRepository.existsByEmail(dto.email());
		if(isExists) {
			throw new RuntimeException("이미 가입된 이메일입니다.");
		}
		//비밀번호 암호화
		String encodedPw = encoder.encode(dto.pw()); 
		
		//엔티티 생성
		Users user = new Users(dto.email(), encodedPw, dto.nickname());
		//레포.save(엔티티객체)
		usersRepository.save(user);
		return true;
	}

}
