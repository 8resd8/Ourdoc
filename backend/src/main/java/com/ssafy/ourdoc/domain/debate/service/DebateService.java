package com.ssafy.ourdoc.domain.debate.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ssafy.ourdoc.domain.debate.dto.CreateRoomRequest;
import com.ssafy.ourdoc.domain.debate.dto.JoinRoomRequest;
import com.ssafy.ourdoc.domain.debate.dto.OnlineUserDto;
import com.ssafy.ourdoc.domain.debate.dto.RoomCreateResponse;
import com.ssafy.ourdoc.domain.debate.dto.RoomDetailResponse;
import com.ssafy.ourdoc.domain.debate.dto.RoomDto;
import com.ssafy.ourdoc.domain.debate.dto.RoomJoinResponse;
import com.ssafy.ourdoc.domain.debate.dto.UpdateRoomRequest;
import com.ssafy.ourdoc.domain.debate.entity.Room;
import com.ssafy.ourdoc.domain.debate.entity.RoomOnline;
import com.ssafy.ourdoc.domain.debate.repository.DebateRoomOnlineRepository;
import com.ssafy.ourdoc.domain.debate.repository.DebateRoomRepository;
import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.global.exception.ForbiddenException;
import com.ssafy.ourdoc.global.integration.openvidu.service.OpenviduService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class DebateService {
	private final DebateRoomRepository debateRoomRepository;
	private final DebateRoomOnlineRepository debateRoomOnlineRepository;
	private final OpenviduService openviduService;

	public Page<RoomDto> getDebateRooms(Pageable pageable) {
		Page<Room> roomPage = debateRoomRepository.findByEndAtIsNullOrderByCreatedAtDesc(pageable);
		return roomPage.map(room -> {
			Long currentPeople = debateRoomOnlineRepository.countCurrentPeople(room.getId());
			String schoolName = debateRoomRepository.getSchoolName(room.getUser().getId());
			return new RoomDto(
				room.getId(),
				room.getTitle(),
				room.getUser().getName(),
				room.getMaxPeople(),
				currentPeople,
				schoolName,
				room.getCreatedAt(),
				room.getSessionId(),
				room.getUser().getId()
			);
		});
	}

	public RoomCreateResponse createDebateRoom(User user, CreateRoomRequest request) {
		String sessionId = openviduService.createSession();

		Room room = Room.builder()
			.sessionId(sessionId)
			.user(user)
			.title(request.title())
			.password(request.password())
			.maxPeople(9)
			.build();

		debateRoomRepository.save(room);

		return new RoomCreateResponse(room.getId());
	}

	public RoomJoinResponse joinDebateRoom(User user, Long roomId, JoinRoomRequest request) {
		Room room = debateRoomRepository.findById(roomId)
			.orElseThrow(() -> new IllegalArgumentException("해당 방은 존재하지 않습니다."));

		if (room.getEndAt() != null) {
			throw new ForbiddenException("해당 방은 종료되었습니다.");
		}

		if (!room.getPassword().equals(request.password())) {
			throw new ForbiddenException("비밀번호가 일치하지 않습니다.");
		}

		if (debateRoomOnlineRepository.countCurrentPeople(room.getId()) >= room.getMaxPeople()) {
			throw new ForbiddenException("방에 빈자리가 없습니다.");
		}

		String sessionId = room.getSessionId();
		if (sessionId == null || sessionId.isEmpty()) {
			throw new IllegalArgumentException("해당 방은 세션이 존재하지 않습니다.");
		}

		String token = openviduService.generateToken(sessionId);

		RoomOnline roomOnline = RoomOnline.builder()
			.room(room)
			.token(token)
			.user(user)
			.build();
		debateRoomOnlineRepository.save(roomOnline);

		return new RoomJoinResponse(token);
	}

	public void leaveDebateRoom(User user, Long roomId) {
		RoomOnline roomOnline = debateRoomOnlineRepository
			.findActiveByRoomIdAndUserId(roomId, user.getId())
			.orElseThrow(() -> new IllegalArgumentException("해당 방에 접속 중인 유저가 아닙니다."));
		debateRoomOnlineRepository.updateEndAt(roomOnline.getId());
		debateRoomOnlineRepository.save(roomOnline);
	}

	public void updateDebateRoom(User user, Long roomId, UpdateRoomRequest request) {
		Room room = debateRoomRepository.findById(roomId)
			.orElseThrow(() -> new IllegalArgumentException("해당 방이 존재하지 않습니다."));

		if (room.getEndAt() != null) {
			throw new ForbiddenException("종료된 방입니다.");
		}

		if (!room.getUser().getId().equals(user.getId())) {
			throw new ForbiddenException("방 수정 권한이 없습니다.");
		}

		if (request.title() != null && !request.title().isEmpty()) {
			room.updateTitle(request.title());
		}

		if (request.password() != null && !request.password().isEmpty()) {
			room.updatePassword(request.password());
		}

		if (request.maxPeople() != null) {
			room.updateMaxPeople(request.maxPeople());
		}

		debateRoomRepository.save(room);
	}

	public void deleteDebateRoom(User user, Long roomId) {
		Room room = debateRoomRepository.findById(roomId)
			.orElseThrow(() -> new IllegalArgumentException("해당 방은 존재하지 않습니다."));

		if (!room.getUser().getId().equals(user.getId())) {
			throw new ForbiddenException("방 삭제 권한이 없습니다.");
		}

		List<RoomOnline> currentPeople = debateRoomOnlineRepository.findAllActiveByRoomId(roomId);
		for (RoomOnline currentPerson : currentPeople) {
			debateRoomOnlineRepository.updateEndAt(currentPerson.getId());
			debateRoomOnlineRepository.save(currentPerson);
		}

		room.updateEndAt(LocalDateTime.now());
		debateRoomRepository.save(room);
	}

	public RoomDetailResponse getDebateRoomDetail(Long roomId) {
		Room room = debateRoomRepository.findById(roomId)
			.orElseThrow(() -> new IllegalArgumentException("해당 방은 존재하지 않습니다."));

		if (room.getEndAt() != null) {
			throw new IllegalArgumentException("종료된 방입니다.");
		}

		Long currentPeople = debateRoomOnlineRepository.countCurrentPeople(roomId);
		List<OnlineUserDto> onlineUserList = debateRoomOnlineRepository.findOnlineUsersByRoomId(roomId);
		return new RoomDetailResponse(
			room.getId(),
			room.getTitle(),
			room.getUser().getName(),
			room.getUser().getLoginId(),
			room.getMaxPeople(),
			currentPeople,
			room.getCreatedAt(),
			onlineUserList
		);
	}
}
