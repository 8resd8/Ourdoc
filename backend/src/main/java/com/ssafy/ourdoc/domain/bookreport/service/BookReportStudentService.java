package com.ssafy.ourdoc.domain.bookreport.service;

import static com.ssafy.ourdoc.global.common.enums.ApproveStatus.*;
import static com.ssafy.ourdoc.global.common.enums.HomeworkStatus.*;
import static com.ssafy.ourdoc.global.common.enums.NotificationType.*;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.ssafy.ourdoc.domain.book.entity.Book;
import com.ssafy.ourdoc.domain.book.repository.BookRepository;
import com.ssafy.ourdoc.domain.bookreport.dto.BookReadLogRequest;
import com.ssafy.ourdoc.domain.bookreport.dto.BookReportDto;
import com.ssafy.ourdoc.domain.bookreport.dto.BookReportListResponse;
import com.ssafy.ourdoc.domain.bookreport.entity.BookReport;
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
	private final StudentClassRepository studentClassRepository;
	private final BookRepository bookRepository;
	private final NotificationService notificationService;

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


	public BookReportListResponse getBookReports(User user, int grade) {
		List<BookReport> bookReports = bookReportRepository.findByUserIdAndGrade(user.getId(), grade);

		List<BookReportDto> bookReportDtos = bookReports.stream()
			.map(report -> new BookReportDto(
				report.getAfterContent(),
				report.getCreatedAt(),
				report.getApproveTime() == null ? 없음 : 있음,
				report.getHomework() == null ? 미제출 : 제출
			))
			.toList();

		return new BookReportListResponse(bookReportDtos);
	}

	public void deleteBookReport(Long bookReportId) {
		BookReport bookReport = bookReportRepository.findById(bookReportId)
			.orElseThrow(() -> new NoSuchElementException("지울 독서록이 없습니다."));

		if (bookReport.getApproveTime() != null) {
			throw new IllegalArgumentException("승인이 된 독서록은 지울 수 없습니다.");
		}

		bookReportRepository.delete(bookReport);
	}

}
