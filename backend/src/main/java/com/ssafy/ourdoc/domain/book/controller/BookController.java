package com.ssafy.ourdoc.domain.book.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.ourdoc.domain.book.dto.BookDetailResponse;
import com.ssafy.ourdoc.domain.book.dto.BookRequest;
import com.ssafy.ourdoc.domain.book.dto.BookResponse;
import com.ssafy.ourdoc.domain.book.dto.HomeworkRequest;
import com.ssafy.ourdoc.domain.book.service.BookService;
import com.ssafy.ourdoc.domain.book.service.HomeworkService;
import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.global.annotation.Login;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {

	private final BookService bookService;
	private final HomeworkService homeworkService;

	@GetMapping
	public ResponseEntity<List<BookResponse>> getBooks(@RequestBody BookRequest request) {
		List<BookResponse> books = bookService.searchBook(request);
		return ResponseEntity.ok(books);
	}

	@GetMapping("/{bookId}")
	public ResponseEntity<BookDetailResponse> getBook(@PathVariable("bookId") Long bookId) {
		BookDetailResponse book = bookService.getBookDetail(bookId);
		return ResponseEntity.ok(book);
	}

	@PostMapping("/teachers/homework")
	public void addHomework(@RequestBody HomeworkRequest request, @Login User user) {
		homeworkService.addHomework(request, user);
	}

	@DeleteMapping("/teachers/homework")
	public void deleteHomework(@RequestBody HomeworkRequest request, @Login User user) {
		homeworkService.deleteHomework(request, user);
	}
}
