package com.ssafy.ourdoc.domain.award.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.ourdoc.domain.award.dto.AwardDto;
import com.ssafy.ourdoc.domain.award.dto.AwardListResponse;
import com.ssafy.ourdoc.domain.award.dto.AwardStampResponse;
import com.ssafy.ourdoc.domain.award.entity.Award;
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
		int awardCount = awardRepository.getAwardCount(user.getId());
		for (int i = awardCount; i < stampCount / 10; i++) {
			Award award = Award.builder()
				.user(user)
				.imagePath("image")
				.title("다독왕")
				.build();

			awardRepository.save(award);
		}
		return new AwardStampResponse(stampCount);
	}
}
