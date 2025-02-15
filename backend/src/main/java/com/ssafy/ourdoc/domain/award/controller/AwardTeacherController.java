package com.ssafy.ourdoc.domain.award.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.ourdoc.domain.award.dto.teacher.AwardTeacherRequest;
import com.ssafy.ourdoc.domain.award.dto.teacher.AwardTeacherResponse;
import com.ssafy.ourdoc.domain.award.service.AwardTeacherService;
import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.global.annotation.Login;

import lombok.RequiredArgsConstructor;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/teachers/awards")
public class AwardTeacherController {

	private final AwardTeacherService awardTeacherService;

	@GetMapping
	public AwardTeacherResponse getTeacherClassAwards(@Login User user, @ModelAttribute AwardTeacherRequest request) {
		return awardTeacherService.getAwardTeachersClass(user, request);
	}
}
