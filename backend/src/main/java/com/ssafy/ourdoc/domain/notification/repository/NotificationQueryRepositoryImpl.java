package com.ssafy.ourdoc.domain.notification.repository;

import static com.ssafy.ourdoc.domain.notification.entity.QNotification.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.ourdoc.domain.notification.dto.NotificationDto;
import com.ssafy.ourdoc.domain.notification.entity.QNotificationRecipient;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class NotificationQueryRepositoryImpl implements NotificationQueryRepository {

	private final JPAQueryFactory queryFactory;

	// user가 읽지 않은 알림 전체조회
	@Override
	public List<NotificationDto> findAllByUserId(Long userId, Pageable pageable) {
		QNotificationRecipient recipientId = QNotificationRecipient.notificationRecipient;

		return queryFactory
			.select(notification)
			.from(notification)
			.join(recipientId).on(notification.id.eq(recipientId.notification.id))
			.where(userEq(userId, recipientId), notRead(recipientId))
			.orderBy(notification.createdAt.desc())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch()
			.stream()
			.map(n -> new NotificationDto(
				n.getId(),
				n.getNotificationType(),
				n.getContent(),
				n.getCreatedAt()
			))
			.collect(Collectors.toList());
	}

	private BooleanExpression userEq(Long userId, QNotificationRecipient recipient) {
		return recipient.recipient.id.eq(userId);
	}

	private BooleanExpression notRead(QNotificationRecipient recipient) {
		return recipient.readTime.isNull();
	}
}
