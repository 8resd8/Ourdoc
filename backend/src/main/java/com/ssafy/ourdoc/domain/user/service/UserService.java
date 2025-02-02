package com.ssafy.ourdoc.domain.user.service;

import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.ssafy.ourdoc.domain.user.dto.LoginRequest;
import com.ssafy.ourdoc.domain.user.dto.LoginResponse;
import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	// 1. 사용자 로그인
	public LoginResponse login(LoginRequest request) {

		// 1) 아이디로 User 조회
		Optional<User> existingUser = userRepository.findByLoginId(request.getLoginId());

		if (existingUser.isEmpty()) {
			return LoginResponse.builder()
				.resultCode("401")
				.message("로그인 실패: 아이디가 존재하지 않습니다.")
				.build();
		}

		User user = existingUser.get();

		// 2) 비밀번호 해시 검사
		if (!BCrypt.checkpw(request.getPassword(), user.getPassword())) {
			return LoginResponse.builder()
				.resultCode("401")
				.message("로그인 실패: 비밀번호가 틀렸습니다.")
				.build();
		}

		// 3) userType 불일치 시 (선택적 체크)
		if (!user.getUserType().toString().equals(request.getUserType())) {
			return LoginResponse.builder()
				.resultCode("401")
				.message("로그인 실패: 유저 타입이 일치하지 않습니다.")
				.build();
		}

		// 4) 로그인 성공
		return LoginResponse.builder()
			.resultCode("200")
			.message("로그인 성공")
			.user(
				LoginResponse.UserInfo.builder()
					.id(user.getLoginId())
					.name(user.getName())
					.role(user.getUserType().toString())
					.build()
			)
			.build();
	}
}
