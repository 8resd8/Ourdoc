package com.ssafy.ourdoc.domain.debate.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.ourdoc.domain.debate.entity.RoomOnline;

public interface RoomOnlineRepository extends JpaRepository<RoomOnline, Long> {
	int countByRoomIdAndUpdatedAtNull(Long roomId);
}
