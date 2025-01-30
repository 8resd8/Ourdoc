package com.ssafy.ourdoc.domain.classroom.repository;

import java.time.Year;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.ourdoc.domain.classroom.entity.ClassRoom;

public interface ClassRoomRepository extends JpaRepository<ClassRoom, Long> {
	Optional<ClassRoom> findByGradeAndClassNumberAndYear(int grade, int classNumber, Year year);
}
