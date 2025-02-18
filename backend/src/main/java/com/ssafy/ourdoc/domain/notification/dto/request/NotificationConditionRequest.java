package com.ssafy.ourdoc.domain.notification.dto.request;

import com.ssafy.ourdoc.global.annotation.EnumValid;
import com.ssafy.ourdoc.global.common.enums.NotificationStatus;
import com.ssafy.ourdoc.global.common.enums.NotificationType;

public record NotificationConditionRequest(

	@EnumValid(enumClass = NotificationStatus.class, message = "{not.enum}")
	NotificationStatus status,

	@EnumValid(enumClass = NotificationType.class, message = "{not.enum}")
	NotificationType type
) {
}
