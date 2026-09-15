package kr.fast.diary.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.fast.diary.dto.UserDTO;
import kr.fast.diary.entity.Users;
import kr.fast.diary.repository.UsersRepository;
import kr.fast.diary.security.JwtProvider;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	
	private final BCryptPasswordEncoder encoder;
	private final UsersRepository usersRepository;
	private final JwtProvider jwtProvider;
	
	@Transactional
	public boolean signup(UserDTO dto) {
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

	@Transactional
	public String login(UserDTO dto) {

		//이메일을 이용하여 회원 정보 가져옴
		Users user = usersRepository.findByEmail(dto.email());
		
		//없으면 예외 발생
		if(user == null) {
			throw new RuntimeException("아이디 또는 비번이 일치하지 않습니다.");
		}
		
		//비번이 다르면 예외 발생
		if(!encoder.matches(dto.pw(), user.getPassword())) {
			throw new RuntimeException("아이디 또는 비번이 일치하지 않습니다.");
		}
		
		//accessToken 생성
		String accessToken 
			= jwtProvider.createToken(
					user.getEmail(), user.getNickname(), user.getUserId(), "USER");
		//accessToken 리턴
		return accessToken;
	}

}
