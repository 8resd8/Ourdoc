package com.ssafy.ourdoc.domain.bookreport.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.ourdoc.domain.bookreport.dto.BookReadLogRequest;
import com.ssafy.ourdoc.domain.bookreport.dto.BookReportListResponse;
import com.ssafy.ourdoc.domain.bookreport.service.BookReportStudentService;
import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.global.annotation.Login;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bookreports/students")
public class BookReportStudentController {

	private final BookReportStudentService bookReportStudentService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void createBookReportStudent(@Login User user, @RequestBody BookReadLogRequest request) {
		bookReportStudentService.saveBookReport(user, request);
	}

	@GetMapping
	public BookReportListResponse getBookReportList(@Login User user, @RequestParam("grade") int grade) {
		return bookReportStudentService.getBookReports(user, grade);
	}

	@DeleteMapping("/{bookReportId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteBookReport(@PathVariable("bookReportId") Long bookReportId) {
		bookReportStudentService.deleteBookReport(bookReportId);
	}

}
