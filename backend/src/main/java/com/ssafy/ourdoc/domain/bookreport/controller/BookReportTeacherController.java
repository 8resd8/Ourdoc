package com.ssafy.ourdoc.domain.bookreport.controller;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.ourdoc.domain.bookreport.dto.BookReportDailyStatisticsDto;
import com.ssafy.ourdoc.domain.bookreport.dto.BookReportMonthlyStatisticsDto;
import com.ssafy.ourdoc.domain.bookreport.dto.BookReportRankResponse;
import com.ssafy.ourdoc.domain.bookreport.dto.BookReportStatisticsRequest;
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
	public ReportTeacherListResponse getBookReportList(@Login User user, @ModelAttribute ReportTeacherRequest request,
		@PageableDefault(page = 0, size = 10) Pageable pageable) {
		return bookReportTeacherService.getBookReports(user, request, pageable);
	}

	@PatchMapping("/{bookReportId}/stamp")
	@ResponseStatus(HttpStatus.CREATED)
	public void approveBookReport(@Login User user, @PathVariable("bookReportId") Long bookReportId) {
		bookReportTeacherService.approveStamp(user, bookReportId);
	}

	@PostMapping("/{bookreportId}/comment")
	@ResponseStatus(HttpStatus.CREATED)
	public void createBookReportComment(@Login User user, @PathVariable("bookreportId") Long bookreportId,
		@RequestBody ReportCommentRequest request) {
		bookReportTeacherService.createComment(user, bookreportId, request);
	}

	@PatchMapping("/{bookreportId}/comment")
	@ResponseStatus(HttpStatus.OK)
	public void updateBookReportComment(@PathVariable("bookreportId") Long bookreportId,
		@RequestBody ReportCommentRequest request) {
		bookReportTeacherService.updateComment(bookreportId, request);
	}

	@DeleteMapping("/{bookreportId}/comment")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteBookReportComment(@PathVariable("bookreportId") Long bookreportId) {
		bookReportTeacherService.deleteComment(bookreportId);
	}

	@GetMapping("/statistics/months")
	public List<BookReportMonthlyStatisticsDto> getMonthlyBookReportStatistics(@Login User user) {
		return bookReportTeacherService.getMonthlyBookReportStatistics(user);
	}

	@GetMapping("/statistics/days")
	public List<BookReportDailyStatisticsDto> getDailyBookReportStatistics(@Login User user, @ModelAttribute
	BookReportStatisticsRequest request) {
		return bookReportTeacherService.getDailyBookReportStatistics(user, request.month());
	}

	@GetMapping("/rank")
	public BookReportRankResponse getBookReportRank(@Login User user) {
		return bookReportTeacherService.getBookReportRank(user);
	}

}
