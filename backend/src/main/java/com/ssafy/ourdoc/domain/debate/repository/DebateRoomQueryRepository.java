package com.ssafy.ourdoc.domain.debate.repository;

public interface DebateRoomQueryRepository {
import java.util.List;
import java.util.Optional;

import com.ssafy.ourdoc.domain.debate.entity.RoomOnline;

	Long countCurrentPeople(Long roomId);

	Optional<RoomOnline> findActiveByRoomIdAndUserId(Long roomId, Long userId);

	Long updateEndAt(Long roomOnlineId);

	List<RoomOnline> findAllActiveByRoomId(Long roomId);

	List<OnlineUserDto> findOnlineUsersByRoomId(Long roomId);
}
