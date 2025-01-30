package com.ssafy.ourdoc.domain.classroom.repository;

import com.ssafy.ourdoc.domain.classroom.entity.ClassRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassRoomRepository extends JpaRepository<ClassRoom, Long> {
}
