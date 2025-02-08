package com.ssafy.ourdoc.domain.bookreport.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.ourdoc.domain.bookreport.dto.teacher.ReportCommentRequest;
import com.ssafy.ourdoc.domain.bookreport.dto.teacher.ReportTeacherListResponse;
import com.ssafy.ourdoc.domain.bookreport.dto.teacher.ReportTeacherRequest;
import com.ssafy.ourdoc.domain.bookreport.service.BookReportTeacherService;
import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.global.annotation.Login;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bookreports/teachers")
public class BookReportTeacherController {

	private final BookReportTeacherService bookReportTeacherService;

	@GetMapping
	public ReportTeacherListResponse getBookReportList(@Login User user, @RequestBody ReportTeacherRequest request) {
		return bookReportTeacherService.getBookReports(user, request);
	}

	@PatchMapping("/{bookReportId}/stamp")
	@ResponseStatus(HttpStatus.CREATED)
	public void approveBookReport(@Login User user, @PathVariable("bookReportId") Long bookReportId) {
		bookReportTeacherService.approveStamp(user, bookReportId);
	}

	@PostMapping("/{bookreportId}")
	@ResponseStatus(HttpStatus.CREATED)
	public void createBookReportComment(@Login User user, @PathVariable("bookreportId") Long bookreportId,
		@RequestBody ReportCommentRequest request) {
		bookReportTeacherService.createComment(user, bookreportId, request);
	}

	@PatchMapping("/{bookreportId}")
	@ResponseStatus(HttpStatus.OK)
	public void updateBookReportComment(@PathVariable("bookreportId") Long bookreportId,
		@RequestBody ReportCommentRequest request) {
		bookReportTeacherService.updateComment(bookreportId, request);
	}

	@DeleteMapping("/{bookreportId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteBookReportComment(@PathVariable("bookreportId") Long bookreportId) {
		bookReportTeacherService.deleteComment(bookreportId);
	}

}
