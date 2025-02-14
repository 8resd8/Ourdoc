package com.ssafy.ourdoc.domain.user.service;

import static com.ssafy.ourdoc.global.common.enums.AuthStatus.*;
import static com.ssafy.ourdoc.global.common.enums.EmploymentStatus.*;
import static com.ssafy.ourdoc.global.common.enums.UserType.*;

import java.util.NoSuchElementException;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ssafy.ourdoc.domain.user.dto.CheckIdRequest;
import com.ssafy.ourdoc.domain.user.dto.LoginRequest;
import com.ssafy.ourdoc.domain.user.dto.LogoutResponse;
import com.ssafy.ourdoc.domain.user.dto.StudentLoginDto;
import com.ssafy.ourdoc.domain.user.dto.StudentQueryDto;
import com.ssafy.ourdoc.domain.user.dto.TeacherLoginDto;
import com.ssafy.ourdoc.domain.user.dto.TeacherQueryDto;
import com.ssafy.ourdoc.domain.user.dto.request.ChangePasswordRequest;
import com.ssafy.ourdoc.domain.user.dto.request.CheckPasswordRequest;
import com.ssafy.ourdoc.domain.user.dto.response.AdminLoginDto;
import com.ssafy.ourdoc.domain.user.dto.response.TeacherNotInClassLoginDto;
import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.domain.user.repository.UserRepository;
import com.ssafy.ourdoc.domain.user.student.entity.Student;
import com.ssafy.ourdoc.domain.user.student.repository.StudentQueryRepository;
import com.ssafy.ourdoc.domain.user.student.repository.StudentRepository;
import com.ssafy.ourdoc.domain.user.teacher.repository.TeacherQueryRepository;
import com.ssafy.ourdoc.domain.user.teacher.repository.TeacherRepository;
import com.ssafy.ourdoc.global.common.enums.AuthStatus;
import com.ssafy.ourdoc.global.common.enums.EmploymentStatus;
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
	private final TeacherRepository teacherRepository;
	private final StudentQueryRepository studentQueryRepository;
	private final TeacherQueryRepository teacherQueryRepository;

	public ResponseEntity<?> login(LoginRequest request) {
		User user = validation(request);

		if (request.userType().equals(학생)) {
			return loginStudent(request, user);
		} else if (request.userType().equals(교사)) {
			return loginTeacher(request, user);
		} else if (request.userType().equals(관리자)) {
			return loginAdmin(request, user);
		}

		throw new UserFailedException("알수없는 이유로 로그인 실패");
	}

	private ResponseEntity<?> loginTeacher(LoginRequest request, User user) {
		checkEmploymentStatus(user);

		TeacherQueryDto search = teacherQueryRepository.getTeacherLoginDto(user.getId());
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + getAccessToken(request));

		saveRefreshTokenAndSetCookie(user, headers);
		String profileImagePath = user.getProfileImagePath() == null ? "" : user.getProfileImagePath();

		if (search != null) {
			TeacherLoginDto response = new TeacherLoginDto(user.getLoginId(), user.getName(), user.getUserType(),
				search.schoolName(), search.schoolId(), search.classId(), search.grade(), search.classNumber(), profileImagePath);
			return ResponseEntity.ok().headers(headers).body(response);
		} else if (search == null) {
			TeacherNotInClassLoginDto response = new TeacherNotInClassLoginDto(user.getLoginId(), user.getName(),
				user.getUserType(), profileImagePath);
			return ResponseEntity.ok().headers(headers).body(response);
		} else {
			throw new UserFailedException("알 수 없는 이유로 로그인 실패");
		}
	}

	private void checkEmploymentStatus(User user) {
		if (teacherRepository.findByUser(user).getEmploymentStatus().equals(비재직)) {
			throw new UserFailedException("승인되지 않은 교사입니다.");
		} else if (teacherRepository.findByUser(user).getEmploymentStatus() == null) {
			throw new UserFailedException("알 수 없는 이유로 로그인 할 수 없습니다.");
		}
	}

	private ResponseEntity<StudentLoginDto> loginStudent(LoginRequest request, User user) {
		Student student = studentRepository.findByUser(user);

		if (student.getAuthStatus() == 대기) {
			throw new IllegalArgumentException("승인 대기 중입니다.");
		} else if (student.getAuthStatus() == 거절) {
			throw new IllegalArgumentException("학생 승인이 거절되었습니다.");
		}

		StudentQueryDto search = studentQueryRepository.getStudentLoginDto(user.getId());

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + getAccessToken(request));

		saveRefreshTokenAndSetCookie(user, headers);
		String profileImagePath = user.getProfileImagePath() == null ? "" : user.getProfileImagePath();

		StudentLoginDto response = new StudentLoginDto(user.getLoginId(), user.getName(), user.getUserType(),
			search.schoolName(), search.schoolId(), search.classId(), search.grade(), search.classNumber(), search.studentNumber(),
			student.getTempPassword(), profileImagePath);

		return ResponseEntity.ok().headers(headers).body(response);
	}

	private ResponseEntity<AdminLoginDto> loginAdmin(LoginRequest request, User user) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + getAccessToken(request));

		saveRefreshTokenAndSetCookie(user, headers);
		String profileImagePath = user.getProfileImagePath() == null ? "" : user.getProfileImagePath();

		AdminLoginDto response = new AdminLoginDto(user.getLoginId(), user.getName(), user.getUserType(), profileImagePath);

		return ResponseEntity.ok().headers(headers).body(response);
	}

	private void saveRefreshTokenAndSetCookie(User user, HttpHeaders headers) {
		String refreshToken = jwtUtil.createRefreshToken(user.getLoginId());
		refreshService.storeRefreshToken(user.getLoginId(), refreshToken, jwtConfig.getRefreshExpiration());
		headers.add(HttpHeaders.SET_COOKIE, "Refresh-Token=" + refreshToken + "; HttpOnly; Secure; Path=/");
	}

	private User validation(LoginRequest request) {
		User user = userRepository.findByLoginId(request.loginId())
			.orElseThrow(() -> new UserFailedException("로그인 실패: 아이디가 존재하지 않습니다."));

		if (!BCrypt.checkpw(request.password(), user.getPassword())) {
			throw new UserFailedException("로그인 실패: 비밀번호가 틀렸습니다.");
		}

		if (user.getUserType() != request.userType()) {
			throw new UserFailedException("로그인 실패: 유저 타입이 일치하지 않습니다.");
		}
		return user;
	}

	public String getAccessToken(LoginRequest request) {
		return jwtUtil.createToken(request.loginId(), request.userType().toString());
	}

	// 2. ID 중복 체크
	public boolean isLoginIdDuplicate(CheckIdRequest request) {
		return userRepository.findByLoginId(request.loginId()).isPresent();
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

	// 비밀번호 일치 여부 확인
	public boolean verifyPassword(User user, CheckPasswordRequest request) {
		String encryptedPasswordFromDB = userRepository.findPasswordById(user.getId());
		return BCrypt.checkpw(request.password(), encryptedPasswordFromDB);
	}

	// 비밀번호 변경
	public void changePassword(User user, ChangePasswordRequest request) {
		if (request.newPassword() == null || request.newPassword().isBlank()) {
			throw new IllegalArgumentException("새 비밀번호를 입력해주세요.");
		}
		if (BCrypt.checkpw(request.newPassword(), user.getPassword())) {
			throw new IllegalArgumentException("기존 비밀번호와 다른 비밀번호를 입력해주세요.");
		}
		String encodedPassword = BCrypt.hashpw(request.newPassword(), BCrypt.gensalt());
		userRepository.updatePassword(user, encodedPassword);
	}
}
