package com.ssafy.ourdoc.domain.classroom.repository;

import java.util.List;

import com.ssafy.ourdoc.domain.classroom.entity.ClassRoom;

public interface ClassRoomQueryRepository {
	List<ClassRoom> findActiveClassBySchoolAndGrade(Long schoolId, int grade);
}
