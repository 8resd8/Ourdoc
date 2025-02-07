package com.ssafy.ourdoc.domain.debate.repository;

import static com.ssafy.ourdoc.domain.debate.entity.QRoomOnline.*;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DebateRoomQueryRepositoryImpl implements DebateRoomQueryRepository {
	private final JPAQueryFactory queryFactory;

	public Long countCurrentPeople(Long roomId) {
		return queryFactory.select(roomOnline.count())
			.from(roomOnline)
			.where(
				roomOnline.room.id.eq(roomId)
					.and(roomOnline.createdAt.eq(roomOnline.updatedAt))
			).fetchOne();
	}
}
