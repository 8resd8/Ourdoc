package com.ssafy.ourdoc.domain.notification.repository;

import static com.ssafy.ourdoc.domain.notification.entity.QNotification.*;
import static com.ssafy.ourdoc.domain.notification.entity.QNotificationRecipient.*;
import static com.ssafy.ourdoc.global.common.enums.NotificationStatus.*;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.ourdoc.domain.notification.dto.request.NotificationConditionRequest;
import com.ssafy.ourdoc.domain.notification.dto.NotificationDetailDto;
import com.ssafy.ourdoc.domain.notification.dto.NotificationDto;
import com.ssafy.ourdoc.domain.notification.entity.QNotificationRecipient;
import com.ssafy.ourdoc.global.common.enums.NotificationStatus;
import com.ssafy.ourdoc.global.common.enums.NotificationType;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class NotificationQueryRepositoryImpl implements NotificationQueryRepository {

	private final JPAQueryFactory queryFactory;

	// 알림 전체조회 (조건에 따른)
	@Override
	public Page<NotificationDto> findAllConditionByUserId(Long userId, NotificationConditionRequest condition,
		Pageable pageable) {
		QNotificationRecipient recipient = notificationRecipient;

		List<NotificationDto> content = queryFactory
			.select(Projections.constructor(NotificationDto.class,
				notification.id,
				notification.notificationType,
				notification.content,
				notification.createdAt,
				notification.sender.name,
				recipient.readTime))
			.from(notification)
			.leftJoin(recipient).on(notification.id.eq(recipient.notification.id))
			.where(
				userEq(userId, recipient),
				readFilter(recipient, condition.status()),
				typeFilter(condition.type()))
			.orderBy(notification.createdAt.desc())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		Long total = queryFactory
			.select(notification.count())
			.from(notification)
			.join(recipient).on(notification.id.eq(recipient.notification.id))
			.where(
				userEq(userId, recipient),
				readFilter(recipient, condition.status()),
				typeFilter(condition.type())
			)
			.fetchOne();

		return new PageImpl<>(content, pageable, total);
	}

	// 알림 상세조회
	@Override
	public NotificationDetailDto findByNotificationId(Long loginUserId, Long notificationId) {
		return queryFactory
			.select(Projections.constructor(NotificationDetailDto.class,
				notification.id,
				notification.notificationType,
				notification.sender.name.as("senderName"),
				notification.content,
				notification.createdAt,
				notificationRecipient.readTime))
			.from(notification)
			.leftJoin(notificationRecipient).on(notification.id.eq(notificationRecipient.notification.id))
			.where(
				notificationIdEquals(notificationId), // 알림 id 일치여부
				recipientEquals(loginUserId)) // 알림수신자: 로그인유저
			.fetchOne();
	}

	private BooleanExpression notificationIdEquals(Long notificationId) {
		return notificationRecipient.notification.id.eq(notificationId);
	}

	private BooleanExpression recipientEquals(Long loginUserId) {
		return notificationRecipient.recipient.id.eq(loginUserId);
	}

	private BooleanExpression typeFilter(NotificationType type) {
		return type == null ? null : notification.notificationType.eq(type);
	}

	private BooleanExpression userEq(Long userId, QNotificationRecipient recipient) {
		return recipient.recipient.id.eq(userId);
	}

	private BooleanExpression readFilter(QNotificationRecipient recipient, NotificationStatus status) {
		if (status == 읽음) {
			return recipient.readTime.isNotNull();
		} else if (status == 안읽음) {
			return recipient.readTime.isNull();
		}
		return null;
	}
}
