package com.ssafy.ourdoc.domain.debate.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.ourdoc.domain.debate.dto.JoinRoomRequest;
import com.ssafy.ourdoc.domain.debate.dto.openvidu.JoinRequest;
import com.ssafy.ourdoc.domain.debate.dto.openvidu.JoinResponse;
import com.ssafy.ourdoc.domain.debate.service.DebateService;
import com.ssafy.ourdoc.domain.debate.service.OpenViduService;
import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.global.annotation.Login;

import lombok.RequiredArgsConstructor;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/openvidu")
public class OpenViduController {

	private final OpenViduService openViduService;
	private final DebateService debateService;


	@PostMapping("/join")
	public JoinResponse joinSession(@Login User user, @RequestBody JoinRequest joinRequest) {
		String randomId = UUID.randomUUID().toString();
		String token = openViduService.getToken(joinRequest, randomId, user);

		return new JoinResponse(token);
	}


	@PostMapping("/new-join")
	@ResponseStatus(HttpStatus.OK)
	public void newJoinSession(@Login User user, @RequestBody JoinRequest joinRequest) {
		openViduService.newCreateSession(joinRequest, user);
	}

	@PostMapping("/{roomId}/connection")
	@ResponseStatus(HttpStatus.OK)
	public String joinDebateRoom(@Login User user, @PathVariable("roomId") Long roomId, @RequestBody JoinRoomRequest request) {
		return debateService.joinDebateRoom(user, roomId, request);
	}
}