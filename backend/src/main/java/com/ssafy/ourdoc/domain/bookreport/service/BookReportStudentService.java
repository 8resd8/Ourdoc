package com.ssafy.ourdoc.domain.bookreport.service;

import static com.ssafy.ourdoc.global.common.enums.ApproveStatus.*;
import static com.ssafy.ourdoc.global.common.enums.HomeworkStatus.*;
import static com.ssafy.ourdoc.global.common.enums.NotificationType.*;
import static com.ssafy.ourdoc.global.common.enums.OcrCheck.*;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ssafy.ourdoc.domain.book.entity.Book;
import com.ssafy.ourdoc.domain.book.repository.BookRepository;
import com.ssafy.ourdoc.domain.bookreport.dto.BookReadLogRequest;
import com.ssafy.ourdoc.domain.bookreport.dto.BookReportDailyStatisticsDto;
import com.ssafy.ourdoc.domain.bookreport.dto.BookReportDto;
import com.ssafy.ourdoc.domain.bookreport.dto.BookReportHomeworkStudent;
import com.ssafy.ourdoc.domain.bookreport.dto.BookReportListResponse;
import com.ssafy.ourdoc.domain.bookreport.dto.BookReportMonthlyStatisticsDto;
import com.ssafy.ourdoc.domain.bookreport.dto.BookReportStatisticsRequest;
import com.ssafy.ourdoc.domain.bookreport.dto.BookReportStatisticsResponse;
import com.ssafy.ourdoc.domain.bookreport.entity.BookReport;
import com.ssafy.ourdoc.domain.bookreport.repository.BookReportRepository;
import com.ssafy.ourdoc.domain.notification.service.NotificationService;
import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.domain.user.student.entity.StudentClass;
import com.ssafy.ourdoc.domain.user.student.repository.StudentClassRepository;
import com.ssafy.ourdoc.global.common.enums.ApproveStatus;
import com.ssafy.ourdoc.global.common.enums.SubmitStatus;

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
		if (request.ocrCheck() == 사용 && (request.imageUrl() == null || request.imageUrl().trim().isEmpty())) {
			throw new IllegalArgumentException("OCR 사용 시 imageURL 입력이 필요합니다.");
		}

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

	public BookReportListResponse getBookReports(User user, int grade, Pageable pageable) {
		Page<BookReport> bookReports = bookReportRepository.findByUserIdAndGrade(user.getId(), grade, pageable);

		List<BookReportDto> bookReportDtos = bookReports.stream()
			.map(report -> new BookReportDto(
				report.getId(),
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

	public List<BookReportHomeworkStudent> getReportStudentHomeworkResponses(Long homeworkId, Long userId) {
		List<BookReportHomeworkStudent> convertDto = bookReportRepository.bookReportsHomeworkStudents(homeworkId,
				userId).stream()
			.map(dto -> new BookReportHomeworkStudent(
				dto.bookreportId(),
				dto.createdAt(),
				dto.homeworkId() != null ? SubmitStatus.제출 : SubmitStatus.미제출,
				dto.approveTime() != null ? ApproveStatus.있음 : ApproveStatus.없음))
			.toList();
		return convertDto;
	}

	public void deleteBookReport(Long bookReportId) {
		BookReport bookReport = bookReportRepository.findById(bookReportId)
			.orElseThrow(() -> new NoSuchElementException("지울 독서록이 없습니다."));

		if (bookReport.getApproveTime() != null) {
			throw new IllegalArgumentException("승인이 된 독서록은 지울 수 없습니다.");
		}

		bookReportRepository.delete(bookReport);
	}

	public BookReportStatisticsResponse getBookReportStatistics(User user, BookReportStatisticsRequest request) {
		long myCount = bookReportRepository.myBookReportsCount(user.getId(), request.grade());

		double averageCount = bookReportRepository.classAverageBookReportsCount(user.getId(), request.grade());

		long highestCount = bookReportRepository.classHighestBookReportCount(user.getId(), request.grade());

		return new BookReportStatisticsResponse((int)myCount, averageCount, (int)highestCount);
	}

	public List<BookReportMonthlyStatisticsDto> getMonthlyBookReportStatistics(User user,
		BookReportStatisticsRequest request) {
		return bookReportRepository.myMonthlyBookReportCount(user.getId(), request.grade());
	}

	public List<BookReportDailyStatisticsDto> getDailyBookReportStatistics(User user,
		BookReportStatisticsRequest request) {
		return bookReportRepository.myDailyBookReportCount(user.getId(), request.grade(), request.month());
	}

}
