package com.ssafy.ourdoc.domain.classroom.repository;

import java.time.Year;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.ourdoc.domain.classroom.entity.ClassRoom;
import com.ssafy.ourdoc.domain.classroom.entity.School;

public interface ClassRoomRepository extends JpaRepository<ClassRoom, Long>, ClassRoomQueryRepository {
	Optional<ClassRoom> findByGradeAndClassNumberAndYear(int grade, int classNumber, Year year);

	Optional<List<ClassRoom>> findBySchoolAndGradeAndClassNumberAndYear(School school, Integer grade, Integer classNumber, Year year);
}
