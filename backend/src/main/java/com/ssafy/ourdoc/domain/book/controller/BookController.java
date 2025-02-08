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
import com.ssafy.ourdoc.domain.book.dto.BookFavoriteRequest;
import com.ssafy.ourdoc.domain.book.dto.BookRecommendRequest;
import com.ssafy.ourdoc.domain.book.dto.BookRecommendResponseStudent;
import com.ssafy.ourdoc.domain.book.dto.BookRecommendResponseTeacher;
import com.ssafy.ourdoc.domain.book.dto.BookRequest;
import com.ssafy.ourdoc.domain.book.dto.BookResponse;
import com.ssafy.ourdoc.domain.book.service.BookFavoriteService;
import com.ssafy.ourdoc.domain.book.service.BookRecommendService;
import com.ssafy.ourdoc.domain.book.service.BookService;
import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.global.annotation.Login;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {

	private final BookService bookService;
	private final BookFavoriteService bookFavoriteService;
	private final BookRecommendService bookRecommendService;

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

	@PostMapping("/favorite")
	public void addFavorite(@RequestBody BookFavoriteRequest request, @Login User user) {
		bookFavoriteService.addBookFavorite(request, user);
	}

	@GetMapping("/favorite")
	public ResponseEntity<List<BookResponse>> getFavorite(@Login User user) {
		List<BookResponse> books = bookFavoriteService.getBookFavorites(user);
		return ResponseEntity.ok(books);
	}

	@DeleteMapping("/favorite")
	public void deleteFavorite(@RequestBody BookFavoriteRequest request, @Login User user) {
		bookFavoriteService.deleteBookFavorite(request, user);
	}

	@PostMapping("/teachers/recommend")
	public void addRecommend(@RequestBody BookRecommendRequest request, @Login User user) {
		bookRecommendService.addBookRecommend(request, user);
	}

	@GetMapping("/teachers/recommend")
	public ResponseEntity<BookRecommendResponseTeacher> getRecommendTeacher(@Login User user) {
		BookRecommendResponseTeacher response = bookRecommendService.getBookRecommendsTeacher(user);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/students/recommend")
	public ResponseEntity<BookRecommendResponseStudent> getRecommendStudent(@Login User user) {
		BookRecommendResponseStudent response = bookRecommendService.getBookRecommendsStudent(user);
		return ResponseEntity.ok(response);
	}
}
