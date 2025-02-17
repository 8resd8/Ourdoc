package com.ssafy.ourdoc.domain.book.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.ourdoc.domain.book.dto.BookListResponse;
import com.ssafy.ourdoc.domain.book.dto.BookSearchRequest;
import com.ssafy.ourdoc.domain.book.dto.BookStudentDetailResponse;
import com.ssafy.ourdoc.domain.book.dto.BookTeacherDetailResponse;
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
	public ResponseEntity<BookListResponse> getBooks(
		@Login User user,
		@ModelAttribute BookSearchRequest request,
		@PageableDefault(page = 0, size = 10) Pageable pageable) {
		BookListResponse books = bookService.searchBook(user, request, pageable);
		return ResponseEntity.ok(books);
	}

	@GetMapping("/teachers/{bookId}")
	public ResponseEntity<BookTeacherDetailResponse> getBookTeacher(
		@PathVariable("bookId") Long bookId,
		@Login User user,
		@PageableDefault(page = 0, size = 10) Pageable pageable) {
		BookTeacherDetailResponse book = bookService.getBookDetailTeacher(user, bookId, pageable);
		return ResponseEntity.ok(book);
	}

	@GetMapping("/students/{bookId}")
	public ResponseEntity<BookStudentDetailResponse> getBookStudent(
		@PathVariable("bookId") Long bookId,
		@Login User user,
		@PageableDefault(page = 0, size = 10) Pageable pageable) {
		BookStudentDetailResponse book = bookService.getBookDetailStudent(user, bookId, pageable);
		return ResponseEntity.ok(book);
	}

	@GetMapping("/teachers/mostread")
	public ResponseEntity<BookMostResponse> getMost(@Login User user) {
		BookMostResponse books = bookService.getBookMost(user);
		return ResponseEntity.ok(books);
	}

}
