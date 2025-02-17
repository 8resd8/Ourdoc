package com.ssafy.ourdoc.domain.bookreport.service;

import static com.ssafy.ourdoc.global.common.enums.ApproveStatus.*;
import static com.ssafy.ourdoc.global.common.enums.EvaluatorType.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.ourdoc.domain.bookreport.dto.BookReportDailyStatisticsDto;
import com.ssafy.ourdoc.domain.bookreport.dto.BookReportMonthlyStatisticsDto;
import com.ssafy.ourdoc.domain.bookreport.dto.BookReportRankDto;
import com.ssafy.ourdoc.domain.bookreport.dto.BookReportRankResponse;
import com.ssafy.ourdoc.domain.bookreport.dto.teacher.BookReportTeacher;
import com.ssafy.ourdoc.domain.bookreport.dto.teacher.ReportCommentRequest;
import com.ssafy.ourdoc.domain.bookreport.dto.teacher.ReportTeacherListResponse;
import com.ssafy.ourdoc.domain.bookreport.dto.teacher.ReportTeacherRequest;
import com.ssafy.ourdoc.domain.bookreport.dto.teacher.ReportTeacherResponse;
import com.ssafy.ourdoc.domain.bookreport.entity.BookReport;
import com.ssafy.ourdoc.domain.bookreport.entity.BookReportFeedBack;
import com.ssafy.ourdoc.domain.bookreport.repository.BookReportFeedbackRepository;
import com.ssafy.ourdoc.domain.bookreport.repository.BookReportRepository;
import com.ssafy.ourdoc.domain.bookreport.repository.BookReportStatisticRepository;
import com.ssafy.ourdoc.domain.notification.service.NotificationService;
import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.domain.user.student.entity.StudentClass;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class BookReportTeacherService {

	private final BookReportRepository bookReportRepository;
	private final NotificationService notificationService;
	private final BookReportFeedbackRepository bookReportFeedbackRepository;
	private final BookReportStatisticRepository bookReportStatisticRepository;

	public ReportTeacherListResponse getBookReports(User user, ReportTeacherRequest request, Pageable pageable) {
		Page<ReportTeacherResponse> pageDto = getReportTeacherResponses(user, request, pageable);
		return new ReportTeacherListResponse(pageDto);
	}

	private Page<ReportTeacherResponse> getReportTeacherResponses(User user, ReportTeacherRequest request,
		Pageable pageable) {
		Page<ReportTeacherResponse> pageDto = bookReportRepository.bookReports(user.getId(), request, pageable)
			.map(dto -> new ReportTeacherResponse(
				dto.bookReportId(),
				dto.bookTitle(),
				dto.studentNumber(),
				dto.studentName(),
				dto.createdAt(),
				(dto.approveTime() != null) ? 있음 : 없음
			));

		return pageDto;
	}

	public List<BookReportTeacher> getReportTeacherHomeworkResponses(Long homeworkId) {
		List<BookReportTeacher> convertDto = bookReportRepository.bookReportsHomework(homeworkId).stream()
			.map(dto -> new BookReportTeacher(
				dto.id(),
				dto.studentNumber(),
				dto.studentName(),
				dto.createdAt(),
				(dto.approveTime() != null) ? 있음 : 없음
			))
			.toList();
		return convertDto;
	}

	public Page<BookReportTeacher> getReportTeacherHomeworkPageResponses(Long homeworkId,
		String approveStatus, Pageable pageable) {
		Page<BookReportTeacher> pageDto = bookReportRepository.bookReportsHomeworkPage(homeworkId,
				approveStatus, pageable)
			.map(dto -> new BookReportTeacher(
				dto.id(),
				dto.studentNumber(),
				dto.studentName(),
				dto.createdAt(),
				(dto.approveTime() != null) ? 있음 : 없음
			));

		return pageDto;
	}

	public Page<BookReportTeacher> getReportTeacherPageResponses(Long bookId, Pageable pageable) {
		Page<BookReportTeacher> pageDto = bookReportRepository.bookReportsTeacherPage(bookId, pageable)
			.map(dto -> new BookReportTeacher(
				dto.id(),
				dto.studentNumber(),
				dto.studentName(),
				dto.createdAt(),
				(dto.approveTime() != null) ? 있음 : 없음
			));

		return pageDto;
	}

	public void approveStamp(User user, Long bookReportId) {
		BookReport bookReport = getBookReport(bookReportId);

		bookReport.approveStamp();

		StudentClass studentClass = bookReport.getStudentClass();
		notificationService.sendNotifyTeacherFromStudent(user, studentClass.getId());
	}

	public void createComment(User user, Long bookReportId, ReportCommentRequest request) {
		BookReportFeedBack bookReportFeedBack = BookReportFeedBack.builder()
			.bookReport(getBookReport(bookReportId))
			.comment(request.comment())
			.type(교사)
			.build();
		bookReportFeedbackRepository.save(bookReportFeedBack);

		StudentClass studentClass = getBookReport(bookReportId).getStudentClass();
		notificationService.sendNotifyTeacherFromStudent(user, studentClass.getId());
	}

	public void updateComment(Long bookReportId, ReportCommentRequest request) {
		BookReportFeedBack bookReportFeedBack = getTeacherReportFeedBack(bookReportId);
		bookReportFeedBack.updateTeacherComment(request.comment());
	}

	public void deleteComment(Long bookReportId) {
		BookReportFeedBack bookReportFeedBack = getTeacherReportFeedBack(bookReportId);
		bookReportFeedbackRepository.delete(bookReportFeedBack);
	}

	private BookReport getBookReport(Long bookReportId) {
		return bookReportRepository.findById(bookReportId)
			.orElseThrow(() -> new NoSuchElementException("도장찍을 독서록이 없습니다."));
	}

	private BookReportFeedBack getTeacherReportFeedBack(Long bookReportId) {
		return bookReportFeedbackRepository.findByBookReportIdAndEvaluatorType(bookReportId, 교사)
			.orElseThrow(() -> new NoSuchElementException("피드백 독서록이 없습니다."));
	}

	public List<BookReportMonthlyStatisticsDto> getMonthlyBookReportStatistics(User user) {
		return bookReportStatisticRepository.classMonthlyBookReportCount(user.getId());
	}

	public List<BookReportDailyStatisticsDto> getDailyBookReportStatistics(User user, int month) {
		return bookReportStatisticRepository.classDailyBookReportCount(user.getId(), month);
	}

	public BookReportRankResponse getBookReportRank(User user) {
		List<BookReportRankDto> rankList = bookReportStatisticRepository.bookReportRank(user.getId());
		List<BookReportRankDto> podiumList = new ArrayList<>();
		int rank = 0;
		int totalCount = 0;
		for (BookReportRankDto rankDto : rankList) {
			rank++;
			long readCount = rankDto.readCount();
			totalCount += readCount;
			if (rank < 4) {
				podiumList.add(new BookReportRankDto(rankDto.studentNumber(), rankDto.name(), (int)readCount, rank,
					rankDto.profileImagePath()));
			}
		}

		return new BookReportRankResponse(podiumList, totalCount);
	}
}
