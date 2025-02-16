package com.ssafy.ourdoc.domain.debate.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.ssafy.ourdoc.domain.debate.dto.openvidu.JoinRequest;
import com.ssafy.ourdoc.domain.debate.dto.openvidu.JoinResponse;
import com.ssafy.ourdoc.domain.debate.repository.DebateRoomRepository;
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

	@PostMapping("/join")
	public JoinResponse joinSession(@Login User user, @RequestBody JoinRequest joinRequest) {
		String randomId = UUID.randomUUID().toString();
		String token = openViduService.getToken(joinRequest, randomId, user);
		return new JoinResponse(token, randomId);
	}
}