package com.ssafy.ourdoc.domain.bookreport.service;

import static com.ssafy.ourdoc.global.common.enums.NotificationType.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

import com.ssafy.ourdoc.domain.book.entity.Book;
import com.ssafy.ourdoc.domain.book.repository.BookRepository;
import com.ssafy.ourdoc.domain.bookreport.dto.BookReadLogRequest;
import com.ssafy.ourdoc.domain.bookreport.dto.BookReportSaveResponse;
import com.ssafy.ourdoc.domain.bookreport.dto.FeedbackRequest;
import com.ssafy.ourdoc.domain.bookreport.entity.BookReport;
import com.ssafy.ourdoc.domain.bookreport.entity.BookReportFeedBack;
import com.ssafy.ourdoc.domain.bookreport.repository.BookReportFeedbackRepository;
import com.ssafy.ourdoc.domain.bookreport.repository.BookReportRepository;
import com.ssafy.ourdoc.domain.notification.service.NotificationService;
import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.domain.user.student.entity.StudentClass;
import com.ssafy.ourdoc.domain.user.student.repository.StudentClassRepository;
import com.ssafy.ourdoc.global.common.enums.Active;
import com.ssafy.ourdoc.global.common.enums.OcrCheck;

@ExtendWith(MockitoExtension.class)
class BookReportStudentServiceTest {

	@InjectMocks
	private BookReportStudentService bookReportStudentService;

	@InjectMocks
	private BookReportService bookReportService;

	@Mock
	private BookReportRepository bookReportRepository;

	@Mock
	private BookReportFeedbackRepository feedbackRepository;

	@Mock
	private StudentClassRepository studentClassRepository;
	@Mock
	private NotificationService notificationService;
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
	@Disabled
	@DisplayName("독서록을 정상적으로 저장할 수 있다.")
	void testSaveBookReport() {
		// given
		BookReadLogRequest request = new BookReadLogRequest(1L, null, "독서 전 내용", "사용", OcrCheck.사용);

		when(studentClassRepository.findByUserIdAndActive(mockUser.getId(), Active.활성))
			.thenReturn(Optional.of(mockStudentClass));
		when(bookRepository.findById(request.bookId()))
			.thenReturn(Optional.of(mockBook));

		// Mock BookReport 객체 생성
		BookReport mockBookReport = BookReport.builder()
			.studentClass(mockStudentClass)
			.book(mockBook)
			.beforeContent(request.beforeContent())
			.homework(null)
			.ocrCheck(request.ocrCheck())
			.imagePath(request.imageUrl())
			.build();

		// save() 메서드 호출 시 ID를 포함한 BookReport 객체 반환 설정
		ReflectionTestUtils.setField(mockBookReport, "id", 1L); // 테스트 환경에서 필드 수동 설정
		when(bookReportRepository.save(any(BookReport.class))).thenReturn(mockBookReport);

		doNothing().when(notificationService).sendNotifyStudentFromTeacher(any(User.class), any());

		// when
		BookReportSaveResponse response = bookReportStudentService.saveBookReport(mockUser, request);

		// then
		verify(bookReportRepository, times(1)).save(any(BookReport.class));
		verify(notificationService, times(1)).sendNotifyStudentFromTeacher(mockUser, 독서록);

		// 응답 값 검증
		assertNotNull(response);
		assertEquals(1L, response.bookReportId());
	}

	@Test
	@DisplayName("독서록 피드백을 정상적으로 저장할 수 있다.")
	void testSaveBookReportFeedback() {
		// given
		FeedbackRequest request = new FeedbackRequest(1L, "피드백 감상문", "맞춤법");

		when(bookReportRepository.findById(request.bookReportId())).thenReturn(Optional.of(mockBookReport));

		// when
		bookReportService.saveBookReportFeedback(request);

		// then
		verify(bookReportRepository, times(1)).findById(request.bookReportId());
		verify(feedbackRepository, times(1)).save(any(BookReportFeedBack.class));
		assertThat(mockBookReport.getAfterContent()).isEqualTo("맞춤법");
	}

	@Test
	@Disabled
	@DisplayName("특정 사용자의 학년별 독서록을 페이지네이션으로 조회할 수 있다.")
	void testGetBookReports() {
		// given
		Pageable pageable = PageRequest.of(0, 10); // 첫 번째 페이지, 페이지 크기 10
		List<BookReport> mockBookReports = List.of(mockBookReport);
		Page<BookReport> mockPage = new PageImpl<>(mockBookReports, pageable, mockBookReports.size());

		when(bookReportRepository.findByUserIdAndGrade(mockUser.getId(), 3, pageable))
			.thenReturn(mockPage);

		// when
		// BookReportListResponse response = bookReportStudentService.getBookReports(mockUser, 3, pageable);

		// then
		// assertThat(response).isNotNull();
		// assertThat(response.bookReports().getContent()).hasSize(1); // Page의 content 검증
		// assertThat(response.bookReports().getContent().get(0).content()).isEqualTo("독서 후 감상");
		// assertThat(response.bookReports().getContent().get(0).bookReportApproveStatus()).isEqualTo(ApproveStatus.없음);
		// assertThat(response.bookReports().getContent().get(0).homework()).isEqualTo(HomeworkStatus.미제출);
		// assertThat(response.bookReports().getTotalElements()).isEqualTo(1); // 전체 데이터 개수 검증
		// assertThat(response.bookReports().getTotalPages()).isEqualTo(1);   // 총 페이지 수 검증
	}

}
