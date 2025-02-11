package com.ssafy.ourdoc.domain.classroom.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.ourdoc.domain.classroom.dto.CreateClassRequest;
import com.ssafy.ourdoc.domain.classroom.dto.teacher.TeacherRoomResponse;
import com.ssafy.ourdoc.domain.classroom.service.ClassService;
import com.ssafy.ourdoc.domain.classroom.service.TeacherClassRoomService;
import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.global.annotation.Login;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/teachers")
@RequiredArgsConstructor
public class TeacherClassController {

	private final ClassService classService;
	private final TeacherClassRoomService teacherClassRoomService;

	@PostMapping("/class")
	@ResponseStatus(HttpStatus.CREATED)
	public void createClassRoom(@Login User user, @RequestBody CreateClassRequest request) {
		classService.createClass(user, request);
	}

	// 해당 교사의 전체 학교 - 학년 - 반 조회
	@GetMapping("/year-class")
	public TeacherRoomResponse getTeachersRoom(@Login User user) {
		return teacherClassRoomService.getTeacherRoom(user);
	}

	// 해당 학년 - 반의 학생 조회
}
