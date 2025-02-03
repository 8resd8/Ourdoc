package com.ssafy.ourdoc.domain.notification.dto;

import com.ssafy.ourdoc.global.common.enums.NotificationStatus;
import com.ssafy.ourdoc.global.common.enums.NotificationType;

public record NotificationConditionRequest(
	NotificationStatus status,
	NotificationType type
) {
}
