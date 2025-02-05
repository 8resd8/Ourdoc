package com.ssafy.ourdoc.user.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.sql.Date;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ssafy.ourdoc.domain.user.dto.LoginRequest;
import com.ssafy.ourdoc.domain.user.dto.LoginResponse;
import com.ssafy.ourdoc.domain.user.dto.LogoutResponse;
import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.domain.user.repository.UserRepository;
import com.ssafy.ourdoc.domain.user.service.UserService;
import com.ssafy.ourdoc.global.common.enums.Active;
import com.ssafy.ourdoc.global.common.enums.Gender;
import com.ssafy.ourdoc.global.common.enums.UserType;
import com.ssafy.ourdoc.global.config.JwtConfig;
import com.ssafy.ourdoc.global.exception.UserFailedException;
import com.ssafy.ourdoc.global.util.JwtBlacklistService;
import com.ssafy.ourdoc.global.util.JwtRefreshService;
import com.ssafy.ourdoc.global.util.JwtUtil;

import io.jsonwebtoken.Claims;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	@Mock
	private UserRepository userRepository;

	@Mock
	private JwtUtil jwtUtil;

	@Mock
	private 	JwtConfig jwtConfig;

	@Mock
	private JwtRefreshService refreshService;

	@Mock
	private JwtBlacklistService blacklistService;

	@InjectMocks
	private UserService userService;

	private User mockUser;
	private LoginRequest loginRequest;

	@BeforeEach
	void setUp() {
		String hashedPassword = BCrypt.hashpw("password123", BCrypt.gensalt());

		mockUser = User.builder()
			.userType(UserType.교사)
			.name("김선생")
			.loginId("teacher123")
			.password(hashedPassword)
			.birth(Date.valueOf("1985-06-15"))
			.gender(Gender.남)
			.active(Active.활성)
			.build();

		loginRequest = new LoginRequest(
			UserType.교사, // userType
			"teacher123", // loginId
			"password123" // password
		);
	}

	@Test
	@DisplayName("로그인 성공")
	void login_Success() {
		// Given
		given(userRepository.findByLoginId(loginRequest.loginId())).willReturn(Optional.of(mockUser));
		given(jwtUtil.createToken(mockUser.getLoginId(), mockUser.getUserType().toString()))
			.willReturn("mocked-jwt-token");
		given(jwtUtil.createRefreshToken(mockUser.getLoginId()))
			.willReturn("mocked-refresh-token");
		given(jwtConfig.getRefreshExpiration())  // ✅ Mock 동작 추가
			.willReturn(86400L); // 예를 들어 24시간 (초 단위)

		// ✅ JwtRefreshService Mock 동작 추가
		doNothing().when(refreshService).storeRefreshToken(eq(mockUser.getLoginId()), eq("mocked-refresh-token"), anyLong());

		// When
		LoginResponse response = userService.login(loginRequest);

		// Then
		assertThat(response.resultCode()).isEqualTo("200");
		assertThat(response.message()).isEqualTo("로그인 성공");
		assertThat(response.user().name()).isEqualTo(mockUser.getName());
		assertThat(response.user().role()).isEqualTo(mockUser.getUserType().toString());
	}


	@Test
	@DisplayName("로그인 실패 - 존재하지 않는 아이디")
	void login_Fail_IdNotFound() {
		// Given: 존재하지 않는 사용자
		given(userRepository.findByLoginId(loginRequest.loginId())).willReturn(Optional.empty());

		// When & Then
		UserFailedException exception = assertThrows(UserFailedException.class, () -> {
			userService.login(loginRequest);
		});
		assertThat(exception.getMessage()).isEqualTo("로그인 실패: 아이디가 존재하지 않습니다.");
	}

	@Test
	@DisplayName("로그인 실패 - 비밀번호 틀림")
	void login_Fail_WrongPassword() {
		// Given: 존재하는 사용자지만, 비밀번호가 다름
		given(userRepository.findByLoginId(loginRequest.loginId())).willReturn(Optional.of(mockUser));

		LoginRequest wrongPasswordRequest = new LoginRequest(
			UserType.교사,
			"teacher123",
			"wrongPassword"
		);

		// When & Then
		UserFailedException exception = assertThrows(UserFailedException.class, () -> {
			userService.login(wrongPasswordRequest);
		});
		assertThat(exception.getMessage()).isEqualTo("로그인 실패: 비밀번호가 틀렸습니다.");
	}

	@Test
	@DisplayName("로그인 실패 - 유저 타입 불일치")
	void login_Fail_WrongUserType() {
		// Given: 존재하는 사용자지만, userType이 다름
		given(userRepository.findByLoginId(loginRequest.loginId())).willReturn(Optional.of(mockUser));

		LoginRequest wrongUserTypeRequest = new LoginRequest(
			UserType.학생,
			"teacher123",
			"password123"
		);

		// When & Then
		UserFailedException exception = assertThrows(UserFailedException.class, () -> {
			userService.login(wrongUserTypeRequest);
		});
		assertThat(exception.getMessage()).isEqualTo("로그인 실패: 유저 타입이 일치하지 않습니다.");
	}

	@Test
	@DisplayName("ID 중복 체크 - 중복 존재")
	void isLoginIdDuplicate_True() {
		// Given: 중복되는 ID가 있음
		given(userRepository.findByLoginId("teacher123")).willReturn(Optional.of(mockUser));

		// When: 중복 체크 실행
		boolean isDuplicate = userService.isLoginIdDuplicate("teacher123");

		// Then: true 반환 확인
		assertTrue(isDuplicate);
	}

	@Test
	@DisplayName("ID 중복 체크 - 중복 없음")
	void isLoginIdDuplicate_False() {
		// Given: 중복되는 ID가 없음
		given(userRepository.findByLoginId("newuser123")).willReturn(Optional.empty());

		// When: 중복 체크 실행
		boolean isDuplicate = userService.isLoginIdDuplicate("newuser123");

		// Then: false 반환 확인
		assertFalse(isDuplicate);
	}

	@Test
	@DisplayName("로그아웃 성공")
	void logout_Success() {
		// Given: 유효한 토큰
		String validToken = jwtUtil.createToken(mockUser.getLoginId(), mockUser.getUserType().toString());
		given(jwtUtil.validateToken(validToken)).willReturn(true);

		// ✅ Claims 객체 Mocking (여기가 핵심!)
		Claims mockClaims = mock(Claims.class);
		given(mockClaims.getExpiration()).willReturn(new Date(System.currentTimeMillis() + 60000)); // 1분 후 만료
		given(jwtUtil.getClaims(validToken)).willReturn(mockClaims);

		// ✅ JwtBlacklistService Mocking (여기가 핵심!)
		doNothing().when(blacklistService).addToBlacklist(eq(validToken), anyLong());

		// When: 로그아웃 실행
		LogoutResponse response = userService.logout(validToken);

		// Then: 로그아웃 성공 응답 확인
		assertThat(response.resultCode()).isEqualTo("200");
		assertThat(response.message()).isEqualTo("로그아웃 성공");
	}

	@Test
	@DisplayName("로그아웃 실패 - 유효하지 않은 토큰")
	void logout_Fail_InvalidToken() {
		// Given: 유효하지 않은 토큰
		String invalidToken = "invalid.token.value";
		given(jwtUtil.validateToken(invalidToken)).willReturn(false);

		// When & Then: 로그아웃 실패 예외 확인
		UserFailedException exception = assertThrows(
			UserFailedException.class,
			() -> userService.logout(invalidToken)
		);

		assertThat(exception.getMessage()).isEqualTo("로그아웃 실패: 유효하지 않은 토큰입니다.");
	}

}