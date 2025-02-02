package com.ssafy.ourdoc.domain.user.service;

import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.ssafy.ourdoc.domain.user.dto.LoginRequest;
import com.ssafy.ourdoc.domain.user.dto.LoginResponse;
import com.ssafy.ourdoc.domain.user.dto.LoginResponse.UserInfo;
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
		Optional<User> existingUser = userRepository.findByLoginId(request.loginId());

		if (existingUser.isEmpty()) {
			return new LoginResponse("401", "로그인 실패: 아이디가 존재하지 않습니다.", null);
		}

		User user = existingUser.get();

		// 2) 비밀번호 해시 검사
		if (!BCrypt.checkpw(request.password(), user.getPassword())) {
			return new LoginResponse("401", "로그인 실패: 비밀번호가 틀렸습니다.", null);
		}

		// 3) userType 불일치 시 (선택적 체크)
		if (!user.getUserType().equals(request.userType())) {
			return new LoginResponse("401", "로그인 실패: 유저 타입이 일치하지 않습니다.", null);
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
