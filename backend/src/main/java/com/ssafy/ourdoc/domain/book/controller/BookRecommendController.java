package com.ssafy.ourdoc.domain.book.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.ourdoc.domain.book.dto.BookRequest;
import com.ssafy.ourdoc.domain.book.dto.BookSearchRequest;
import com.ssafy.ourdoc.domain.book.dto.recommend.BookRecommendStudentResponse;
import com.ssafy.ourdoc.domain.book.dto.recommend.BookRecommendTeacherResponse;
import com.ssafy.ourdoc.domain.book.service.BookRecommendService;
import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.global.annotation.Login;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookRecommendController {

	private final BookRecommendService bookRecommendService;

	@PostMapping("/teachers/recommend/classes")
	@ResponseStatus(HttpStatus.CREATED)
	public void addRecommend(@RequestBody BookRequest request, @Login User user) {
		bookRecommendService.addBookRecommend(request, user);
	}

	@DeleteMapping("/teachers/recommend/classes")
	public void deleteRecommend(@RequestBody BookRequest request, @Login User user) {
		bookRecommendService.deleteBookRecommend(request, user);
	}

	@GetMapping("/teachers/recommend/grades")
	public ResponseEntity<BookRecommendTeacherResponse> getRecommendTeacher(
		@ModelAttribute BookSearchRequest request,
		@Login User user,
		@PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
		BookRecommendTeacherResponse response = bookRecommendService.getBookRecommendsTeacher(request, user, pageable);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/students/recommend/grades")
	public ResponseEntity<BookRecommendStudentResponse> getRecommendStudent(
		@ModelAttribute BookSearchRequest request,
		@Login User user,
		@PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
		BookRecommendStudentResponse response = bookRecommendService.getBookRecommendsStudent(request, user, pageable);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/teachers/recommend/classes")
	public ResponseEntity<BookRecommendTeacherResponse> getRecommendTeacherClass(
		@ModelAttribute BookSearchRequest request,
		@Login User user,
		@PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
		BookRecommendTeacherResponse response = bookRecommendService.getBookRecommendsTeacherClass(request, user,
			pageable);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/students/recommend/classes")
	public ResponseEntity<BookRecommendStudentResponse> getRecommendStudentClass(
		@ModelAttribute BookSearchRequest request,
		@Login User user,
		@PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
		BookRecommendStudentResponse response = bookRecommendService.getBookRecommendsStudentClass(request, user,
			pageable);
		return ResponseEntity.ok(response);
	}
}
