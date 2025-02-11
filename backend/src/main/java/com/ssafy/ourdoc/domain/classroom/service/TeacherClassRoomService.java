package com.ssafy.ourdoc.domain.classroom.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.ourdoc.domain.classroom.dto.teacher.TeacherRoomResponse;
import com.ssafy.ourdoc.domain.classroom.dto.teacher.TeachersRoomDto;
import com.ssafy.ourdoc.domain.classroom.entity.ClassRoom;
import com.ssafy.ourdoc.domain.classroom.repository.ClassRoomRepository;
import com.ssafy.ourdoc.domain.user.entity.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TeacherClassRoomService {

	private final ClassRoomRepository classRoomRepository;

	public TeacherRoomResponse getTeacherRoom(User user) {
		List<TeachersRoomDto> findTeacherRooms = classRoomRepository.findByTeachersRoom(user.getId());

		return new TeacherRoomResponse(findTeacherRooms);
	}
}
