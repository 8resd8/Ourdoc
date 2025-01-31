package com.ssafy.ourdoc.domain.classroom.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.ourdoc.domain.classroom.dto.SchoolResponse;
import com.ssafy.ourdoc.domain.classroom.service.SchoolService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/class")
public class ClassRoomController {
	private final SchoolService schoolService;

	@GetMapping("/school")
	public ResponseEntity<List<SchoolResponse>> getSchoolList(@RequestParam(required = false) String schoolName) throws
		IOException {
		List<SchoolResponse> schools = schoolService.parseSchool(schoolName);
		return ResponseEntity.ok(schools);
	}
}
