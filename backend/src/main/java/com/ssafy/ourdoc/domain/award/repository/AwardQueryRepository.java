package com.ssafy.ourdoc.domain.award.repository;

import java.util.List;
import java.util.Optional;

import com.ssafy.ourdoc.domain.award.dto.AwardDto;

public interface AwardQueryRepository {

	List<AwardDto> findAllAwardByUserId(Long userId);

	Optional<AwardDto> findAwardByUserId(Long userId, Long awardId);
}
