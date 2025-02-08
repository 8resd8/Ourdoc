package com.ssafy.ourdoc.domain.bookreport.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.ourdoc.domain.bookreport.dto.teacher.ReportTeacherListResponse;
import com.ssafy.ourdoc.domain.bookreport.dto.teacher.ReportTeacherRequest;
import com.ssafy.ourdoc.domain.bookreport.service.BookReportTeacherService;
import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.global.annotation.Login;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("bookreports")
public class BookReportTeacherController {

	private final BookReportTeacherService bookReportTeacherService;

	@GetMapping("/teacher")
	public ReportTeacherListResponse getBookReportList(@Login User user, @RequestBody ReportTeacherRequest request) {
		return bookReportTeacherService.getBookReports(user, request);
	}
}
