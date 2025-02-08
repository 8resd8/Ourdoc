package com.ssafy.ourdoc.domain.book.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.ourdoc.domain.book.dto.BookRecommendRequest;
import com.ssafy.ourdoc.domain.book.dto.BookRecommendResponseStudent;
import com.ssafy.ourdoc.domain.book.dto.BookRecommendResponseTeacher;
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
