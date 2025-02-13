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
import com.ssafy.ourdoc.domain.book.dto.homework.HomeworkDetailStudent;
import com.ssafy.ourdoc.domain.book.dto.homework.HomeworkDetailTeacher;
import com.ssafy.ourdoc.domain.book.dto.homework.HomeworkResponseStudent;
import com.ssafy.ourdoc.domain.book.dto.homework.HomeworkResponseTeacher;
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
	public ResponseEntity<HomeworkResponseTeacher> getHomeworkTeacherClass(
		@ModelAttribute BookSearchRequest request,
		@Login User user,
		@PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
		HomeworkResponseTeacher response = homeworkService.getHomeworkTeacherClass(request, user, pageable);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/teachers/homework/{homeworkId}")
	public ResponseEntity<HomeworkDetailTeacher> getHomeworkTeacherDetail(
		@PathVariable("homeworkId") long homeworkId,
		@Login User user) {
		HomeworkDetailTeacher response = homeworkService.getHomeworkDetailTeacher(homeworkId, user);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/students/homework")
	public ResponseEntity<HomeworkResponseStudent> getHomeworkStudentClass(
		@ModelAttribute BookSearchRequest request,
		@Login User user,
		@PageableDefault(page = 0, size = 10) Pageable pageable) {
		HomeworkResponseStudent response = homeworkService.getHomeworkStudentClass(request, user, pageable);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/students/homework/{homeworkId}")
	public ResponseEntity<HomeworkDetailStudent> getHomeworkStudent(@PathVariable("homeworkId") long homeworkId,
		@Login User user) {
		return ResponseEntity.ok(homeworkService.getHomeworkDetailStudent(homeworkId, user));
	}
}
