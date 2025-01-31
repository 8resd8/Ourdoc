package com.ssafy.ourdoc.domain.classroom.repository;

import java.time.Year;
import java.util.Optional;
import com.ssafy.ourdoc.domain.classroom.entity.ClassRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassRoomRepository extends JpaRepository<ClassRoom, Long> {
	Optional<ClassRoom> findByGradeAndClassNumberAndYear(int grade, int classNumber, Year year);
}
