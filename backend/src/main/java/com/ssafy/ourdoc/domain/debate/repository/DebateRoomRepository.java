package com.ssafy.ourdoc.domain.debate.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.ourdoc.domain.debate.entity.Room;

public interface DebateRoomRepository extends JpaRepository<Room, Long>, DebateRoomQueryRepository {
	Page<Room> findByEndAtIsNull(Pageable pageable);
}
