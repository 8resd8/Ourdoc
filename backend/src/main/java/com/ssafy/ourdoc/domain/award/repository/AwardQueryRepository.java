package com.ssafy.ourdoc.domain.award.repository;

import java.util.List;

import com.ssafy.ourdoc.domain.award.dto.AwardDto;

public interface AwardQueryRepository {

	List<AwardDto> findAllAward();
}
