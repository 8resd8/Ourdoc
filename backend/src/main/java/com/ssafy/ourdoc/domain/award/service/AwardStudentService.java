package com.ssafy.ourdoc.domain.award.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.ourdoc.domain.award.dto.AwardDto;
import com.ssafy.ourdoc.domain.award.dto.AwardListResponse;
import com.ssafy.ourdoc.domain.award.dto.AwardStampResponse;
import com.ssafy.ourdoc.domain.award.repository.AwardRepository;
import com.ssafy.ourdoc.domain.user.entity.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AwardStudentService {

	private final AwardRepository awardRepository;

	// 학생 - 상장 전체조회
	public AwardListResponse getAllAwards(User user) {

		List<AwardDto> allAwards = awardRepository.findAllAwardByUserId(user.getId());
		return new AwardListResponse(allAwards);
	}

	public AwardStampResponse getStampCount(User user) {
		int stampCount = awardRepository.getStampCount(user.getId());
		return new AwardStampResponse(stampCount);
	}
}
