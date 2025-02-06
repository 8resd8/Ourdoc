package com.ssafy.ourdoc.domain.bookreport.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.ourdoc.domain.bookreport.dto.BookReadLogRequest;
import com.ssafy.ourdoc.domain.bookreport.dto.BookReportListResponse;
import com.ssafy.ourdoc.domain.bookreport.dto.FeedbackRequest;
import com.ssafy.ourdoc.domain.bookreport.service.BookReportStudentService;
import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.global.annotation.Login;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bookreports")
public class BookReportStudentController {

	private final BookReportStudentService bookReportStudentService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void createBookReportStudent(@Login User user, @RequestBody BookReadLogRequest request) {
		bookReportStudentService.saveBookReport(user, request);
	}

	@PostMapping("/feedback")
	@ResponseStatus(HttpStatus.CREATED)
	public void createBookReportFeedback(@RequestBody FeedbackRequest request) {
		bookReportStudentService.saveBookReportFeedback(request);
	}

	@GetMapping
	public BookReportListResponse getBookReportList(@Login User user) {
		return bookReportStudentService.getBookReports(user);
	}

}
