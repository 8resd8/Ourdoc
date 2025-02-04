package com.ssafy.ourdoc.domain.book.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ssafy.ourdoc.domain.book.dto.BookDetailResponse;
import com.ssafy.ourdoc.domain.book.dto.BookRequest;
import com.ssafy.ourdoc.domain.book.dto.BookResponse;
import com.ssafy.ourdoc.domain.book.entity.Book;
import com.ssafy.ourdoc.domain.book.repository.BookRepository;
import com.ssafy.ourdoc.global.integration.nationallibrary.service.NationalLibraryBookService;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

	@Mock
	private BookRepository bookRepository;

	@InjectMocks
	private BookService bookService;

	@Spy
	private NationalLibraryBookService nationalLibraryBookService;

	private Book book;

	@BeforeEach
	void setUp() throws Exception {
		book = Book.builder().isbn("1234").title("홍길동전").author("허균").publisher("조선출판사").build();
		setBookId(book, 1L);
	}

	@Test
	@DisplayName("책 저장 테스트")
	void addBookSuccess() {
		when(bookRepository.save(any(Book.class))).thenReturn(book);
		bookService.registerBook(book);
		verify(bookRepository, times(1)).save(any(Book.class));
	}

	@Test
	@DisplayName("책 저장 실패 테스트-중복 ISBN")
	void addBookFail() {
		Book duplicateIsbnBook = Book.builder()
			.isbn("1234")
			.title("중복 도서")
			.author("테스트 저자")
			.publisher("테스트 출판사")
			.build();
		when(bookRepository.findByIsbn(duplicateIsbnBook.getIsbn())).thenReturn(Optional.of(book));

		assertThatThrownBy(() -> bookService.registerBook(duplicateIsbnBook))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("이미 존재하는 ISBN입니다.");

		verify(bookRepository, times(1)).findByIsbn(book.getIsbn());
		verify(bookRepository, never()).save(book);
	}

	@Test
	@DisplayName("책 제목 조회 테스트")
	void searchBookByTitle() {
		BookRequest request = new BookRequest("홍길동전", "", "");
		List<Book> mockBooks = List.of(
			Book.builder().isbn("1234").title("홍길동전").author("허균").publisher("조선출판사").build(),
			Book.builder().isbn("12345").title("홍길동전").author("허균").publisher("고전문학사").build()
		);
		when(bookRepository.findBookList("홍길동전", "", "")).thenReturn(mockBooks);
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
		List<Book> mockBooks = List.of(
			Book.builder().isbn("12345").title("홍길동전").author("허균").publisher("고전문학사").build()
		);
		when(bookRepository.findBookList("홍길동전", "", "고전문학사")).thenReturn(mockBooks);
		List<BookResponse> result = bookService.searchBook(request);
		assertThat(result.size()).isEqualTo(1);
		BookResponse book = result.get(0);
		assertThat(book.title()).isEqualTo("홍길동전");
		assertThat(book.publisher()).isEqualTo("고전문학사");
	}

	@Test
	@DisplayName("책 상세 조회 성공")
	void getBookDetailSuccess() {
		when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
		BookDetailResponse result = bookService.getBookDetail(1L);
		assertThat(result).isNotNull();
		assertThat(result.title()).isEqualTo("홍길동전");
	}

	@Test
	@DisplayName("책 상세 조회 실패_책 없음")
	void getBookDetailFail() {
		assertThatThrownBy(() -> bookService.getBookDetail(999L)).isInstanceOf(NoSuchElementException.class)
			.hasMessage("해당하는 ID의 도서가 없습니다.");
	}

	private void setBookId(Book book, Long id) throws Exception {
		Field idField = Book.class.getDeclaredField("id");
		idField.setAccessible(true);
		idField.set(book, id);
	}
}
