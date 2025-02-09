package com.ssafy.ourdoc.domain.book.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.ourdoc.domain.book.dto.BookRequest;
import com.ssafy.ourdoc.domain.book.dto.homework.HomeworkDetailTeacher;
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

	@GetMapping("/teachers/homework/{homeworkId}")
	public ResponseEntity<HomeworkDetailTeacher> getHomework(@PathVariable long homeworkId, @Login User user) {
		return ResponseEntity.ok(homeworkService.getHomeworkDetailTeacher(homeworkId, user));
	}
}
