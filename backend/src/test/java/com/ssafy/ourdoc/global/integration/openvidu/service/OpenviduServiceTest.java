package com.ssafy.ourdoc.global.integration.openvidu.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Field;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ssafy.ourdoc.global.integration.openvidu.exception.OpenviduSessionFailException;
import com.ssafy.ourdoc.global.integration.openvidu.exception.OpenviduSessionNotFoundException;

import io.openvidu.java.client.Connection;
import io.openvidu.java.client.ConnectionProperties;
import io.openvidu.java.client.OpenVidu;
import io.openvidu.java.client.OpenViduException;
import io.openvidu.java.client.Session;

@ExtendWith(MockitoExtension.class)
class OpenviduServiceTest {

	@InjectMocks
	private OpenviduService openviduService;

	@Mock
	private OpenVidu openvidu;

	@Mock
	private Session session;

	@Mock
	private Connection connection;

	private static class TestOpenViduException extends OpenViduException {
		public TestOpenViduException(String message) { super(message); }
	}

	@BeforeEach
	void setUp() throws Exception {
		openviduService = new OpenviduService();

		Field openviduField = OpenviduService.class.getDeclaredField("openvidu");
		openviduField.setAccessible(true);
		openviduField.set(openviduService, openvidu);
	}

	@Test
	@DisplayName("세션 생성 성공")
	void createSession_success() throws OpenViduException {
		String expectedSessionId = "session1234";
		when(openvidu.createSession()).thenReturn(session);
		when(session.getSessionId()).thenReturn(expectedSessionId);

		String sessionId = openviduService.createSession();

		assertThat(sessionId).isEqualTo(expectedSessionId);
		verify(openvidu).createSession();
	}

	@Test
	@DisplayName("세션 생성 실패")
	void createSession_failure() throws OpenViduException {
		when(openvidu.createSession()).thenAnswer(invocation -> {
			throw new TestOpenViduException("error");
		});

		assertThatThrownBy(() -> openviduService.createSession())
			.isInstanceOf(OpenviduSessionFailException.class)
			.hasMessage("세션 생성에 실패했습니다.");
		verify(openvidu).createSession();
	}

	@Test
	@DisplayName("토큰 생성 성공")
	void generateToken_success() throws OpenViduException {
		String sessionId = "session1234";
		String expectedToken = "token1234";
		when(openvidu.getActiveSession(sessionId)).thenReturn(session);
		when(session.createConnection(any(ConnectionProperties.class))).thenReturn(connection);
		when(connection.getToken()).thenReturn(expectedToken);

		String token = openviduService.generateToken(sessionId);

		assertThat(token).isEqualTo(expectedToken);
		verify(openvidu).getActiveSession(sessionId);
		verify(session).createConnection(any(ConnectionProperties.class));
	}

	@Test
	@DisplayName("토큰 생성 실패 - 세션 없음")
	void generateToekn_failure_ifSessionNotExist() {
		String sessionId = "session1234";
		when(openvidu.getActiveSession(sessionId)).thenReturn(null);

		assertThatThrownBy(() -> openviduService.generateToken(sessionId))
			.isInstanceOf(OpenviduSessionNotFoundException.class)
			.hasMessage("해당 세션이 존재하지 않습니다.");
		verify(openvidu).getActiveSession(sessionId);
	}

	@Test
	@DisplayName("토큰 생성 실패 - 세션 연결 실패")
	void generateToken_failure_ifConnectionFail() throws OpenViduException {
		String sessionId = "session1234";
		when(openvidu.getActiveSession(sessionId)).thenReturn(session);
		when(session.createConnection(any(ConnectionProperties.class)))
			.thenAnswer(invocation -> {
				throw new TestOpenViduException("error");
			});

		assertThatThrownBy(() -> openviduService.generateToken(sessionId))
			.isInstanceOf(OpenviduSessionFailException.class)
			.hasMessage("세션 연결에 실패했습니다.");
		verify(openvidu).getActiveSession(sessionId);
		verify(session).createConnection(any(ConnectionProperties.class));
	}
}