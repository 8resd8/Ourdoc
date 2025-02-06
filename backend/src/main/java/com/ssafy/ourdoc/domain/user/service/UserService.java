package com.ssafy.ourdoc.domain.user.service;

import static com.ssafy.ourdoc.global.common.enums.UserType.*;

import javax.security.auth.login.LoginException;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ssafy.ourdoc.domain.user.dto.LoginRequest;
import com.ssafy.ourdoc.domain.user.dto.LogoutResponse;
import com.ssafy.ourdoc.domain.user.dto.StudentLoginDto;
import com.ssafy.ourdoc.domain.user.dto.StudentQueryDto;
import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.domain.user.repository.UserRepository;
import com.ssafy.ourdoc.domain.user.student.entity.Student;
import com.ssafy.ourdoc.domain.user.student.repository.StudentQueryRepository;
import com.ssafy.ourdoc.domain.user.student.repository.StudentRepository;
import com.ssafy.ourdoc.global.config.JwtConfig;
import com.ssafy.ourdoc.global.exception.UserFailedException;
import com.ssafy.ourdoc.global.util.JwtBlacklistService;
import com.ssafy.ourdoc.global.util.JwtRefreshService;
import com.ssafy.ourdoc.global.util.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final JwtUtil jwtUtil;
	private final JwtConfig jwtConfig;
	private final JwtBlacklistService blacklistService;
	private final JwtRefreshService refreshService;
	private final StudentRepository studentRepository;
	private final StudentQueryRepository studentQueryRepository;

	// 1. 사용자 로그인
	public ResponseEntity<?> login(LoginRequest request) {
		// 유저 검증 (로그인 중복, 비밀번호)
		User user = validation(request);

		saveRefreshToken(user);

		if (request.userType().equals(학생)) {
			return loginStudent(request, user);
		} else if (request.userType().equals(교사)) {
			// return loginTeacher(request, user);
		} else {

		}
		// if 관리자일때

		// 그 외 예외던져

		throw new UserFailedException("알수없는 이유로 로그인 실패");
	}

	// private ResponseEntity<?> loginTeacher(LoginRequest request, User user) {
	// }

	private ResponseEntity<StudentLoginDto> loginStudent(LoginRequest request, User user) {
		Student student = studentRepository.findByUser(user);
		StudentQueryDto search = studentQueryRepository.getStudentLoginDto(user.getId());
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + getAccessToken(request));

		StudentLoginDto response = new StudentLoginDto(
			user.getLoginId(),
			user.getName(),
			user.getUserType(),
			search.schoolName(),
			search.grade(),
			search.classNumber(),
			search.studentNumber(),
			student.getTempPassword());

		return ResponseEntity.ok().headers(headers).body(response);
	}

	private void saveRefreshToken(User user) {
		String refreshToken = jwtUtil.createRefreshToken(user.getLoginId());
		refreshService.storeRefreshToken(user.getLoginId(), refreshToken, jwtConfig.getRefreshExpiration());
	}

	private User validation(LoginRequest request) {
		User user = userRepository.findByLoginId(request.loginId())
			.orElseThrow(() -> new UserFailedException("로그인 실패: 아이디가 존재하지 않습니다."));

		if (!BCrypt.checkpw(request.password(), user.getPassword())) {
			throw new UserFailedException("로그인 실패: 비밀번호가 틀렸습니다.");
		}

		if (!user.getUserType().equals(request.userType())) {
			throw new UserFailedException("로그인 실패: 유저 타입이 일치하지 않습니다.");
		}
		return user;
	}

	public String getAccessToken(LoginRequest request) {
		return jwtUtil.createToken(request.loginId(), request.userType().toString());
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

		// 2) 토큰 만료 시간 계산
		long expirationMillis = jwtUtil.getClaims(token).getExpiration().getTime() - System.currentTimeMillis();

		// 3) 블랙리스트에 추가
		blacklistService.addToBlacklist(token, expirationMillis);

		// 4) 로그아웃 처리 완료
		return new LogoutResponse("200", "로그아웃 성공");
	}
}
