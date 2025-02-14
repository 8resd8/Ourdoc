package com.ssafy.ourdoc.domain.book.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.ourdoc.domain.book.dto.BookDetailResponse;
import com.ssafy.ourdoc.domain.book.dto.BookListResponse;
import com.ssafy.ourdoc.domain.book.dto.BookSearchRequest;
import com.ssafy.ourdoc.domain.book.dto.most.BookMostResponse;
import com.ssafy.ourdoc.domain.book.service.BookService;
import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.global.annotation.Login;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {

	private final BookService bookService;

	@GetMapping
	public ResponseEntity<BookListResponse> getBooks(@ModelAttribute BookSearchRequest request,
		@PageableDefault(page = 0, size = 10) Pageable pageable) {
		BookListResponse books = bookService.searchBook(request, pageable);
		return ResponseEntity.ok(books);
	}

	@GetMapping("/{bookId}")
	public ResponseEntity<BookDetailResponse> getBook(@PathVariable("bookId") Long bookId) {
		BookDetailResponse book = bookService.getBookDetail(bookId);
		return ResponseEntity.ok(book);
	}

	@GetMapping("/teachers/mostread")
	public ResponseEntity<BookMostResponse> getMost(@Login User user) {
		BookMostResponse books = bookService.getBookMost(user);
		return ResponseEntity.ok(books);
	}

}
