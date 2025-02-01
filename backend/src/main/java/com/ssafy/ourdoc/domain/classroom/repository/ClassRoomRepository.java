package com.ssafy.ourdoc.domain.classroom.repository;

import java.time.Year;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.ourdoc.domain.classroom.entity.ClassRoom;
import com.ssafy.ourdoc.domain.classroom.entity.School;

public interface ClassRoomRepository extends JpaRepository<ClassRoom, Long> {
	Optional<ClassRoom> findByGradeAndClassNumberAndYear(int grade, int classNumber, Year year);

	Optional<ClassRoom> findBySchoolAndGradeAndClassNumber(School school, int grade, int classNumber);
}
