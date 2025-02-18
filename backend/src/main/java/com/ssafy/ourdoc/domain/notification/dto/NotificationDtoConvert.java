package com.ssafy.ourdoc.domain.notification.dto;

import java.time.LocalDateTime;

import com.ssafy.ourdoc.global.common.enums.NotificationStatus;
import com.ssafy.ourdoc.global.common.enums.NotificationType;

public record NotificationDtoConvert(
	Long notificationId,
	NotificationType type,
	String content,
	LocalDateTime createdAt,
	String senderName,
	NotificationStatus status
) {
}
