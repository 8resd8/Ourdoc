package com.ssafy.ourdoc.domain.classroom.repository;

import java.time.Year;
import java.util.List;
import java.util.Map;

import com.ssafy.ourdoc.domain.classroom.dto.SchoolClassDto;
import com.ssafy.ourdoc.domain.classroom.dto.teacher.TeacherClassRequest;
import com.ssafy.ourdoc.domain.classroom.dto.teacher.TeacherRoomStudentDto;
import com.ssafy.ourdoc.domain.classroom.dto.teacher.TeachersRoomDto;
import com.ssafy.ourdoc.domain.classroom.entity.ClassRoom;

public interface ClassRoomQueryRepository {
	List<ClassRoom> findActiveClassBySchoolAndGrade(Long schoolId, int grade);

	List<SchoolClassDto> findByTeacher(Long userId);

	List<SchoolClassDto> findByTeacherAndYear(Long userId, Year year);

	Map<String, List<TeachersRoomDto>> findByTeachersRoom(Long userId);

	List<TeacherRoomStudentDto> findByTeachersRoomStudent(Long userId, Long classId);

	List<SchoolClassDto> findByStudent(Long userId);
}
