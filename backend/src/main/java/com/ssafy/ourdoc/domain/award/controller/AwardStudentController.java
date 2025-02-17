package com.ssafy.ourdoc.domain.award.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.ourdoc.domain.award.dto.AwardListResponse;
import com.ssafy.ourdoc.domain.award.dto.AwardStampResponse;
import com.ssafy.ourdoc.domain.award.service.AwardStudentService;
import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.global.annotation.Login;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/students/awards")
public class AwardStudentController {

	private final AwardStudentService awardStudentService;

	// 상장 전체 조회
	@GetMapping
	public AwardListResponse allAward(@Login User user) {
		return awardStudentService.getAllAwards(user);
	}

	@GetMapping("/stamp")
	public AwardStampResponse getStampCount(@Login User user) {
		return awardStudentService.getStampCount(user);
	}
}
