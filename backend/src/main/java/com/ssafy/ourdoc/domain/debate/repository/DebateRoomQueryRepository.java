package com.ssafy.ourdoc.domain.debate.repository;

import java.util.Optional;

import com.ssafy.ourdoc.domain.debate.entity.RoomOnline;

public interface DebateRoomQueryRepository {
	Long countCurrentPeople(Long roomId);
	Optional<RoomOnline> findActiveByRoomIdAndUserId(Long roomId, Long userId);

	Long updateEndAt(Long roomOnlineId);
}
