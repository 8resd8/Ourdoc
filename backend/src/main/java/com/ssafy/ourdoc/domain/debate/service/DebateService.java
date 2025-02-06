package com.ssafy.ourdoc.domain.debate.service;

import org.springframework.stereotype.Service;

import com.ssafy.ourdoc.domain.debate.dto.CreateRoomRequest;
import com.ssafy.ourdoc.domain.debate.entity.Room;
import com.ssafy.ourdoc.domain.debate.repository.RoomRepository;
import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.global.common.enums.UserType;
import com.ssafy.ourdoc.global.exception.ForbiddenException;
import com.ssafy.ourdoc.global.integration.openvidu.service.OpenviduService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DebateService {
	private final RoomRepository roomRepository;
	private final OpenviduService openviduService;
	public void createDebateRoom(User user, CreateRoomRequest request) {
		if (user.getUserType() == UserType.학생) {
			throw new ForbiddenException("독서토론방 생성 권한이 없습니다.");
		}

		String sessionId = openviduService.createSession();

		Room room = Room.builder()
			.session_id(sessionId)
			.user(user)
			.title(request.title())
			.password(request.password())
			.max_people(request.maxPeople())
			.current_people(0)
			.build();

		roomRepository.save(room);
	}
}
