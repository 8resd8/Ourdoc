package com.ssafy.ourdoc.domain.debate.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.ourdoc.domain.debate.dto.CreateRoomRequest;
import com.ssafy.ourdoc.domain.debate.dto.JoinRoomRequest;
import com.ssafy.ourdoc.domain.debate.dto.RoomDto;
import com.ssafy.ourdoc.domain.debate.service.DebateService;
import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.global.annotation.Login;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/debates")
public class DebateController {

	private final DebateService debateService;

	@GetMapping
	public ResponseEntity<List<RoomDto>> getDebateRooms() {
		List<RoomDto> rooms = debateService.getDebateRooms();
		return ResponseEntity.ok(rooms);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void createDebateRoom(@Login User user, @RequestBody CreateRoomRequest request) {
		debateService.createDebateRoom(user, request);
	}

	@PostMapping("/{roomId}/connection")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<String> joinDebateRoom(@Login User user, @PathVariable("roomId") Long roomId, @RequestBody JoinRoomRequest request) {
		String token = debateService.joinDebateRoom(user, roomId, request);
		return ResponseEntity.ok(token);
	}
}
