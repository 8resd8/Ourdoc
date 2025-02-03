package com.ssafy.ourdoc.domain.notification.dto;

import java.time.LocalDateTime;

import com.ssafy.ourdoc.global.common.enums.NotificationType;

public record NotificationDetailDto(
	Long notificationId,
	NotificationType type,
	String senderName,
	String content,
	LocalDateTime createdAt
) {
}
