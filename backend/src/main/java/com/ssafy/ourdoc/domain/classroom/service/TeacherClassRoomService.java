package com.ssafy.ourdoc.domain.classroom.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.ourdoc.domain.classroom.dto.teacher.TeacherClassRequest;
import com.ssafy.ourdoc.domain.classroom.dto.teacher.TeacherRoomResponse;
import com.ssafy.ourdoc.domain.classroom.dto.teacher.TeacherRoomStudentDto;
import com.ssafy.ourdoc.domain.classroom.dto.teacher.TeacherRoomStudentResponse;
import com.ssafy.ourdoc.domain.classroom.dto.teacher.TeachersRoomDto;
import com.ssafy.ourdoc.domain.classroom.repository.ClassRoomRepository;
import com.ssafy.ourdoc.domain.user.entity.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TeacherClassRoomService {

	private final ClassRoomRepository classRoomRepository;

	public TeacherRoomResponse getTeacherRoom(User user, TeacherClassRequest request) {
		List<TeachersRoomDto> findTeacherRooms = classRoomRepository.findByTeachersRoom(user.getId(), request);

		return new TeacherRoomResponse(findTeacherRooms);
	}

	public TeacherRoomStudentResponse getTeacherRoomStudent(User user, Long classId) {
		List<TeacherRoomStudentDto> findTeacherStudents = classRoomRepository.findByTeachersRoomStudent(user.getId(),
			classId);

		return new TeacherRoomStudentResponse(findTeacherStudents);
	}
}
