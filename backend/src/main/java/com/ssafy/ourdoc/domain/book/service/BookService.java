package com.ssafy.ourdoc.domain.book.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ssafy.ourdoc.domain.book.dto.BookDetailResponse;
import com.ssafy.ourdoc.domain.book.dto.BookResponse;
import com.ssafy.ourdoc.domain.book.dto.BookSearchRequest;
import com.ssafy.ourdoc.domain.book.entity.Book;
import com.ssafy.ourdoc.domain.book.repository.BookRepository;

import groovy.util.logging.Slf4j;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class BookService {
	private final BookRepository bookRepository;

	public void registerBook(Book book) {
		if (bookRepository.findByIsbn(book.getIsbn()).isPresent()) {
			throw new IllegalArgumentException("이미 존재하는 ISBN입니다.");
		}
		bookRepository.save(book);
	}

	public void registerBookList(List<Book> books) {
		bookRepository.saveAll(books);
	}

	public List<BookResponse> searchBook(BookSearchRequest request) {
		List<Book> books = bookRepository.findBookList(request.title(), request.author(), request.publisher());
		return books.stream()
			.map(BookResponse::of)
			.collect(Collectors.toList());
	}

	public BookDetailResponse getBookDetail(Long id) {
		Book book = bookRepository.findById(id)
			.orElseThrow(() -> new NoSuchElementException("해당하는 ID의 도서가 없습니다."));
		return BookDetailResponse.of(book, book.getDescription());
	}
}
