package com.ssafy.ourdoc.domain.debate.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.ourdoc.domain.debate.dto.CreateRoomRequest;
import com.ssafy.ourdoc.domain.debate.dto.JoinRoomRequest;
import com.ssafy.ourdoc.domain.debate.dto.RoomDetailDto;
import com.ssafy.ourdoc.domain.debate.dto.RoomDto;
import com.ssafy.ourdoc.domain.debate.dto.UpdateRoomRequest;
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
	public ResponseEntity<Page<RoomDto>> getDebateRooms(Pageable pageable) {
		Page<RoomDto> rooms = debateService.getDebateRooms(pageable);
		return ResponseEntity.ok(rooms);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void createDebateRoom(@Login User user, @RequestBody CreateRoomRequest request) {
		debateService.createDebateRoom(user, request);
	}

	@PostMapping("/{roomId}/connection")
	public ResponseEntity<String> joinDebateRoom(@Login User user, @PathVariable("roomId") Long roomId,
		@RequestBody JoinRoomRequest request) {
		String token = debateService.joinDebateRoom(user, roomId, request);
		return ResponseEntity.ok(token);
	}

	@GetMapping("/{roomId}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<RoomDetailDto> getDebateRoom(@PathVariable("roomId") Long roomId) {
		RoomDetailDto roomDetailDto = debateService.getDebateRoomDetail(roomId);
		return ResponseEntity.ok(roomDetailDto);
	}

	@DeleteMapping("/{roomId}/exit")
	@ResponseStatus(HttpStatus.OK)
	public void leaveDebateRoom(@Login User user, @PathVariable("roomId") Long roomId) {
		debateService.leaveDebateRoom(user, roomId);
	}

	@PatchMapping("/{roomId}")
	@ResponseStatus(HttpStatus.OK)
	public void updateDebateRoom(@Login User user, @PathVariable("roomId") Long roomId, UpdateRoomRequest request) {
		debateService.updateDebateRoom(user, roomId, request);
	}

	@DeleteMapping("/{roomId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteDebateRoom(@Login User user, @PathVariable("roomId") Long roomId) {
		debateService.deleteDebateRoom(user, roomId);
	}
}
