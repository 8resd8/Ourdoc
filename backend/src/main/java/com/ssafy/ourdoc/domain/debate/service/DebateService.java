package com.ssafy.ourdoc.domain.debate.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ssafy.ourdoc.domain.debate.dto.CreateRoomRequest;
import com.ssafy.ourdoc.domain.debate.dto.JoinRoomRequest;
import com.ssafy.ourdoc.domain.debate.dto.RoomDto;
import com.ssafy.ourdoc.domain.debate.dto.UpdateRoomRequest;
import com.ssafy.ourdoc.domain.debate.entity.Room;
import com.ssafy.ourdoc.domain.debate.entity.RoomOnline;
import com.ssafy.ourdoc.domain.debate.repository.DebateRoomOnlineRepository;
import com.ssafy.ourdoc.domain.debate.repository.DebateRoomRepository;
import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.global.common.enums.UserType;
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
		Page<Room> roomPage = debateRoomRepository.findByEndAtIsNull(pageable);
		return roomPage.map(room -> {
			Long currentPeople = debateRoomOnlineRepository.countCurrentPeople(room.getId());
			return new RoomDto(
				room.getId(),
				room.getTitle(),
				room.getUser().getName(),
				room.getMaxPeople(),
				currentPeople
			);
		});
	}

	public void createDebateRoom(User user, CreateRoomRequest request) {
		if (user.getUserType() == UserType.학생) {
			throw new ForbiddenException("독서토론방 생성 권한이 없습니다.");
		}

		String sessionId = openviduService.createSession();

		Room room = Room.builder()
			.sessionId(sessionId)
			.user(user)
			.title(request.title())
			.password(request.password())
			.maxPeople(request.maxPeople())
			.build();

		debateRoomRepository.save(room);
	}

	public String joinDebateRoom(User user, Long roomId, JoinRoomRequest request) {
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

		return token;
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
}
