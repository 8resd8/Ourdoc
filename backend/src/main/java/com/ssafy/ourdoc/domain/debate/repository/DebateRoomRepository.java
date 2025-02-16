package com.ssafy.ourdoc.domain.debate.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ssafy.ourdoc.domain.debate.entity.Room;

public interface DebateRoomRepository extends JpaRepository<Room, Long>, DebateRoomQueryRepository {
	Page<Room> findByEndAtIsNullOrderByCreatedAtDesc(Pageable pageable);

	@Query("select r from Room r where r.sessionId = :randomId")
	Optional<Room> findByRandomId(@Param("randomId") String randomId);
}
