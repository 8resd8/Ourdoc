package com.ssafy.ourdoc.domain.award.controller;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.ourdoc.domain.award.dto.AwardListResponse;
import com.ssafy.ourdoc.domain.award.dto.CreateAwardRequest;
import com.ssafy.ourdoc.domain.award.service.AwardService;
import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.global.annotation.Login;

import lombok.RequiredArgsConstructor;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/awards")
public class AwardController {

	private final AwardService awardService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void createAward(@Login User user, @RequestPart CreateAwardRequest request,
		@RequestPart(name = "awardImage") MultipartFile file) {
		awardService.createAward(user, request, file);
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public AwardListResponse allAward(@Login User user) {
		return awardService.getAllAwards(user);
	}

	@GetMapping("/{awardId}")
	@ResponseStatus(HttpStatus.OK)
	public void createAward(@Login User user, @PathVariable(name = "awardId") Long awardId) {
		awardService.awardDetail(user, awardId);
	}
}
