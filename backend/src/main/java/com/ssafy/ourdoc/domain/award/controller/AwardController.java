package com.ssafy.ourdoc.domain.award.controller;

import org.springframework.http.HttpStatus;
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

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/awards")
public class AwardController {

	private final AwardService awardService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void createAward(@RequestPart CreateAwardRequest request,
		@RequestPart(name = "awardImage") MultipartFile file) {
		awardService.createAward(request, file);
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public AwardListResponse allAward() {
		return awardService.getAllAward();
	}

	@GetMapping("/{awardId}")
	@ResponseStatus(HttpStatus.OK)
	public void createAward(@PathVariable(name = "awardId") Long awardId) {
		awardService.searchAward(awardId);
	}
}
