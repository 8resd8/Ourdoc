package com.ssafy.ourdoc.domain.debate.repository;

import java.util.List;

import com.ssafy.ourdoc.domain.debate.entity.RoomOnline;

public interface DebateRoomQueryRepository {
	Long countCurrentPeople(Long roomId);
	List<RoomOnline> findAllActiveByRoomId(Long roomId);
}
