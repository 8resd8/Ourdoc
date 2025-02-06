package com.ssafy.ourdoc.domain.book.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ssafy.ourdoc.data.entity.BookSample;
import com.ssafy.ourdoc.data.entity.ClassRoomSample;
import com.ssafy.ourdoc.data.entity.SchoolSample;
import com.ssafy.ourdoc.data.entity.TeacherClassSample;
import com.ssafy.ourdoc.data.entity.UserSample;
import com.ssafy.ourdoc.domain.book.dto.BookRecommendRequest;
import com.ssafy.ourdoc.domain.book.dto.BookResponse;
import com.ssafy.ourdoc.domain.book.entity.Book;
import com.ssafy.ourdoc.domain.book.entity.BookRecommend;
import com.ssafy.ourdoc.domain.book.repository.BookRecommendRepository;
import com.ssafy.ourdoc.domain.book.repository.BookRepository;
import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.domain.user.teacher.entity.TeacherClass;
import com.ssafy.ourdoc.domain.user.teacher.repository.TeacherClassRepository;
import com.ssafy.ourdoc.global.common.enums.Active;
import com.ssafy.ourdoc.global.common.enums.UserType;
import com.ssafy.ourdoc.global.exception.ForbiddenException;

@ExtendWith(MockitoExtension.class)
public class BookRecommendServiceTest {

	@Mock
	private BookRepository bookRepository;

	@Mock
	private BookRecommendRepository bookRecommendRepository;

	@Mock
	private TeacherClassRepository teacherClassRepository;

	@InjectMocks
	private BookRecommendService bookRecommendService;

	private Book book;

	@BeforeEach
	void setUp() throws Exception {
		book = Book.builder().isbn("1234").title("홍길동전").author("허균").publisher("조선출판사").build();
		setBookId(book, 1L);
	}

	@Test
	@DisplayName("책 추천 도서 등록 성공")
	void addBookRecommendSuccess() {
		User user = UserSample.user(UserType.교사);

		when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));

		when(teacherClassRepository.findByUserIdAndActive(any(), any())).thenReturn(TeacherClass.builder().build());

		when(bookRecommendRepository.existsByBookAndUserAndClassRoom(any(), any(), any())).thenReturn(false);

		bookRecommendService.addBookRecommend(new BookRecommendRequest(1L), user);

		verify(bookRecommendRepository, times(1)).save(any());
	}

	@Test
	@DisplayName("책 추천 도서 등록 실패-권한 없는 학생")
	void addBookRecommendFailSinceStudent() {
		User user = UserSample.user(UserType.학생);

		assertThatThrownBy(
			() -> bookRecommendService.addBookRecommend(new BookRecommendRequest(1L), user)).isInstanceOf(
			ForbiddenException.class).hasMessage("추천도서를 생성할 권한이 없습니다.");
	}

	@Test
	@DisplayName("책 추천 도서 등록 실패-도서 없음")
	void addBookRecommendFailSinceNoBook() {
		User user = UserSample.user(UserType.교사);

		assertThatThrownBy(
			() -> bookRecommendService.addBookRecommend(new BookRecommendRequest(999L), user)).isInstanceOf(
			NoSuchElementException.class).hasMessage("해당하는 ID의 도서가 없습니다.");
	}

	@Test
	@DisplayName("책 추천 도서 등록 실패-중복 관심 등록")
	void addBookRecommendFailSinceDuplicate() {
		User user = UserSample.user(UserType.교사);

		when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
		when(teacherClassRepository.findByUserIdAndActive(any(), any())).thenReturn(TeacherClass.builder().build());
		when(bookRecommendRepository.existsByBookAndUserAndClassRoom(any(), any(), any())).thenReturn(true);

		assertThatThrownBy(
			() -> bookRecommendService.addBookRecommend(new BookRecommendRequest(1L), user)).isInstanceOf(
			IllegalArgumentException.class).hasMessage("이미 추천 도서로 등록했습니다.");
	}

	@Test
	@DisplayName("책 추천 도서 삭제 실패-도서 없음")
	void deleteBookRecommendFailSinceNoBook() {
		User user = UserSample.user(UserType.교사);

		when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());

		assertThatThrownBy(
			() -> bookRecommendService.deleteBookRecommend(new BookRecommendRequest(999L), user)).isInstanceOf(
			NoSuchElementException.class).hasMessage("해당하는 ID의 도서가 없습니다.");
	}

	@Test
	@DisplayName("책 추천 도서 삭제 실패-추천 도서 아님")
	void deleteBookRecommendFailSinceDuplicate() {
		User user = UserSample.user(UserType.교사);

		when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
		when(teacherClassRepository.findByUserIdAndActive(any(), any())).thenReturn(TeacherClass.builder().build());
		when(bookRecommendRepository.findByBookAndUserAndClassRoom(any(), any(), any())).thenReturn(Optional.empty());

		assertThatThrownBy(
			() -> bookRecommendService.deleteBookRecommend(new BookRecommendRequest(1L), user)).isInstanceOf(
			IllegalArgumentException.class).hasMessage("추천 도서로 등록한 도서가 아닙니다.");
	}

	@Test
	@DisplayName("추천도서 목록 조회 성공 - 학교와 학년 필터링")
	void getBookRecommendBySchoolAndGradeSuccess() {
		User user = UserSample.user(UserType.교사);

		List<BookRecommend> mockBookRecommend = List.of(
			new BookRecommend(BookSample.book("1111", "책제목1", "책저자1"), user,
				TeacherClassSample.teacherClass(user,
					ClassRoomSample.classRoom(SchoolSample.school("학교1"), 1, 1, 2025),
					Active.활성).getClassRoom()),
			new BookRecommend(BookSample.book("2222", "책제목2", "책저자2"), user,
				TeacherClassSample.teacherClass(user,
					ClassRoomSample.classRoom(SchoolSample.school("학교1"), 2, 1, 2025),
					Active.활성).getClassRoom()),
			new BookRecommend(BookSample.book("3333", "책제목3", "책저자3"), user,
				TeacherClassSample.teacherClass(user,
					ClassRoomSample.classRoom(SchoolSample.school("학교1"), 2, 1, 2023),
					Active.비활성).getClassRoom()),
			new BookRecommend(BookSample.book("4444", "책제목4", "책저자4"), user,
				TeacherClassSample.teacherClass(user,
					ClassRoomSample.classRoom(SchoolSample.school("학교2"), 1, 1, 2022),
					Active.비활성).getClassRoom())
		);

		when(bookRecommendRepository.findRecommendBookList(user)).thenReturn(mockBookRecommend);

		List<BookRecommend> filteredBookRecommend = mockBookRecommend.stream()
			.filter(bookRecommend -> bookRecommend.getClassRoom().getSchool().getSchoolName().equals("학교1")
				&& bookRecommend.getClassRoom().getGrade() == 1)
			.toList();

		List<BookResponse> expectedBookResponse = filteredBookRecommend.stream()
			.map(bookRecommend -> BookResponse.of(bookRecommend.getBook()))
			.collect(Collectors.toList());

		List<BookResponse> bookRecommends = bookRecommendService.getBookRecommends(user);

		assertThat(bookRecommends).isEqualTo(expectedBookResponse);
	}

	@Test
	@DisplayName("추천도서 목록 빈 경우 성공")
	void getEmptyBookRecommendSuccess() {
		User user = UserSample.user(UserType.교사);

		List<BookRecommend> mockBookRecommend = new ArrayList<>();

		when(bookRecommendRepository.findRecommendBookList(user)).thenReturn(mockBookRecommend);

		List<BookResponse> bookFavorites = bookRecommendService.getBookRecommends(user);

		verify(bookRecommendRepository, times(1)).findRecommendBookList(user);
		assertTrue(bookFavorites.isEmpty());
	}

	private void setBookId(Book book, Long id) throws Exception {
		Field idField = Book.class.getDeclaredField("id");
		idField.setAccessible(true);
		idField.set(book, id);
	}
}
