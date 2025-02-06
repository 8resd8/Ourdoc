package com.ssafy.ourdoc.domain.classroom.controller;

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
@RequestMapping("/schools")
public class SchoolController {

	private final SchoolService schoolService;

	@GetMapping
	public ResponseEntity<List<SchoolResponse>> findSchools(@RequestParam("schoolName") String schoolName) {
		return ResponseEntity.ok().body(schoolService.searchSchoolName(schoolName));
	}
}
