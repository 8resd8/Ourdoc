package com.ssafy.ourdoc.domain.bookreport.service;

import static com.ssafy.ourdoc.global.common.enums.NotificationType.*;
import static com.ssafy.ourdoc.global.common.enums.OcrCheck.*;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ssafy.ourdoc.domain.award.repository.AwardRepository;
import com.ssafy.ourdoc.domain.book.entity.Book;
import com.ssafy.ourdoc.domain.book.entity.Homework;
import com.ssafy.ourdoc.domain.book.repository.BookRepository;
import com.ssafy.ourdoc.domain.book.repository.HomeworkRepository;
import com.ssafy.ourdoc.domain.bookreport.dto.BookReadLogRequest;
import com.ssafy.ourdoc.domain.bookreport.dto.BookReportDailyStatisticsDto;
import com.ssafy.ourdoc.domain.bookreport.dto.BookReportLatestAiFeedbackResponse;
import com.ssafy.ourdoc.domain.bookreport.dto.BookReportMonthlyStatisticsDto;
import com.ssafy.ourdoc.domain.bookreport.dto.BookReportMyRankDto;
import com.ssafy.ourdoc.domain.bookreport.dto.BookReportMyRankResponse;
import com.ssafy.ourdoc.domain.bookreport.dto.BookReportSaveResponse;
import com.ssafy.ourdoc.domain.bookreport.dto.BookReportStatisticsRequest;
import com.ssafy.ourdoc.domain.bookreport.dto.BookReportStatisticsResponse;
import com.ssafy.ourdoc.domain.bookreport.dto.BookReportStudent;
import com.ssafy.ourdoc.domain.bookreport.dto.student.BookReportListDto;
import com.ssafy.ourdoc.domain.bookreport.dto.student.BookReportListDtoConvert;
import com.ssafy.ourdoc.domain.bookreport.dto.student.BookReportListRequest;
import com.ssafy.ourdoc.domain.bookreport.dto.student.BookReportStudentListResponse;
import com.ssafy.ourdoc.domain.bookreport.entity.BookReport;
import com.ssafy.ourdoc.domain.bookreport.repository.BookReportRepository;
import com.ssafy.ourdoc.domain.bookreport.repository.BookReportStatisticRepository;
import com.ssafy.ourdoc.domain.classroom.entity.ClassRoom;
import com.ssafy.ourdoc.domain.notification.service.NotificationService;
import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.domain.user.student.entity.StudentClass;
import com.ssafy.ourdoc.domain.user.student.repository.StudentClassRepository;
import com.ssafy.ourdoc.global.common.enums.Active;
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
	private final HomeworkRepository homeworkRepository;
	private final BookReportStatisticRepository bookReportStatisticRepository;
	private final AwardRepository awardRepository;

	public BookReportSaveResponse saveBookReport(User user, BookReadLogRequest request) {
		if (request.ocrCheck() == 사용 && (request.imageUrl() == null || request.imageUrl().trim().isEmpty())) {
			throw new IllegalArgumentException("OCR 사용 시 imageURL 입력이 필요합니다.");
		}

		StudentClass studentClass = studentClassRepository.findByUserIdAndActive(user.getId(), Active.활성).orElseThrow();
		Book book = bookRepository.findById(request.bookId()).orElseThrow();

		BookReport bookReport = BookReport.builder()
			.studentClass(studentClass)
			.book(book)
			.homework(findHomeworkById(request))
			.beforeContent(request.beforeContent())
			.ocrCheck(request.ocrCheck())
			.imagePath(request.imageUrl())
			.build();

		BookReport saveBookReport = bookReportRepository.save(bookReport);
		notificationService.sendNotifyStudentFromTeacher(user, 독서록); // 알림전송
		return new BookReportSaveResponse(saveBookReport.getId());
	}

	private Homework findHomeworkById(BookReadLogRequest request) {
		if (request.homeworkId() == null) {
			return null;
		}
		return homeworkRepository.findById(request.homeworkId()).orElse(null);
	}

	public BookReportStudentListResponse getBookReports(User user, BookReportListRequest request, Pageable pageable) {
		Page<BookReportListDto> bookReportLists = bookReportRepository.bookReportList(user, request,
			pageable);
		List<BookReportListDtoConvert> convertList = bookReportLists.stream()
			.map(report -> new BookReportListDtoConvert(
				report.bookId(),
				report.bookReportId(),
				report.bookTitle(),
				report.bookImagePath(),
				report.homework() != null,
				report.createdAt()
			))
			.toList();

		Page<BookReportListDtoConvert> convertedPage = new PageImpl<>(convertList, pageable,
			bookReportLists.getTotalElements());

		return new BookReportStudentListResponse(convertedPage);
	}

	public List<BookReportStudent> getReportStudentHomeworkResponses(Long bookId, Long userId) {
		List<BookReportStudent> convertDto = bookReportRepository.bookReportsHomeworkStudents(bookId,
				userId)
			.stream()
			.map(dto -> new BookReportStudent(
				dto.bookreportId(),
				dto.beforeContent(),
				dto.createdAt(),
				dto.homeworkId() != null ? SubmitStatus.제출 : SubmitStatus.미제출,
				dto.approveTime() != null ? ApproveStatus.있음 : ApproveStatus.없음))
			.toList();
		return convertDto;
	}

	public Page<BookReportStudent> getReportStudentHomeworkPageResponses(Long bookId, Long userId,
		Pageable pageable) {
		Page<BookReportStudent> pageDto = bookReportRepository.bookReportsHomeworkStudentsPage(bookId,
				userId, pageable)
			.map(dto -> new BookReportStudent(
				dto.bookreportId(),
				dto.beforeContent(),
				dto.createdAt(),
				dto.homeworkId() != null ? SubmitStatus.제출 : SubmitStatus.미제출,
				dto.approveTime() != null ? ApproveStatus.있음 : ApproveStatus.없음
			));
		return pageDto;
	}

	public void submitBookReportToHomework(User user, Long bookReportId, Long homeworkId) {
		BookReport bookReport = bookReportRepository.findById(bookReportId)
			.orElseThrow(() -> new NoSuchElementException("숙제로 제출할 독서록이 없습니다."));
		Homework homework = homeworkRepository.findById(homeworkId)
			.orElseThrow(() -> new NoSuchElementException("해당하는 숙제가 없습니다."));

		ClassRoom classRoom = homework.getClassRoom();
		StudentClass studentClass = studentClassRepository.findByUserAndClassRoom(user, classRoom);
		if (studentClass == null) {
			throw new IllegalArgumentException("해당 숙제에 해당하는 학급의 학생이 아닙니다.");
		}

		int submitCount = bookReportRepository.countByUserIdAndHomeworkId(user.getId(), homeworkId);
		if (bookReport.getHomework() != null || submitCount > 0) {
			throw new IllegalArgumentException("숙제로 제출한 독서록이 이미 있습니다.");
		}

		bookReport.submitToHomework(homework);
		bookReportRepository.save(bookReport);
	}

	public void retrieveBookReportFromHomework(User user, Long bookReportId, Long homeworkId) {
		BookReport bookReport = bookReportRepository.findById(bookReportId)
			.orElseThrow(() -> new NoSuchElementException("숙제에서 회수할 독서록이 없습니다."));
		Homework homework = homeworkRepository.findById(homeworkId)
			.orElseThrow(() -> new NoSuchElementException("해당하는 숙제가 없습니다."));

		ClassRoom classRoom = homework.getClassRoom();
		StudentClass studentClass = studentClassRepository.findByUserAndClassRoom(user, classRoom);
		if (studentClass == null) {
			throw new IllegalArgumentException("해당 숙제에 해당하는 학급의 학생이 아닙니다.");
		}

		if (bookReport.getHomework() != homework) {
			throw new IllegalArgumentException("숙제로 제출한 독서록이 아닙니다.");
		}
		if (bookReport.getApproveTime() != null) {
			throw new IllegalArgumentException("승인이 된 독서록은 숙제에서 회수할 수 없습니다.");
		}

		bookReport.retrieveFromHomework();
		bookReportRepository.save(bookReport);
	}

	public void deleteBookReport(User user, Long bookReportId) {
		BookReport bookReport = bookReportRepository.findByBookReport(bookReportId, user.getId())
			.orElseThrow(() -> new NoSuchElementException("본인의 독서록이 없습니다."));

		if (bookReport.getApproveTime() != null) {
			throw new IllegalArgumentException("승인이 된 독서록은 지울 수 없습니다.");
		}

		bookReportRepository.delete(bookReport);
	}

	public BookReportStatisticsResponse getBookReportStatistics(User user, BookReportStatisticsRequest request) {
		long myCount = bookReportStatisticRepository.myBookReportsCount(user.getId(), request.grade());

		double averageCount = bookReportStatisticRepository.classAverageBookReportsCount(user.getId(), request.grade());

		long highestCount = bookReportStatisticRepository.classHighestBookReportCount(user.getId(), request.grade());

		return new BookReportStatisticsResponse((int)myCount, averageCount, (int)highestCount);
	}

	public List<BookReportMonthlyStatisticsDto> getMonthlyBookReportStatistics(User user,
		BookReportStatisticsRequest request) {
		return bookReportStatisticRepository.myMonthlyBookReportCount(user.getId(), request.grade());
	}

	public List<BookReportDailyStatisticsDto> getDailyBookReportStatistics(User user,
		BookReportStatisticsRequest request) {
		return bookReportStatisticRepository.myDailyBookReportCount(user.getId(), request.grade(), request.month());
	}

	public BookReportMyRankResponse getBookReportRank(User user) {
		List<BookReportMyRankDto> rankList = bookReportStatisticRepository.myBookReportRank(user.getId());
		int rank = 0;
		int myRank = 0;
		int idx = 0;
		int prevCount = -1;
		int readCount;
		for (BookReportMyRankDto rankDto : rankList) {
			idx++;
			readCount = rankDto.readCount();
			if (readCount != prevCount) {
				rank = idx;
				prevCount = readCount;
			}
			if (user.getId().equals(rankDto.userId())) {
				myRank = rank;
			}
		}

		int stampCount = awardRepository.getStampCount(user.getId());

		return new BookReportMyRankResponse(idx, stampCount, myRank);
	}

	public BookReportLatestAiFeedbackResponse getLatestAiFeedback(User user) {
		return new BookReportLatestAiFeedbackResponse(bookReportStatisticRepository.getLatestAiFeedback(user.getId()));
	}
}
