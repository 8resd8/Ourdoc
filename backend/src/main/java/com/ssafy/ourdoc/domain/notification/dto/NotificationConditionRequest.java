package com.ssafy.ourdoc.domain.notification.dto;

import com.ssafy.ourdoc.global.common.enums.NotificationStatus;
import com.ssafy.ourdoc.global.common.enums.NotificationType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record NotificationConditionRequest(

	@NotBlank(message = "{notblank}")
	@Size(max = 250, message = "{size.max}")
	NotificationStatus status,

	@NotBlank(message = "{notblank}")
	@Size(max = 250, message = "{size.max}")
	NotificationType type
) {
}
