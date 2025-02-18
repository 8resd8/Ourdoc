package com.ssafy.ourdoc.domain.notification.dto;

import java.time.LocalDateTime;

import com.ssafy.ourdoc.global.common.enums.NotificationStatus;
import com.ssafy.ourdoc.global.common.enums.NotificationType;

import lombok.Builder;

@Builder
public record NotificationDetailDtoConvert(
	Long notificationId,
	NotificationType type,
	String senderName,
	String content,
	LocalDateTime createdAt,
	NotificationStatus status
) {
}
