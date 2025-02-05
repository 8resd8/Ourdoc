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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ssafy.ourdoc.domain.book.dto.BookFavoriteRequest;
import com.ssafy.ourdoc.domain.book.dto.BookResponse;
import com.ssafy.ourdoc.domain.book.entity.Book;
import com.ssafy.ourdoc.domain.book.entity.BookFavorite;
import com.ssafy.ourdoc.domain.book.repository.BookFavoriteRepository;
import com.ssafy.ourdoc.domain.book.repository.BookRepository;
import com.ssafy.ourdoc.domain.user.entity.User;

@ExtendWith(MockitoExtension.class)
public class BookFavoriteServiceTest {

	@Mock
	private BookRepository bookRepository;

	@Mock
	private BookFavoriteRepository bookFavoriteRepository;

	@InjectMocks
	private BookFavoriteService bookFavoriteService;

	private Book book;

	@BeforeEach
	void setUp() throws Exception {
		book = Book.builder().isbn("1234").title("홍길동전").author("허균").publisher("조선출판사").build();
		setBookId(book, 1L);
	}

	@Test
	@DisplayName("책 관심 도서 등록 성공")
	void addBookFavoriteSuccess() {
		User user = Mockito.mock(User.class);

		when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
		when(bookFavoriteRepository.existsByBookAndUser(any(), any())).thenReturn(false);

		bookFavoriteService.addBookFavorite(new BookFavoriteRequest(1L), user);

		verify(bookFavoriteRepository, times(1)).save(any());
	}

	@Test
	@DisplayName("책 관심 도서 등록 실패-도서 없음")
	void addBookFavoriteFailSinceNoBook() {
		User user = Mockito.mock(User.class);

		assertThatThrownBy(() -> bookFavoriteService.addBookFavorite(new BookFavoriteRequest(999L), user)).isInstanceOf(
			NoSuchElementException.class).hasMessage("해당하는 ID의 도서가 없습니다.");
	}

	@Test
	@DisplayName("책 관심 도서 등록 실패-중복 관심 등록")
	void addBookFavoriteFailSinceDuplicate() {
		User user = Mockito.mock(User.class);

		when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
		when(bookFavoriteRepository.existsByBookAndUser(any(), any())).thenReturn(true);

		assertThatThrownBy(() -> bookFavoriteService.addBookFavorite(new BookFavoriteRequest(1L), user)).isInstanceOf(
			IllegalArgumentException.class).hasMessage("이미 관심 도서로 등록했습니다.");
	}

	@Test
	@DisplayName("책 관심 도서 삭제 실패-도서 없음")
	void deleteBookFavoriteFailSinceNoBook() {
		User user = Mockito.mock(User.class);

		when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());

		assertThatThrownBy(
			() -> bookFavoriteService.deleteBookFavorite(new BookFavoriteRequest(999L), user)).isInstanceOf(
			NoSuchElementException.class).hasMessage("해당하는 ID의 도서가 없습니다.");
	}

	@Test
	@DisplayName("책 관심 도서 삭제 실패-관심 도서 아님")
	void deleteBookFavoriteFailSinceDuplicate() {
		User user = Mockito.mock(User.class);

		when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
		when(bookFavoriteRepository.findByBookAndUser(any(), any())).thenReturn(Optional.empty());

		assertThatThrownBy(
			() -> bookFavoriteService.deleteBookFavorite(new BookFavoriteRequest(1L), user)).isInstanceOf(
			IllegalArgumentException.class).hasMessage("관심 도서로 등록한 도서가 아닙니다.");
	}

	@Test
	@DisplayName("관심도서 목록 조회 성공")
	void getBookFavoriteSuccess() {
		User user = Mockito.mock(User.class);

		List<BookFavorite> mockBookFavorite = List.of(
			new BookFavorite(book, user)
		);

		when(bookFavoriteRepository.findByUser(user)).thenReturn(mockBookFavorite);

		List<BookResponse> bookFavorites = bookFavoriteService.getBookFavorites(user);

		verify(bookFavoriteRepository, times(1)).findByUser(user);
		assertThat(bookFavorites).isEqualTo(List.of(BookResponse.of(book)));

	}

	@Test
	@DisplayName("관심도서 목록 빈 경우 성공")
	void getEmptyBookFavoriteSuccess() {
		User user = Mockito.mock(User.class);

		List<BookFavorite> mockBookFavorite = new ArrayList<>();

		when(bookFavoriteRepository.findByUser(user)).thenReturn(mockBookFavorite);

		List<BookResponse> bookFavorites = bookFavoriteService.getBookFavorites(user);

		verify(bookFavoriteRepository, times(1)).findByUser(user);
		assertTrue(bookFavorites.isEmpty());
	}

	private void setBookId(Book book, Long id) throws Exception {
		Field idField = Book.class.getDeclaredField("id");
		idField.setAccessible(true);
		idField.set(book, id);
	}
}
