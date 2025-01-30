package com.ssafy.ourdoc.classroom.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.ourdoc.classroom.entity.ClassRoom;

public interface ClassRoomRepository extends JpaRepository<ClassRoom, Long> {
}
