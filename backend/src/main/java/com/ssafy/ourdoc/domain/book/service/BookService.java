package com.ssafy.ourdoc.domain.book.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ssafy.ourdoc.domain.book.dto.BookDetailResponse;
import com.ssafy.ourdoc.domain.book.dto.BookListResponse;
import com.ssafy.ourdoc.domain.book.dto.BookResponse;
import com.ssafy.ourdoc.domain.book.dto.BookSearchRequest;
import com.ssafy.ourdoc.domain.book.dto.most.BookMostDto;
import com.ssafy.ourdoc.domain.book.dto.most.BookMostResponse;
import com.ssafy.ourdoc.domain.book.entity.Book;
import com.ssafy.ourdoc.domain.book.repository.BookRepository;
import com.ssafy.ourdoc.domain.book.util.BookStatusMapper;
import com.ssafy.ourdoc.domain.user.entity.User;

import groovy.util.logging.Slf4j;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class BookService {
	private final BookRepository bookRepository;
	private final BookStatusMapper bookStatusMapper;

	public void registerBook(Book book) {
		if (bookRepository.findByIsbn(book.getIsbn()).isPresent()) {
			throw new IllegalArgumentException("이미 존재하는 ISBN입니다.");
		}
		bookRepository.save(book);
	}

	public void registerBookList(List<Book> books) {
		bookRepository.saveAll(books);
	}

	public BookListResponse searchBook(User user, BookSearchRequest request, Pageable pageable) {
		Page<Book> books = bookRepository.findBookPage(request.title(), request.author(), request.publisher(),
			pageable);
		List<BookResponse> bookResponse = books.stream()
			.map(book -> BookResponse.of(book, bookStatusMapper.mapBookStatus(book, user)))
			.toList();
		Page<BookResponse> bookResponsePage = new PageImpl<>(bookResponse, pageable, books.getTotalElements());
		return new BookListResponse(bookResponsePage);
	}

	public BookDetailResponse getBookDetail(User user, Long id) {
		Book book = findBookById(id);
		return BookDetailResponse.of(book, book.getDescription(), bookStatusMapper.mapBookStatus(book, user));
	}

	public Book findBookById(Long id) {
		return bookRepository.findById(id)
			.orElseThrow(() -> new NoSuchElementException("해당하는 ID의 도서가 없습니다."));
	}

	public BookMostResponse getBookMost(User user) {
		BookMostDto gradeMost = bookRepository.findBookGradeMost(user.getId());
		BookMostDto classMost = bookRepository.findBookClassMost(user.getId());
		return new BookMostResponse(gradeMost, classMost);
	}
}
