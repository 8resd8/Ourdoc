package com.ssafy.ourdoc.domain.notification.dto;

import java.time.LocalDateTime;

import com.ssafy.ourdoc.global.common.enums.NotificationType;

public record NotificationResponse(
	Long notificationId,
	NotificationType type,
	String content,
	LocalDateTime createdAt
) {
}
