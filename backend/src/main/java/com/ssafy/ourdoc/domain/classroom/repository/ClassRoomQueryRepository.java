package com.ssafy.ourdoc.domain.classroom.repository;

import java.time.Year;
import java.util.List;

import com.ssafy.ourdoc.domain.classroom.entity.ClassRoom;

public interface ClassRoomQueryRepository {
	List<ClassRoom> findActiveClassBySchoolAndGrade(Long schoolId, int grade);

	List<ClassRoom> findByTeacher(Long userId);

	List<ClassRoom> findByTeacherAndYear(Long userId, Year year);
}
