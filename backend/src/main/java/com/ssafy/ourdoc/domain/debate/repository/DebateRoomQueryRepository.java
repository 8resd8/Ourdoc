package com.ssafy.ourdoc.domain.debate.repository;

import java.util.List;
import java.util.Optional;

import com.ssafy.ourdoc.domain.debate.dto.OnlineUserDto;
import com.ssafy.ourdoc.domain.debate.entity.RoomOnline;

public interface DebateRoomQueryRepository {
	Long countCurrentPeople(Long roomId);

	Optional<RoomOnline> findActiveByRoomIdAndUserId(Long roomId, Long userId);

	Long updateEndAt(Long roomOnlineId);

	List<RoomOnline> findAllActiveByRoomId(Long roomId);

	List<OnlineUserDto> findOnlineUsersByRoomId(Long roomId);

	String getSchoolName(Long id);
}
