package com.ssafy.ourdoc.domain.user.service;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.ssafy.ourdoc.domain.user.dto.LoginRequest;
import com.ssafy.ourdoc.domain.user.dto.LoginResponse;
import com.ssafy.ourdoc.domain.user.dto.LoginResponse.UserInfo;
import com.ssafy.ourdoc.domain.user.dto.LogoutResponse;
import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.domain.user.repository.UserRepository;
import com.ssafy.ourdoc.global.exception.UserFailedException;
import com.ssafy.ourdoc.global.util.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final JwtUtil jwtUtil;

	// 1. 사용자 로그인
	public LoginResponse login(LoginRequest request) {

		// 1) 아이디로 User 조회
		User user = userRepository.findByLoginId(request.loginId())
			.orElseThrow(() -> new UserFailedException("로그인 실패: 아이디가 존재하지 않습니다."));

		// 2) 비밀번호 해시 검사
		if (!BCrypt.checkpw(request.password(), user.getPassword())) {
			throw new UserFailedException("로그인 실패: 비밀번호가 틀렸습니다.");
		}

		// 3) userType 불일치 시
		if (!user.getUserType().equals(request.userType())) {
			throw new UserFailedException("로그인 실패: 유저 타입이 일치하지 않습니다.");
		}

		// 4) JWT 토큰 생성
		String token = jwtUtil.createToken(user.getLoginId(), user.getUserType().toString());

		// 5) 로그인 성공
		return new LoginResponse(
			"200",
			"로그인 성공",
			token,
			new UserInfo(user.getLoginId(), user.getName(), user.getUserType().toString())
		);
	}

	// 2. ID 중복 체크
	public boolean isLoginIdDuplicate(String loginId) {
		return userRepository.findByLoginId(loginId).isPresent();
	}

	// 3. 로그아웃 서비스
	public LogoutResponse logout(String token) {

		// 1) 토큰이 유효한지 검증
		if (!jwtUtil.validateToken(token)) {
			throw new UserFailedException("로그아웃 실패: 유효하지 않은 토큰입니다.");
		}

		// 2) 토큰이 유효하다면 로그아웃 처리 (서버에서는 상태를 저장하지 않으므로 클라이언트 측에서 토큰 삭제)
		// 추가로, 필요하다면 DB나 Redis에서 이 토큰을 블랙리스트에 추가하거나 무효화 처리 가능
		return new LogoutResponse("200", "로그아웃 성공");
	}
}
