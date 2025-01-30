package com.ssafy.ourdoc.domain.classroom.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.ourdoc.domain.classroom.entity.ClassRoom;

public interface ClassRoomRepository extends JpaRepository<ClassRoom, Long> {
}
