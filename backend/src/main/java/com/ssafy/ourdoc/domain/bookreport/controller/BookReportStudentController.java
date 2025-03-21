package com.ssafy.ourdoc.domain.bookreport.controller;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.ourdoc.domain.bookreport.dto.BookReadLogRequest;
import com.ssafy.ourdoc.domain.bookreport.dto.BookReportDailyStatisticsDto;
import com.ssafy.ourdoc.domain.bookreport.dto.BookReportLatestAiFeedbackResponse;
import com.ssafy.ourdoc.domain.bookreport.dto.BookReportMonthlyStatisticsDto;
import com.ssafy.ourdoc.domain.bookreport.dto.BookReportMyRankResponse;
import com.ssafy.ourdoc.domain.bookreport.dto.BookReportSaveResponse;
import com.ssafy.ourdoc.domain.bookreport.dto.BookReportStatisticsRequest;
import com.ssafy.ourdoc.domain.bookreport.dto.BookReportStatisticsResponse;
import com.ssafy.ourdoc.domain.bookreport.dto.student.BookReportListRequest;
import com.ssafy.ourdoc.domain.bookreport.dto.student.BookReportStudentListResponse;
import com.ssafy.ourdoc.domain.bookreport.service.BookReportStudentService;
import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.global.annotation.CheckOwner;
import com.ssafy.ourdoc.global.annotation.Login;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bookreports/students")
public class BookReportStudentController {

	private final BookReportStudentService bookReportStudentService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public BookReportSaveResponse createBookReportStudent(@Login User user,
		@Valid @RequestBody BookReadLogRequest request) {
		return bookReportStudentService.saveBookReport(user, request);
	}

	// 학생 독서록리스트 전체 조회
	@GetMapping
	public BookReportStudentListResponse getBookReportList(@Login User user,
		@Valid @ModelAttribute BookReportListRequest request, @PageableDefault(page = 0, size = 10) Pageable pageable) {
		return bookReportStudentService.getBookReports(user, request, pageable);
	}

	@CheckOwner(target = "bookReportId")
	@DeleteMapping("/{bookReportId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteBookReport(@Login User user, @PathVariable("bookReportId") Long bookReportId) {
		bookReportStudentService.deleteBookReport(user, bookReportId);
	}

	@PostMapping("/{bookReportId}/homework/{homeworkId}")
	@ResponseStatus(HttpStatus.OK)
	public void submitBookReportToHomework(@Login User user, @PathVariable("bookReportId") Long bookReportId,
		@PathVariable("homeworkId") Long homeworkId) {
		bookReportStudentService.submitBookReportToHomework(user, bookReportId, homeworkId);
	}

	@DeleteMapping("/{bookReportId}/homework/{homeworkId}")
	@ResponseStatus(HttpStatus.OK)
	public void retrieveBookReportFromHomework(@Login User user, @PathVariable("bookReportId") Long bookReportId,
		@PathVariable("homeworkId") Long homeworkId) {
		bookReportStudentService.retrieveBookReportFromHomework(user, bookReportId, homeworkId);
	}

	@GetMapping("/statistics")
	public BookReportStatisticsResponse getBookReportStatistics(@Login User user,
		@Valid @ModelAttribute BookReportStatisticsRequest request) {
		return bookReportStudentService.getBookReportStatistics(user, request);
	}

	@GetMapping("/statistics/months")
	public List<BookReportMonthlyStatisticsDto> getMonthlyBookReportStatistics(@Login User user,
		@Valid @ModelAttribute BookReportStatisticsRequest request) {
		return bookReportStudentService.getMonthlyBookReportStatistics(user, request);
	}

	@GetMapping("/statistics/days")
	public List<BookReportDailyStatisticsDto> getDailyBookReportStatistics(@Login User user,
		@Valid @ModelAttribute BookReportStatisticsRequest request) {
		return bookReportStudentService.getDailyBookReportStatistics(user, request);
	}

	@GetMapping("/rank")
	public BookReportMyRankResponse getBookReportRank(@Login User user) {
		return bookReportStudentService.getBookReportRank(user);
	}

	@GetMapping("/feedback/ai/recent")
	public BookReportLatestAiFeedbackResponse getLatestAiFeedback(@Login User user) {
		return bookReportStudentService.getLatestAiFeedback(user);
	}

}
