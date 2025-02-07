package com.ssafy.ourdoc.domain.bookreport.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

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
import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.domain.user.student.entity.StudentClass;
import com.ssafy.ourdoc.domain.user.student.repository.StudentClassRepository;
import com.ssafy.ourdoc.global.common.enums.ApproveStatus;
import com.ssafy.ourdoc.global.common.enums.EvaluatorType;
import com.ssafy.ourdoc.global.common.enums.HomeworkStatus;
import com.ssafy.ourdoc.global.common.enums.OcrCheck;

@ExtendWith(MockitoExtension.class)
class BookReportStudentServiceTest {

	@InjectMocks
	private BookReportStudentService bookReportStudentService;

	@Mock
	private BookReportRepository bookReportRepository;

	@Mock
	private BookReportFeedbackRepository feedbackRepository;

	@Mock
	private StudentClassRepository studentClassRepository;

	@Mock
	private BookRepository bookRepository;

	private User mockUser;
	private StudentClass mockStudentClass;
	private Book mockBook;
	private BookReport mockBookReport;

	@BeforeEach
	void setUp() {

		// Mock User
		mockUser = User.builder()
			.name("테스트 학생")
			.build();

		// Mock StudentClass
		mockStudentClass = StudentClass.builder()
			.user(mockUser)
			.build();

		// Mock Book
		mockBook = Book.builder()
			.title("테스트 책")
			.build();

		// Mock BookReport
		mockBookReport = BookReport.builder()
			.studentClass(mockStudentClass)
			.book(mockBook)
			.beforeContent("독서 전 내용")
			.afterContent("독서 후 감상")
			.build();
	}

	@Test
	@DisplayName("독서록을 정상적으로 저장할 수 있다.")
	void testSaveBookReport() {
		// given
		BookReadLogRequest request = new BookReadLogRequest(1L, "독서 전 내용", "사용", OcrCheck.사용);

		when(studentClassRepository.findStudentClassByUserId(mockUser.getId())).thenReturn(
			Optional.of(mockStudentClass));
		when(bookRepository.findById(request.bookId())).thenReturn(Optional.of(mockBook));

		// when
		bookReportStudentService.saveBookReport(mockUser, request);

		// then
		verify(bookReportRepository, times(1)).save(any(BookReport.class));
	}

	@Test
	@DisplayName("독서록 피드백을 정상적으로 저장할 수 있다.")
	void testSaveBookReportFeedback() {
		// given
		FeedbackRequest request = new FeedbackRequest(1L, "수정된 감상문");

		when(bookReportRepository.findById(request.bookReportId())).thenReturn(Optional.of(mockBookReport));

		// when
		bookReportStudentService.saveBookReportFeedback(request);

		// then
		verify(bookReportRepository, times(1)).findById(request.bookReportId());
		verify(feedbackRepository, times(1)).save(any(BookReportFeedBack.class));
		assertThat(mockBookReport.getAfterContent()).isEqualTo("수정된 감상문");
	}

	@Test
	@DisplayName("특정 사용자의 학년별 독서록을 조회할 수 있다.")
	void testGetBookReports() {
		// given
		when(bookReportRepository.findByUserIdAndGrade(mockUser.getId(), 3))
			.thenReturn(List.of(mockBookReport));

		// when
		BookReportListResponse response = bookReportStudentService.getBookReports(mockUser, 3);

		// then
		assertThat(response).isNotNull();
		assertThat(response.bookReports()).hasSize(1);
		assertThat(response.bookReports().get(0).content()).isEqualTo("독서 후 감상");
		assertThat(response.bookReports().get(0).approveStatus()).isEqualTo(ApproveStatus.없음);
		assertThat(response.bookReports().get(0).homework()).isEqualTo(HomeworkStatus.미제출);
	}
}
