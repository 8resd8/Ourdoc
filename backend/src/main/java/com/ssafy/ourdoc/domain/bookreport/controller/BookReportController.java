package com.ssafy.ourdoc.domain.bookreport.controller;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.ourdoc.domain.bookreport.dto.BookReportDetailResponse;
import com.ssafy.ourdoc.domain.bookreport.dto.FeedbackRequest;
import com.ssafy.ourdoc.domain.bookreport.service.BookReportService;
import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.global.annotation.CheckOwner;
import com.ssafy.ourdoc.global.annotation.Login;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bookreports")
public class BookReportController {

	private final BookReportService bookReportService;

	@CheckOwner(target = "bookReportId")
	@GetMapping("/{bookReportId}")
	public BookReportDetailResponse getBookReportList(@Login User user,
		@PathVariable("bookReportId") Long bookReportId) {
		return bookReportService.getBookReportDetail(bookReportId);
	}

	@PostMapping("/feedback")
	@ResponseStatus(HttpStatus.CREATED)
	public void createBookReportFeedback(@RequestBody FeedbackRequest request) {
		bookReportService.saveBookReportFeedback(request);
	}

}
