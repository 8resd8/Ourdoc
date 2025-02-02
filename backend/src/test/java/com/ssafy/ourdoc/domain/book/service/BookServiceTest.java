package com.ssafy.ourdoc.domain.book.service;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ssafy.ourdoc.domain.book.dto.BookRequest;
import com.ssafy.ourdoc.domain.book.entity.Book;
import com.ssafy.ourdoc.domain.book.repository.BookRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
class BookServiceTest {

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private BookService bookService;

	private List<Book> books = new ArrayList<>();

	@BeforeEach
	void setUp() {
		books.add(Book.builder().isbn("1234").title("홍길동전").author("허균").publisher("조선출판사").build());
		books.add(Book.builder().isbn("12345").title("홍길동전").author("허균").publisher("고전문학사").build());
		books.add(Book.builder().isbn("5678").title("콩쥐팥쥐").author("미상").publisher("고전문학사").build());
		books.add(Book.builder().isbn("0000").title("심청전").author("미상").publisher("고전문학사").build());
		bookRepository.saveAll(books);
		assertThat(bookRepository.findAll()).hasSize(4);
	}

	@Test
	@DisplayName("책 저장 테스트")
	void addBook() {
		Book book = Book.builder().isbn("9876").title("어린왕자").author("생텍쥐페리").publisher("출판사").build();
		bookService.registerBook(book);
		assertThat(bookRepository.findAll()).hasSize(5);
	}

	@Test
	@DisplayName("책 제목 조회 테스트")
	void searchBookByTitle() {
		BookRequest request = new BookRequest("홍길동전", "", "");
		List<Book> result = bookService.searchBook(request);
		assertThat(result.size()).isEqualTo(2);
		result.forEach(book -> {
			assertThat(book.getTitle()).contains("홍길동전");
		});
	}

	@Test
	@DisplayName("책 제목과 출판사 조회 테스트")
	void searchBookByTitleAndPublisher() {
		BookRequest request = new BookRequest("홍길동전", "", "고전문학사");
		List<Book> result = bookService.searchBook(request);
		assertThat(result.size()).isEqualTo(1);
		Book book = result.get(0);
		assertThat(book.getTitle()).isEqualTo("홍길동전");
		assertThat(book.getPublisher()).isEqualTo("고전문학사");
	}

	@Test
	@DisplayName("책 상세 조회 성공")
	void getBookDetailSuccess() {
		Book result = bookService.getBookDetail(books.get(0).getId());
		assertThat(result).isNotNull();
		assertThat(result.getTitle()).isEqualTo("홍길동전");
	}

	@Test
	@DisplayName("책 상세 조회 실패_책 없음")
	void getBookDetailFail() {
		Book result = bookService.getBookDetail(999L);
		assertThat(result).isNull();
	}
}
