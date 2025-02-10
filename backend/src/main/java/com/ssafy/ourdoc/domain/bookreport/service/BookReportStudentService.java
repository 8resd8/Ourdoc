package com.ssafy.ourdoc.domain.bookreport.service;

import static com.ssafy.ourdoc.global.common.enums.ApproveStatus.*;
import static com.ssafy.ourdoc.global.common.enums.EvaluatorType.*;
import static com.ssafy.ourdoc.global.common.enums.HomeworkStatus.*;
import static com.ssafy.ourdoc.global.common.enums.NotificationType.*;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ssafy.ourdoc.domain.book.entity.Book;
import com.ssafy.ourdoc.domain.book.repository.BookRepository;
import com.ssafy.ourdoc.domain.bookreport.dto.BookReadLogRequest;
import com.ssafy.ourdoc.domain.bookreport.dto.BookReportDto;
import com.ssafy.ourdoc.domain.bookreport.dto.BookReportListResponse;
import com.ssafy.ourdoc.domain.bookreport.dto.FeedbackRequest;
import com.ssafy.ourdoc.domain.bookreport.entity.BookReport;
import com.ssafy.ourdoc.domain.bookreport.entity.BookReportFeedBack;
import com.ssafy.ourdoc.domain.bookreport.repository.BookReportFeedbackRepository;
import com.ssafy.ourdoc.domain.bookreport.repository.BookReportRepository;
import com.ssafy.ourdoc.domain.notification.service.NotificationService;
import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.domain.user.student.entity.StudentClass;
import com.ssafy.ourdoc.domain.user.student.repository.StudentClassRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class BookReportStudentService {

	private final BookReportRepository bookReportRepository;
	private final BookReportFeedbackRepository feedbackRepository;
	private final StudentClassRepository studentClassRepository;
	private final BookRepository bookRepository;
	private final NotificationService notificationService;

	// 독서록 작성
	public void saveBookReport(User user, BookReadLogRequest request) {
		StudentClass studentClass = studentClassRepository.findStudentClassByUserId(user.getId()).orElseThrow();
		Book book = bookRepository.findById(request.bookId()).orElseThrow();

		BookReport bookReport = BookReport.builder()
			.studentClass(studentClass)
			.book(book)
			.beforeContent(request.beforeContent())
			.ocrCheck(request.ocrCheck())
			.imagePath(request.imageUrl())
			.build();

		bookReportRepository.save(bookReport);
		notificationService.sendNotifyStudentFromTeacher(user, 독서록); // 알림전송
	}

	// 작성한 독서록 기반 피드백 받은것 처리
	public void saveBookReportFeedback(FeedbackRequest request) {
		BookReport bookReport = bookReportRepository.findById(request.bookReportId()).orElseThrow();
		// 교정 내용 반영
		bookReport.saveAfterContent(request.afterContent());

		BookReportFeedBack bookReportFeedBack = BookReportFeedBack.builder().bookReport(bookReport).type(인공지능).build();

		// 피드백 내용 저장
		feedbackRepository.save(bookReportFeedBack);
	}

	public BookReportListResponse getBookReports(User user, int grade, Pageable pageable) {
		Page<BookReport> bookReports = bookReportRepository.findByUserIdAndGrade(user.getId(), grade, pageable);

		List<BookReportDto> bookReportDtos = bookReports.stream()
			.map(report -> new BookReportDto(
				report.getAfterContent(),
				report.getCreatedAt(),
				report.getApproveTime() == null ? 없음 : 있음,
				report.getHomework() == null ? 미제출 : 제출
			))
			.toList();

		Page<BookReportDto> bookReportDtoPage = new PageImpl<>(bookReportDtos, pageable,
			bookReports.getTotalElements());
		return new BookReportListResponse(bookReportDtoPage);
	}

}
