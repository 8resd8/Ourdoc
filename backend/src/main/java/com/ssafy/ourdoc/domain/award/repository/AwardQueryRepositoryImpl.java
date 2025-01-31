package com.ssafy.ourdoc.domain.award.repository;

import static com.querydsl.core.types.Projections.*;
import static com.ssafy.ourdoc.domain.award.entity.QAward.*;

import java.util.List;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.ourdoc.domain.award.dto.AwardDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AwardQueryRepositoryImpl implements AwardQueryRepository {

	private final JPAQueryFactory queryFactory;

	@Override
	public List<AwardDto> findAllAward() {
		return queryFactory
			.select(constructor(AwardDto.class,
				award.id,
				award.imagePath,
				award.title,
				award.createdAt))
			.from(award)
			.fetch();
	}
}
