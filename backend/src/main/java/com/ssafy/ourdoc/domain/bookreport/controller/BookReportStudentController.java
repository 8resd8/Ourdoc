package com.ssafy.ourdoc.domain.bookreport.controller;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.ourdoc.domain.bookreport.dto.BookReadLogRequest;
import com.ssafy.ourdoc.domain.bookreport.dto.BookReportDailyStatisticsDto;
import com.ssafy.ourdoc.domain.bookreport.dto.BookReportListResponse;
import com.ssafy.ourdoc.domain.bookreport.dto.BookReportMonthlyStatisticsDto;
import com.ssafy.ourdoc.domain.bookreport.dto.BookReportStatisticsRequest;
import com.ssafy.ourdoc.domain.bookreport.dto.BookReportStatisticsResponse;
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
	public BookReportListResponse getBookReportList(@Login User user, @RequestParam("grade") int grade,
		@PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
		return bookReportStudentService.getBookReports(user, grade, pageable);
	}

	@DeleteMapping("/{bookReportId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteBookReport(@PathVariable("bookReportId") Long bookReportId) {
		bookReportStudentService.deleteBookReport(bookReportId);
	}

	@GetMapping("/statistics")
	public BookReportStatisticsResponse getBookReportStatistics(@Login User user, @ModelAttribute
	BookReportStatisticsRequest request) {
		return bookReportStudentService.getBookReportStatistics(user, request);
	}

	@GetMapping("/statistics/months")
	public List<BookReportMonthlyStatisticsDto> getMonthlyBookReportStatistics(@Login User user,
		@ModelAttribute BookReportStatisticsRequest request) {
		return bookReportStudentService.getMonthlyBookReportStatistics(user, request);
	}

	@GetMapping("/statistics/days")
	public List<BookReportDailyStatisticsDto> getDailyBookReportStatistics(@Login User user,
		@ModelAttribute BookReportStatisticsRequest request) {
		return bookReportStudentService.getDailyBookReportStatistics(user, request);
	}

}
