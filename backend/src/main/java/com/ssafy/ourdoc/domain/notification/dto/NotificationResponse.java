package com.ssafy.ourdoc.domain.notification.dto;

import java.util.List;

public record NotificationResponse(List<NotificationDto> notifications) {
}
