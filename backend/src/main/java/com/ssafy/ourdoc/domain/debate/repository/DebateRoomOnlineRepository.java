package com.ssafy.ourdoc.domain.debate.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.ourdoc.domain.debate.entity.RoomOnline;

public interface DebateRoomOnlineRepository extends JpaRepository<RoomOnline, Long>, DebateRoomQueryRepository {
	Optional<RoomOnline> findByRoomIdAndUserIdAndCreatedAtEqualsUpdatedAt(Long roomId, Long userId);
}