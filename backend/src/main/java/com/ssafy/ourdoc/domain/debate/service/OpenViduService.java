package com.ssafy.ourdoc.domain.debate.service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.ssafy.ourdoc.domain.debate.dto.openvidu.JoinRequest;
import com.ssafy.ourdoc.domain.debate.entity.Room;
import com.ssafy.ourdoc.domain.debate.repository.DebateRoomRepository;
import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.global.integration.openvidu.service.OpenviduService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OpenViduService {
	@Value("${openvidu.url}")
	private String OPENVIDU_URL;
	@Value("${openvidu.secret}")
	private String OPENVIDU_SECRET;

	private RestTemplate restTemplate = new RestTemplate();

	private final DebateRoomRepository roomRepository;
	private final OpenviduService openviduService;

	// 기본 인증 헤더 생성 (Basic Auth)
	private HttpHeaders createHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		String auth = "OPENVIDUAPP:" + OPENVIDU_SECRET;
		byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.UTF_8));
		String authHeader = "Basic " + new String(encodedAuth);
		headers.set("Authorization", authHeader);
		return headers;
	}

	/**
	 * 세션 생성 및 토큰 발급 처리
	 * @param sessionName 클라이언트가 요청한 세션명
	 * @return 해당 세션에 대한 토큰
	 */
	public String getToken(String sessionName) {
		// 1. 세션 생성 (없으면 생성, 이미 있으면 기존 세션 사용)
		String sessionId = createSession(sessionName);
		// 2. 토큰 생성
		return createToken(sessionId);
	}

	/**
	 * OpenVidu 서버에 세션 생성 요청
	 * customSessionId를 이용해 고유한 세션명을 지정
	 */
	private String createSession(String sessionName) {
		String url = OPENVIDU_URL + "/openvidu/api/sessions";
		HttpHeaders headers = createHeaders();
		JSONObject body = new JSONObject();
		body.put("customSessionId", sessionName);
		HttpEntity<String> requestEntity = new HttpEntity<>(body.toString(), headers);
		try {
			ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);
			JSONObject jsonResponse = new JSONObject(response.getBody());
			return jsonResponse.getString("id");
		} catch (HttpClientErrorException e) {
			// 409 Conflict인 경우 세션이 이미 존재하는 것으로 간주하여 sessionName 반환
			if (e.getStatusCode() == HttpStatus.CONFLICT) {
				log.info("세션 이미 존재, 반환: {}", sessionName);
				return sessionName;
			} else {
				throw e;
			}
		}
	}

	/**
	 * OpenVidu 서버에 토큰 생성 요청
	 */
	private String createToken(String sessionId) {
		String url = OPENVIDU_URL + "/openvidu/api/tokens";
		HttpHeaders headers = createHeaders();
		JSONObject body = new JSONObject();
		body.put("session", sessionId);
		HttpEntity<String> requestEntity = new HttpEntity<>(body.toString(), headers);
		ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);
		JSONObject jsonResponse = new JSONObject(response.getBody());
		return jsonResponse.getString("token");
	}

	// ------------------------- 기존 --------------------------------

	private String createSession(JoinRequest request, String randomId, User user) {
		// request.randomId가 있으면 입장

		String url = OPENVIDU_URL + "/openvidu/api/sessions";
		HttpHeaders headers = createHeaders();
		JSONObject body = new JSONObject();
		body.put("customSessionId", request.getRandomId());
		HttpEntity<String> requestEntity = new HttpEntity<>(body.toString(), headers);
		try {
			ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);
			JSONObject jsonResponse = new JSONObject(response.getBody());

			// request.randomId가 없으면 방 생성
			if (request.getRandomId() == null) {
				Room roomUserCreate = Room.builder()
					.user(user)
					.sessionId(randomId)
					.title(request.getRoomName())
					.maxPeople(10)
					.password(request.getPassword())
					.build();
				roomRepository.save(roomUserCreate);
			}

			return jsonResponse.getString("id");
		} catch (HttpClientErrorException e) {
			// 409 Conflict인 경우 세션이 이미 존재하는 것으로 간주하여 randomId 반환
			if (e.getStatusCode() == HttpStatus.CONFLICT || request.getRandomId() != null) {
				// return randomId;
				return request.getRandomId();
			} else {
				throw e;
			}
		}
	}

	public String getToken(JoinRequest joinRequest, String randomId, User user) {
		// 1. 세션 생성 (없으면 생성, 이미 있으면 기존 세션 사용)
		String sessionId = createSession(joinRequest, randomId, user);
		// 2. 토큰 생성
		return createToken(sessionId);
	}

	public String newCreateSession(JoinRequest request, User user) {
		String sessionId = openviduService.createSession();

		Room room = Room.builder()
			.sessionId(sessionId)
			.user(user)
			.title(request.getRoomName())
			.password(request.getPassword())
			.maxPeople(10)
			.build();

		roomRepository.save(room);

		return sessionId;
	}
}