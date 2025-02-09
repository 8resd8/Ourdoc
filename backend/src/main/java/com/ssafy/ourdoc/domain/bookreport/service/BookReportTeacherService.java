package com.ssafy.ourdoc.domain.bookreport.service;

import static com.ssafy.ourdoc.global.common.enums.ApproveStatus.*;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.ourdoc.domain.bookreport.dto.teacher.ReportCommentRequest;
import com.ssafy.ourdoc.domain.bookreport.dto.teacher.ReportTeacherListResponse;
import com.ssafy.ourdoc.domain.bookreport.dto.teacher.ReportTeacherRequest;
import com.ssafy.ourdoc.domain.bookreport.dto.teacher.ReportTeacherResponse;
import com.ssafy.ourdoc.domain.bookreport.dto.teacher.ReportTeacherResponseWithId;
import com.ssafy.ourdoc.domain.bookreport.entity.BookReport;
import com.ssafy.ourdoc.domain.bookreport.entity.BookReportFeedBack;
import com.ssafy.ourdoc.domain.bookreport.repository.BookReportRepository;
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

	public ReportTeacherListResponse getBookReports(User user, ReportTeacherRequest request) {
		List<ReportTeacherResponse> convertDto = getReportTeacherResponses(
			user, request);

		return new ReportTeacherListResponse(convertDto);
	}

	private List<ReportTeacherResponse> getReportTeacherResponses(User user, ReportTeacherRequest request) {
		List<ReportTeacherResponse> convertDto = bookReportRepository.bookReports(user.getId(), request).stream()
			.map(dto -> new ReportTeacherResponse(
				dto.bookTitle(),
				dto.studentNumber(),
				dto.studentName(),
				dto.createdAt(),
				(dto.approveTime() == null) ? 있음 : 없음
			))
			.toList();
		return convertDto;
	}

	public List<ReportTeacherResponseWithId> getReportTeacherHomeworkResponses(Long homeworkId) {
		List<ReportTeacherResponseWithId> convertDto = bookReportRepository.bookReportsHomework(homeworkId).stream()
			.map(dto -> new ReportTeacherResponseWithId(
				dto.id(),
				dto.studentNumber(),
				dto.studentName(),
				dto.createdAt(),
				(dto.approveTime() == null) ? 있음 : 없음
			))
			.toList();
		return convertDto;
	}

	public void approveStamp(User user, Long bookReportId) {
		BookReport bookReport = getBookReport(bookReportId);

		bookReport.approveStamp();

		StudentClass studentClass = bookReport.getStudentClass();
		notificationService.sendNotifyTeacherFromStudent(user, studentClass.getId());
	}

	public void createComment(User user, Long bookReportId, ReportCommentRequest request) {
		BookReportFeedBack bookReportFeedBack = getBookReport(bookReportId).getBookReportFeedBack();
		bookReportFeedBack.updateComment(request.comment());

		StudentClass studentClass = getBookReport(bookReportId).getStudentClass();
		notificationService.sendNotifyTeacherFromStudent(user, studentClass.getId());
	}

	public void updateComment(Long bookReportId, ReportCommentRequest request) {
		BookReportFeedBack bookReportFeedBack = getBookReport(bookReportId).getBookReportFeedBack();
		bookReportFeedBack.updateComment(request.comment());
	}

	public void deleteComment(Long bookReportId) {
		BookReportFeedBack bookReportFeedBack = getBookReport(bookReportId).getBookReportFeedBack();
		bookReportFeedBack.updateComment(null);
	}

	private BookReport getBookReport(Long bookReportId) {
		return bookReportRepository.findById(bookReportId)
			.orElseThrow(() -> new NoSuchElementException("도장찍을 독서록이 없습니다."));
	}
}
