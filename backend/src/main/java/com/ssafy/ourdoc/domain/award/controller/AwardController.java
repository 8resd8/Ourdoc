package com.ssafy.ourdoc.domain.award.controller;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.ourdoc.domain.award.dto.AwardListResponse;
import com.ssafy.ourdoc.domain.award.service.AwardService;
import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.global.annotation.Login;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/awards")
public class AwardController {

	private final AwardService awardService;

	// 상장 상세조회
	@GetMapping("/{awardId}")
	public void createAward(@Login User user, @PathVariable(name = "awardId") Long awardId) {
		awardService.awardDetail(user, awardId);
	}
}
