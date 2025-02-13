package com.ssafy.ourdoc.domain.book.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.ourdoc.domain.book.dto.BookRequest;
import com.ssafy.ourdoc.domain.book.dto.BookSearchRequest;
import com.ssafy.ourdoc.domain.book.dto.recommend.BookRecommendResponseStudent;
import com.ssafy.ourdoc.domain.book.dto.recommend.BookRecommendResponseTeacher;
import com.ssafy.ourdoc.domain.book.service.BookRecommendService;
import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.global.annotation.Login;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookRecommendController {

	private final BookRecommendService bookRecommendService;

	@PostMapping("/teachers/recommend")
	public void addRecommend(@RequestBody BookRequest request, @Login User user) {
		bookRecommendService.addBookRecommend(request, user);
	}

	@GetMapping("/teachers/recommend")
	public ResponseEntity<BookRecommendResponseTeacher> getRecommendTeacher(@ModelAttribute BookSearchRequest request,
		@Login User user) {
		BookRecommendResponseTeacher response = bookRecommendService.getBookRecommendsTeacher(request, user);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/students/recommend")
	public ResponseEntity<BookRecommendResponseStudent> getRecommendStudent(@ModelAttribute BookSearchRequest request,
		@Login User user) {
		BookRecommendResponseStudent response = bookRecommendService.getBookRecommendsStudent(request, user);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/teachers/recommend/classes")
	public ResponseEntity<BookRecommendResponseTeacher> getRecommendTeacherClass(
		@ModelAttribute BookSearchRequest request,
		@Login User user) {
		BookRecommendResponseTeacher response = bookRecommendService.getBookRecommendsTeacherClass(request, user);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/students/recommend/classes")
	public ResponseEntity<BookRecommendResponseStudent> getRecommendStudentClass(
		@ModelAttribute BookSearchRequest request,
		@Login User user) {
		BookRecommendResponseStudent response = bookRecommendService.getBookRecommendsStudentClass(request, user);
		return ResponseEntity.ok(response);
	}
}
