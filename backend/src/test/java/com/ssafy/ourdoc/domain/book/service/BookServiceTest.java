package com.ssafy.ourdoc.domain.book.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ssafy.ourdoc.data.entity.UserSample;
import com.ssafy.ourdoc.domain.book.dto.BookDetailResponse;
import com.ssafy.ourdoc.domain.book.dto.BookFavoriteRequest;
import com.ssafy.ourdoc.domain.book.dto.BookRequest;
import com.ssafy.ourdoc.domain.book.dto.BookResponse;
import com.ssafy.ourdoc.domain.book.entity.Book;
import com.ssafy.ourdoc.domain.book.exception.BookFavoriteFailException;
import com.ssafy.ourdoc.domain.book.repository.BookFavoriteRepository;
import com.ssafy.ourdoc.domain.book.repository.BookRepository;
import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.global.common.enums.UserType;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

	@Mock
	private BookRepository bookRepository;

	@Mock
	private BookFavoriteRepository bookFavoriteRepository;

	@InjectMocks
	private BookService bookService;

	private List<Book> books = new ArrayList<>();

	private BookRepository bookRepositorySpy = spy(bookRepository);
	private BookFavoriteRepository bookFavoriteRepositorySpy = spy(bookFavoriteRepository);

	@BeforeEach
	void setUp() {
		books.add(Book.builder().isbn("1234").title("홍길동전").author("허균").publisher("조선출판사").build());
		books.add(Book.builder().isbn("12345").title("홍길동전").author("허균").publisher("고전문학사").build());
		books.add(Book.builder().isbn("5678").title("콩쥐팥쥐").author("미상").publisher("고전문학사").build());
		books.add(Book.builder().isbn("0000").title("심청전").author("미상").publisher("고전문학사").build());
		bookRepositorySpy.saveAll(books);
		assertThat(bookRepositorySpy.findAll()).hasSize(4);
	}

	@Test
	@DisplayName("책 저장 테스트")
	void addBook() {
		Book book = Book.builder().isbn("9876").title("어린왕자").author("생텍쥐페리").publisher("출판사").build();
		bookService.registerBook(book);
		assertThat(bookRepositorySpy.findAll()).hasSize(5);
	}

	@Test
	@DisplayName("책 제목 조회 테스트")
	void searchBookByTitle() {
		BookRequest request = new BookRequest("홍길동전", "", "");
		List<BookResponse> result = bookService.searchBook(request);
		assertThat(result.size()).isEqualTo(2);
		result.forEach(book -> {
			assertThat(book.title()).contains("홍길동전");
		});
	}

	@Test
	@DisplayName("책 제목과 출판사 조회 테스트")
	void searchBookByTitleAndPublisher() {
		BookRequest request = new BookRequest("홍길동전", "", "고전문학사");
		List<BookResponse> result = bookService.searchBook(request);
		assertThat(result.size()).isEqualTo(1);
		BookResponse book = result.get(0);
		assertThat(book.title()).isEqualTo("홍길동전");
		assertThat(book.publisher()).isEqualTo("고전문학사");
	}

	@Test
	@DisplayName("책 상세 조회 성공")
	void getBookDetailSuccess() {
		BookDetailResponse result = bookService.getBookDetail(books.get(0).getId());
		assertThat(result).isNotNull();
		assertThat(result.title()).isEqualTo("홍길동전");
	}

	@Test
	@DisplayName("책 상세 조회 실패_책 없음")
	void getBookDetailFail() {
		assertThatThrownBy(() -> bookService.getBookDetail(999L)).isInstanceOf(NoSuchElementException.class)
			.hasMessage("해당하는 ID의 도서가 없습니다.");
	}

	@Test
	@DisplayName("책 관심 도서 등록 성공")
	void addBookFavorite() {
		User sampleUser = UserSample.user(UserType.학생);
		User userSpy = spy(sampleUser);
		when(userSpy.getId()).thenReturn(1L);
		BookFavoriteRequest request = new BookFavoriteRequest(1L);
		assertThat(bookService.addBookFavorite(request, sampleUser)).isEqualTo(true);
	}

	@Test
	@DisplayName("책 관심 도서 등록 실패-도서 없음")
	void addBookFavoriteFailSinceNoBook() {
		User sampleUser = UserSample.user(UserType.학생);
		User userSpy = spy(sampleUser);
		when(userSpy.getId()).thenReturn(1L);
		BookFavoriteRequest request = new BookFavoriteRequest(999L);
		assertThatThrownBy(() -> bookService.addBookFavorite(request, sampleUser)).isInstanceOf(
			NoSuchElementException.class).hasMessage("해당하는 ID의 도서가 없습니다.");
	}

	@Test
	@DisplayName("책 관심 도서 등록 실패-중복 관심 등록")
	void addBookFavoriteFailSinceDuplicate() {
		User sampleUser = UserSample.user(UserType.학생);
		User userSpy = spy(sampleUser);
		when(userSpy.getId()).thenReturn(1L);
		bookService.addBookFavorite(new BookFavoriteRequest(1L), sampleUser);
		BookFavoriteRequest request = new BookFavoriteRequest(1L);
		bookService.addBookFavorite(request, sampleUser);
		assertThatThrownBy(() -> bookService.addBookFavorite(request, sampleUser)).isInstanceOf(
			BookFavoriteFailException.class).hasMessage("이미 관심 도서로 등록했습니다.");
	}

	@Test
	@DisplayName("책 관심 도서 등록 실패-도서 없음")
	void deleteBookFavoriteFailSinceNoBook() {
		User sampleUser = UserSample.user(UserType.학생);
		BookFavoriteRequest request = new BookFavoriteRequest(999L);
		assertThatThrownBy(() -> bookService.deleteBookFavorite(request, sampleUser)).isInstanceOf(
			NoSuchElementException.class).hasMessage("해당하는 ID의 도서가 없습니다.");
	}

	@Test
	@DisplayName("책 관심 도서 삭제 실패-관심 도서 아님")
	void deleteBookFavoriteFailSinceDuplicate() {
		User sampleUser = UserSample.user(UserType.학생);
		bookService.addBookFavorite(new BookFavoriteRequest(1L), sampleUser);
		BookFavoriteRequest request = new BookFavoriteRequest(1L);
		bookService.addBookFavorite(request, sampleUser);
		assertThatThrownBy(() -> bookService.deleteBookFavorite(request, sampleUser)).isInstanceOf(
			BookFavoriteFailException.class).hasMessage("관심 도서로 등록한 도서가 아닙니다.");
	}
}
