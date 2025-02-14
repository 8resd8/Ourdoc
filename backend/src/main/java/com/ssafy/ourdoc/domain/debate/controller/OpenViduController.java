package com.ssafy.ourdoc.domain.debate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.ssafy.ourdoc.domain.debate.dto.openvidu.JoinRequest;
import com.ssafy.ourdoc.domain.debate.dto.openvidu.JoinResponse;
import com.ssafy.ourdoc.domain.debate.service.OpenViduService;

import lombok.RequiredArgsConstructor;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/openvidu")
public class OpenViduController {

	private final OpenViduService openViduService;

	@PostMapping("/join")
	public JoinResponse joinSession(@RequestBody JoinRequest joinRequest) {
		String token = openViduService.getToken(joinRequest.getSessionName());
		return new JoinResponse(token);
	}
}