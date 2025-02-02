package com.ssafy.ourdoc.domain.user.service;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.ssafy.ourdoc.domain.user.dto.LoginRequest;
import com.ssafy.ourdoc.domain.user.dto.LoginResponse;
import com.ssafy.ourdoc.domain.user.dto.LoginResponse.UserInfo;
import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.domain.user.repository.UserRepository;
import com.ssafy.ourdoc.global.exception.LoginFailedException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	// 1. 사용자 로그인
	public LoginResponse login(LoginRequest request) {

		// 1) 아이디로 User 조회
		User user = userRepository.findByLoginId(request.loginId())
			.orElseThrow(() -> new LoginFailedException("로그인 실패: 아이디가 존재하지 않습니다."));

		// 2) 비밀번호 해시 검사
		if (!BCrypt.checkpw(request.password(), user.getPassword())) {
			throw new LoginFailedException("로그인 실패: 비밀번호가 틀렸습니다.");
		}

		// 3) userType 불일치 시
		if (!user.getUserType().equals(request.userType())) {
			throw new LoginFailedException("로그인 실패: 유저 타입이 일치하지 않습니다.");
		}

		// 4) 로그인 성공
		return new LoginResponse(
			"200",
			"로그인 성공",
			new UserInfo(user.getLoginId(), user.getName(), user.getUserType().toString())
		);
	}

	// 2. ID 중복 체크
	public boolean isLoginIdDuplicate(String loginId) {
		return userRepository.findByLoginId(loginId).isPresent();
	}
}
