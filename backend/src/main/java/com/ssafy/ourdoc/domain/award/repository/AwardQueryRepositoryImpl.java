package com.ssafy.ourdoc.domain.award.repository;

import static com.querydsl.core.types.Projections.*;
import static com.ssafy.ourdoc.domain.award.entity.QAward.*;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.ourdoc.domain.award.dto.AwardDto;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AwardQueryRepositoryImpl implements AwardQueryRepository {

	private final JPAQueryFactory queryFactory;

	@Override
	public List<AwardDto> findAllAwardByUserId(Long userId) {
		return queryFactory
			.select(constructor(AwardDto.class,
				award.id,
				award.imagePath,
				award.title,
				award.createdAt))
			.from(award)
			.where(userEq(userId))
			.fetch();
	}

	private BooleanExpression userEq(Long userId) {
		return award.id.eq(userId);
	}

	@Override
	public Optional<AwardDto> findAwardByUserId(Long userId, Long awardId) {
		return Optional.ofNullable(queryFactory
			.select(constructor(AwardDto.class,
				award.id,
				award.imagePath,
				award.title,
				award.createdAt))
			.from(award)
			.where(userEq(userId), award.id.eq(awardId))
			.fetchOne());
	}
}
