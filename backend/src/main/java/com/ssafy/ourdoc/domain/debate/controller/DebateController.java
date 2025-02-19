package com.ssafy.ourdoc.domain.debate.controller;

import java.net.URI;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ssafy.ourdoc.domain.debate.dto.CreateRoomRequest;
import com.ssafy.ourdoc.domain.debate.dto.JoinRoomRequest;
import com.ssafy.ourdoc.domain.debate.dto.RoomCreateResponse;
import com.ssafy.ourdoc.domain.debate.dto.RoomDetailResponse;
import com.ssafy.ourdoc.domain.debate.dto.RoomDto;
import com.ssafy.ourdoc.domain.debate.dto.RoomJoinResponse;
import com.ssafy.ourdoc.domain.debate.dto.UpdateRoomRequest;
import com.ssafy.ourdoc.domain.debate.service.DebateService;
import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.global.annotation.Login;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/debates")
public class DebateController {

	private final DebateService debateService;

	@GetMapping
	public ResponseEntity<Page<RoomDto>> getDebateRooms(@PageableDefault(page = 0, size = 10) Pageable pageable) {
		Page<RoomDto> rooms = debateService.getDebateRooms(pageable);
		return ResponseEntity.ok(rooms);
	}

	@PostMapping("/teachers")
	public ResponseEntity<RoomCreateResponse> createDebateRoom(@Login User user,
		@Valid @RequestBody CreateRoomRequest request) {
		RoomCreateResponse response = debateService.createDebateRoom(user, request);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
			.path("/{roomId}/connection")
			.buildAndExpand(response)
			.toUri();
		return ResponseEntity.created(location).body(response);
	}

	@PostMapping("/{roomId}/connection")
	public ResponseEntity<RoomJoinResponse> joinDebateRoom(@Login User user, @PathVariable("roomId") Long roomId,
		@Valid @RequestBody JoinRoomRequest request) {
		RoomJoinResponse response = debateService.joinDebateRoom(user, roomId, request);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/{roomId}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<RoomDetailResponse> getDebateRoom(@PathVariable("roomId") Long roomId) {
		RoomDetailResponse roomDetailResponse = debateService.getDebateRoomDetail(roomId);
		return ResponseEntity.ok(roomDetailResponse);
	}

	@DeleteMapping("/{roomId}/exit")
	@ResponseStatus(HttpStatus.OK)
	public void leaveDebateRoom(@Login User user, @PathVariable("roomId") Long roomId) {
		debateService.leaveDebateRoom(user, roomId);
	}

	@PatchMapping("/teachers/{roomId}")
	@ResponseStatus(HttpStatus.OK)
	public void updateDebateRoom(@Login User user, @PathVariable("roomId") Long roomId,
		@Valid UpdateRoomRequest request) {
		debateService.updateDebateRoom(user, roomId, request);
	}

	@DeleteMapping("/teachers/{roomId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteDebateRoom(@Login User user, @PathVariable("roomId") Long roomId) {
		debateService.deleteDebateRoom(user, roomId);
	}
}
