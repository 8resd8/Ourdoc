package com.ssafy.ourdoc.domain.book.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.ourdoc.domain.book.dto.BookRequest;
import com.ssafy.ourdoc.domain.book.dto.BookSearchRequest;
import com.ssafy.ourdoc.domain.book.dto.homework.HomeworkStudentDetailPage;
import com.ssafy.ourdoc.domain.book.dto.homework.HomeworkStudentResponse;
import com.ssafy.ourdoc.domain.book.dto.homework.HomeworkTeacherDetailPage;
import com.ssafy.ourdoc.domain.book.dto.homework.HomeworkTeacherResponse;
import com.ssafy.ourdoc.domain.book.service.HomeworkService;
import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.global.annotation.Login;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class HomeworkController {

	private final HomeworkService homeworkService;

	@PostMapping("/teachers/homework")
	public void addHomework(@RequestBody BookRequest request, @Login User user) {
		homeworkService.addHomework(request, user);
	}

	@DeleteMapping("/teachers/homework")
	public void deleteHomework(@RequestBody BookRequest request, @Login User user) {
		homeworkService.deleteHomework(request, user);
	}

	@GetMapping("/teachers/homework")
	public ResponseEntity<HomeworkTeacherResponse> getHomeworkTeacherClass(
		@ModelAttribute BookSearchRequest request,
		@Login User user,
		@PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
		HomeworkTeacherResponse response = homeworkService.getHomeworkTeacherClass(request, user, pageable);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/teachers/homework/{homeworkId}")
	public ResponseEntity<HomeworkTeacherDetailPage> getHomeworkTeacherDetail(
		@PathVariable("homeworkId") long homeworkId,
		@Login User user,
		@PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
		HomeworkTeacherDetailPage response = homeworkService.getHomeworkDetailTeacherPage(homeworkId, user, pageable);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/students/homework")
	public ResponseEntity<HomeworkStudentResponse> getHomeworkStudentClass(
		@ModelAttribute BookSearchRequest request,
		@Login User user,
		@PageableDefault(page = 0, size = 10) Pageable pageable) {
		HomeworkStudentResponse response = homeworkService.getHomeworkStudentClass(request, user, pageable);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/students/homework/{homeworkId}")
	public ResponseEntity<HomeworkStudentDetailPage> getHomeworkStudent(
		@PathVariable("homeworkId") long homeworkId,
		@Login User user,
		@PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
		HomeworkStudentDetailPage response = homeworkService.getHomeworkDetailStudentPage(homeworkId, user, pageable);
		return ResponseEntity.ok(response);
	}
}
