package com.ssafy.ourdoc.domain.classroom.repository;

import java.time.Year;
import java.util.List;

import com.ssafy.ourdoc.domain.classroom.dto.SchoolClassDto;
import com.ssafy.ourdoc.domain.classroom.entity.ClassRoom;

public interface ClassRoomQueryRepository {
	List<ClassRoom> findActiveClassBySchoolAndGrade(Long schoolId, int grade);

	List<SchoolClassDto> findByTeacher(Long userId);

	List<SchoolClassDto> findByTeacherAndYear(Long userId, Year year);
}
