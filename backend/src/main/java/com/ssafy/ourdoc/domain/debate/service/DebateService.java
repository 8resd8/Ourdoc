package com.ssafy.ourdoc.domain.debate.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ssafy.ourdoc.domain.debate.dto.CreateRoomRequest;
import com.ssafy.ourdoc.domain.debate.dto.JoinRoomRequest;
import com.ssafy.ourdoc.domain.debate.dto.RoomDto;
import com.ssafy.ourdoc.domain.debate.entity.Room;
import com.ssafy.ourdoc.domain.debate.entity.RoomOnline;
import com.ssafy.ourdoc.domain.debate.repository.RoomOnlineRepository;
import com.ssafy.ourdoc.domain.debate.repository.RoomRepository;
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
	private final RoomRepository roomRepository;
	private final RoomOnlineRepository roomOnlineRepository;
	private final OpenviduService openviduService;

	public List<RoomDto> getDebateRooms() {
		List<Room> rooms = roomRepository.findAll();
		return rooms.stream()
			.map(room -> {
				Long currentPeople = roomOnlineRepository.countCurrentPeople(room.getId());
				return new RoomDto(
					room.getId(),
					room.getTitle(),
					room.getUser().getName(),
					room.getMaxPeople(),
					currentPeople
				);
			})
			.collect(Collectors.toList());
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

		roomRepository.save(room);
	}

	public String joinDebateRoom(User user, Long roomId, JoinRoomRequest request) {
		Room room = roomRepository.findById(roomId)
			.orElseThrow(() -> new IllegalArgumentException("해당 방은 존재하지 않습니다."));

		if (room.getEndAt() != null) {
			throw new ForbiddenException("해당 방은 종료되었습니다.");
		}

		if (!room.getPassword().equals(request.password())) {
			throw new ForbiddenException("비밀번호가 일치하지 않습니다.");
		}

		if (roomOnlineRepository.countCurrentPeople(room.getId()) >= room.getMaxPeople()) {
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
		roomOnlineRepository.save(roomOnline);

		return token;
	}
}
