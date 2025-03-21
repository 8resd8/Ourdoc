package com.ssafy.ourdoc.domain.debate.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.ourdoc.domain.debate.dto.JoinRoomRequest;
import com.ssafy.ourdoc.domain.debate.dto.RoomJoinResponse;
import com.ssafy.ourdoc.domain.debate.dto.openvidu.JoinRequest;
import com.ssafy.ourdoc.domain.debate.dto.openvidu.JoinResponse;
import com.ssafy.ourdoc.domain.debate.dto.openvidu.JoinTestRequest;
import com.ssafy.ourdoc.domain.debate.dto.openvidu.JoinTestResponse;
import com.ssafy.ourdoc.domain.debate.service.DebateService;
import com.ssafy.ourdoc.domain.debate.service.OpenViduService;
import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.global.annotation.Login;
import com.ssafy.ourdoc.global.integration.openvidu.service.OpenviduService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/openvidu")
public class OpenViduController {

	private final OpenViduService openViduService;
	private final DebateService debateService;
	private final OpenviduService openviduService;

	@PostMapping("/join")
	public JoinResponse joinSession(@Login User user, @Valid @RequestBody JoinRequest joinRequest) {
		String randomId = UUID.randomUUID().toString();
		String token = openViduService.getToken(joinRequest, randomId, user);
		return new JoinResponse(token, randomId);
	}

	@PostMapping("/test")
	public JoinTestResponse testJoinSession(@Valid @RequestBody JoinTestRequest joinRequest) {
		String randomId = UUID.randomUUID().toString();
		String token = openViduService.getToken(joinRequest.getSessionName());
		return new JoinTestResponse(token);
	}

	@PostMapping("/new-join")
	@ResponseStatus(HttpStatus.OK)
	public String newJoinSession(@Login User user, @Valid @RequestBody JoinRequest joinRequest) {
		String sessionId = openViduService.newCreateSession(joinRequest, user);

		return openviduService.generateToken(sessionId);
	}

	@PostMapping("/{roomId}/connection")
	@ResponseStatus(HttpStatus.OK)
	public RoomJoinResponse joinDebateRoom(@Login User user, @PathVariable("roomId") Long roomId,
		@Valid @RequestBody JoinRoomRequest request) {
		return debateService.joinDebateRoom(user, roomId, request);
	}
}