package com.ssafy.ourdoc.domain.classroom.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.ourdoc.domain.classroom.dto.SchoolDto;
import com.ssafy.ourdoc.domain.classroom.dto.SchoolResponse;
import com.ssafy.ourdoc.domain.classroom.repository.SchoolRepository;
import com.ssafy.ourdoc.domain.classroom.service.SchoolSaveService;
import com.ssafy.ourdoc.domain.classroom.service.SchoolService;
import com.ssafy.ourdoc.domain.classroom.util.CsvParser;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schools")
public class SchoolController {

	private final SchoolSaveService schoolSaveService;
	private final SchoolService schoolService;
	private final CsvParser csvParser;
	private final SchoolRepository schoolRepository;

	@GetMapping
	public ResponseEntity<Page<SchoolResponse>> findSchools(@RequestParam("schoolName") String schoolName,
		@PageableDefault(size = 10, page = 0) Pageable pageable) {
		return ResponseEntity.ok().body(schoolService.searchSchoolName(schoolName, pageable));
	}

	// @PostMapping
	// public String save(@RequestParam(name = "filePath") String filePath) {
	// 	// List<SchoolDto> schoolDtos = csvParser.parseSchoolCsv(filePath);
	// 	schoolSaveService.saveSchools(filePath);
	//
	// 	return "ok";
	// }
}
