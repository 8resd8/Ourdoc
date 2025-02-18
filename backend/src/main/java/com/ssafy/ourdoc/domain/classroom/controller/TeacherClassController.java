package com.ssafy.ourdoc.domain.classroom.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.ourdoc.domain.classroom.dto.teacher.TeacherClassRequest;
import com.ssafy.ourdoc.domain.classroom.dto.teacher.TeacherRoomResponse;
import com.ssafy.ourdoc.domain.classroom.dto.teacher.TeacherRoomStudentResponse;
import com.ssafy.ourdoc.domain.classroom.service.TeacherClassRoomService;
import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.global.annotation.Login;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/teachers")
@RequiredArgsConstructor
public class TeacherClassController {

	private final TeacherClassRoomService teacherClassRoomService;

	// 해당 교사의 연도, 학교, 학년, 반 조회
	@GetMapping("/classes")
	public TeacherRoomResponse getTeachersRoom(@Login User user) {
		return teacherClassRoomService.getTeacherRoom(user);
	}

	// 교사 반 학생 조회
	@GetMapping("/classes/students")
	public TeacherRoomStudentResponse getTeachersRoomStudents(@Login User user,
		@RequestParam(value = "classId") Long classId) {
		return teacherClassRoomService.getTeacherRoomStudent(user, classId);
	}

}
