package com.ssafy.ourdoc.domain.award.service;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.ssafy.ourdoc.domain.award.dto.AwardDto;
import com.ssafy.ourdoc.domain.award.repository.AwardRepository;
import com.ssafy.ourdoc.domain.user.entity.User;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AwardService {

	private final AwardRepository awardRepository;

	public AwardDto awardDetail(User user, Long awardId) {
		return getFindAward(user.getId(), awardId);
	}

	private AwardDto getFindAward(Long userId, Long awardId) {
		return awardRepository.findAwardByUserId(userId, awardId)
			.orElseThrow(() -> new NoSuchElementException("상장이 없습니다."));
	}

}
