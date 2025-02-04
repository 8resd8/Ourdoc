package com.ssafy.ourdoc.domain.book.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.ssafy.ourdoc.domain.book.dto.BookFavoriteRequest;
import com.ssafy.ourdoc.domain.book.entity.Book;
import com.ssafy.ourdoc.domain.book.entity.BookFavorite;
import com.ssafy.ourdoc.domain.book.repository.BookFavoriteRepository;
import com.ssafy.ourdoc.domain.book.repository.BookRepository;
import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.global.annotation.Login;

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

	public void addBookFavorite(BookFavoriteRequest request, @Login User user) {
		Book book = bookRepository.findById(request.bookId())
			.orElseThrow(() -> new NoSuchElementException("해당하는 ID의 도서가 없습니다."));
		if (bookFavoriteRepository.existsByBookAndUser(book, user)) {
			throw new IllegalArgumentException("이미 관심 도서로 등록했습니다.");
		}
		BookFavorite bookFavorite = BookFavorite.builder().book(book).user(user).build();
		bookFavoriteRepository.save(bookFavorite);
	}

	public void deleteBookFavorite(BookFavoriteRequest request, @Login User user) {
		Book book = bookRepository.findById(request.bookId())
			.orElseThrow(() -> new NoSuchElementException("해당하는 ID의 도서가 없습니다."));
		BookFavorite bookFavorite = bookFavoriteRepository.findByBookAndUser(book, user)
			.orElseThrow(() -> new IllegalArgumentException("관심 도서로 등록한 도서가 아닙니다."));
		bookFavoriteRepository.delete(bookFavorite);
	}

	public List<BookFavorite> getBookFavorites(@Login User user) {
		return bookFavoriteRepository.findByUser(user);
	}
}
