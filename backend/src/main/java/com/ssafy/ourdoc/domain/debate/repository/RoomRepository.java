package com.ssafy.ourdoc.domain.debate.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.ourdoc.domain.debate.entity.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
