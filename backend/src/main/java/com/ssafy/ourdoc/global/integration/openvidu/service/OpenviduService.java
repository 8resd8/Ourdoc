package com.ssafy.ourdoc.global.integration.openvidu.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ssafy.ourdoc.global.integration.openvidu.exception.OpenviduSessionFailException;
import com.ssafy.ourdoc.global.integration.openvidu.exception.OpenviduSessionNotFoundException;

import io.openvidu.java.client.Connection;
import io.openvidu.java.client.ConnectionProperties;
import io.openvidu.java.client.OpenVidu;
import io.openvidu.java.client.OpenViduException;
import io.openvidu.java.client.Session;
import jakarta.annotation.PostConstruct;

@Service
public class OpenviduService {

	@Value("${openvidu.url}")
	private String openviduUrl;

	@Value("${openvidu.secret}")
	private String openviduSecret;

	private OpenVidu openvidu;

	@PostConstruct
	public void init() {
		this.openvidu = new OpenVidu(openviduUrl, openviduSecret);
	}

	/**
	 * 새로운 세션을 생성합니다.
	 * @return 생성된 OpenVidu 세션의 ID
	 */
	public String createSession() {
		Session session;
		try {
			session = openvidu.createSession();
		} catch (OpenViduException e) {
			throw new OpenviduSessionFailException("세션 생성에 실패했습니다.");
		}
		return session.getSessionId();
	}

	/**
	 * 특정 세션에 참가할 수 있는 토큰을 생성합니다.
	 * @param sessionId OpenVidu 서버에서 생성된 세션 ID
	 * @return 해당 세션에 참가할 수 있는 토큰
	 */
	public String generateToken(String sessionId) {
		Session session = openvidu.getActiveSession(sessionId);
		if (session == null) {
			throw new OpenviduSessionNotFoundException("해당 세션이 존재하지 않습니다.");
		}
		ConnectionProperties properties = new ConnectionProperties.Builder().build();
		Connection connection;
		try {
			connection = session.createConnection(properties);
		} catch (OpenViduException e) {
			throw new OpenviduSessionFailException("세션 연결에 실패했습니다.");
		}
		return connection.getToken();
	}

}
