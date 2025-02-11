package com.ssafy.ourdoc.domain.debate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.ssafy.ourdoc.domain.debate.dto.openvidu.JoinRequest;
import com.ssafy.ourdoc.domain.debate.dto.openvidu.JoinResponse;
import com.ssafy.ourdoc.domain.debate.service.OpenViduService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/openvidu")
public class OpenViduController {

	private final OpenViduService openViduService;

	/**
	 * 클라이언트에서 sessionName과 nickname을 받아 OpenVidu 토큰을 발급한다.
	 */
	@PostMapping("/join")
	public JoinResponse joinSession(@RequestBody JoinRequest joinRequest) {
		String token = openViduService.getToken(joinRequest.getSessionName());
		return new JoinResponse(token);
	}
}