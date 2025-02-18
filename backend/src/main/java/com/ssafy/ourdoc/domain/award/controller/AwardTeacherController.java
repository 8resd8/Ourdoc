package com.ssafy.ourdoc.domain.award.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.ourdoc.domain.award.dto.teacher.AwardTeacherCreateRequest;
import com.ssafy.ourdoc.domain.award.dto.teacher.AwardTeacherRequest;
import com.ssafy.ourdoc.domain.award.dto.teacher.AwardTeacherResponse;
import com.ssafy.ourdoc.domain.award.service.AwardTeacherService;
import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.global.annotation.Login;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/teachers/awards")
public class AwardTeacherController {

	private final AwardTeacherService awardTeacherService;

	// 본인 반 학생 상장 조회
	@GetMapping
	public AwardTeacherResponse getTeacherClassAwards(@Login User user,
		@Valid @ModelAttribute AwardTeacherRequest request) {
		return awardTeacherService.getAwardTeachersClass(user, request);
	}

	// 상장 생성
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void createAward(@Login User user, @RequestPart AwardTeacherCreateRequest request,
		@RequestPart(name = "awardImage") MultipartFile file) {
		awardTeacherService.createAward(user, request, file);
	}

}
