package com.ssafy.ourdoc.domain.debate.repository;

import java.util.List;

import com.ssafy.ourdoc.domain.debate.dto.OnlineUserDto;
import com.ssafy.ourdoc.domain.debate.entity.RoomOnline;

public interface DebateRoomQueryRepository {
	Long countCurrentPeople(Long roomId);

	List<RoomOnline> findAllActiveByRoomId(Long roomId);

	List<OnlineUserDto> findOnlineUsersByRoomId(Long roomId);
}
