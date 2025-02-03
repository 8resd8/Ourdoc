package com.ssafy.ourdoc.domain.notification.repository;

import static com.ssafy.ourdoc.domain.notification.entity.QNotification.*;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.ourdoc.domain.notification.dto.NotificationConditionRequest;
import com.ssafy.ourdoc.domain.notification.dto.NotificationDto;
import com.ssafy.ourdoc.domain.notification.entity.QNotificationRecipient;
import com.ssafy.ourdoc.global.common.enums.NotificationStatus;
import com.ssafy.ourdoc.global.common.enums.NotificationType;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class NotificationQueryRepositoryImpl implements NotificationQueryRepository {

	private final JPAQueryFactory queryFactory;

	@Override
	public List<NotificationDto> findAllConditionByUserId(Long userId, NotificationConditionRequest condition,
		Pageable pageable) {
		QNotificationRecipient recipient = QNotificationRecipient.notificationRecipient;

		return queryFactory
			.select(Projections.constructor(NotificationDto.class,
				notification.id,
				notification.notificationType,
				notification.content,
				notification.createdAt))
			.from(notification)
			.join(recipient).on(notification.id.eq(recipient.notification.id))
			.where(
				userEq(userId, recipient),
				readFilter(recipient, condition.status()),
				typeFilter(condition.type()))
			.orderBy(notification.createdAt.desc())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();
	}

	@Override
	public NotificationDto findByNotificationId(Long loginUserId, Long notificationId) {
		return queryFactory
			.select(Projections.constructor(NotificationDto.class,
				notification.id,
				notification.notificationType,
				notification.content,
				notification.createdAt))
			.from(notification)
			.where(notification.id.eq(notificationId), notification.id.eq(loginUserId))
			.fetchOne();
	}

	private BooleanExpression typeFilter(NotificationType type) {
		return type == null ? null : notification.notificationType.eq(type);
	}

	private BooleanExpression userEq(Long userId, QNotificationRecipient recipient) {
		return recipient.recipient.id.eq(userId);
	}

	private BooleanExpression readFilter(QNotificationRecipient recipient, NotificationStatus status) {
		if (status == NotificationStatus.읽음) {
			return recipient.readTime.isNotNull();
		} else if (status == NotificationStatus.안읽음) {
			return recipient.readTime.isNull();
		}
		return null;
	}
}
