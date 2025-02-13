package com.ssafy.ourdoc.domain.book.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ssafy.ourdoc.domain.book.dto.BookListResponse;
import com.ssafy.ourdoc.domain.book.dto.BookRequest;
import com.ssafy.ourdoc.domain.book.dto.BookResponse;
import com.ssafy.ourdoc.domain.book.dto.BookSearchRequest;
import com.ssafy.ourdoc.domain.book.entity.Book;
import com.ssafy.ourdoc.domain.book.entity.BookFavorite;
import com.ssafy.ourdoc.domain.book.repository.BookFavoriteRepository;
import com.ssafy.ourdoc.domain.book.repository.BookRepository;
import com.ssafy.ourdoc.domain.user.entity.User;

import groovy.util.logging.Slf4j;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class BookFavoriteService {
	private final BookRepository bookRepository;
	private final BookFavoriteRepository bookFavoriteRepository;
	private final BookService bookService;

	public void addBookFavorite(BookRequest request, User user) {
		Book book = bookService.findBookById(request.bookId());
		if (bookFavoriteRepository.existsByBookAndUser(book, user)) {
			throw new IllegalArgumentException("이미 관심 도서로 등록했습니다.");
		}
		BookFavorite bookFavorite = BookFavorite.builder().book(book).user(user).build();
		bookFavoriteRepository.save(bookFavorite);
	}

	public void deleteBookFavorite(BookRequest request, User user) {
		Book book = bookService.findBookById(request.bookId());
		BookFavorite bookFavorite = bookFavoriteRepository.findByBookAndUser(book, user)
			.orElseThrow(() -> new IllegalArgumentException("관심 도서로 등록한 도서가 아닙니다."));
		bookFavoriteRepository.delete(bookFavorite);
	}

	public BookListResponse getBookFavorites(BookSearchRequest request, User user, Pageable pageable) {
		List<Book> searchedBooks = bookRepository.findBookList(request.title(), request.author(), request.publisher());
		Page<BookFavorite> bookFavorites = bookFavoriteRepository.findByUserAndBookIn(user, searchedBooks, pageable);
		List<Book> books = bookFavorites.stream().map(BookFavorite::getBook).toList();
		List<BookResponse> bookResponse = books.stream().map(BookResponse::of).toList();
		Page<BookResponse> bookResponsePage = new PageImpl<>(bookResponse, pageable, books.size());
		return new BookListResponse(bookResponsePage);
	}
}
