package com.ssafy.ourdoc.domain.classroom.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.ourdoc.domain.classroom.dto.CreateClassRequest;
import com.ssafy.ourdoc.domain.classroom.service.ClassService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/teachers")
@RequiredArgsConstructor
public class TeacherClassController {

	private final ClassService classService;

	@PostMapping("/{teacherId}/class")
	@ResponseStatus(HttpStatus.CREATED)
	public void createClassRoom(@PathVariable("teacherId") Long teacherId,
		@RequestBody CreateClassRequest request) {
		classService.createClass(teacherId, request);
	}
}
