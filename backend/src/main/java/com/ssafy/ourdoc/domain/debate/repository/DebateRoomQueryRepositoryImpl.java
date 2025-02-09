package com.ssafy.ourdoc.domain.debate.repository;

import static com.ssafy.ourdoc.domain.debate.entity.QRoomOnline.*;

import java.util.Optional;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.ourdoc.domain.debate.entity.RoomOnline;

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

	public Optional<RoomOnline> findActiveByRoomIdAndUserId(Long roomId, Long userId) {
		RoomOnline result = queryFactory.selectFrom(roomOnline)
			.where(
				roomOnline.room.id.eq(roomId),
				roomOnline.user.id.eq(userId),
				roomOnline.createdAt.eq(roomOnline.updatedAt)
			).fetchOne();
		return Optional.ofNullable(result);
	}
}
