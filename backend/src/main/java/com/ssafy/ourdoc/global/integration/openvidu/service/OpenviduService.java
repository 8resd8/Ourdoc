package com.ssafy.ourdoc.global.integration.openvidu.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ssafy.ourdoc.global.integration.openvidu.exception.OpenviduSessionFailException;

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

}
