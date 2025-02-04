package com.ssafy.ourdoc.domain.notification.dto;

import java.util.List;

import org.springframework.data.domain.Page;

public record NotificationListResponse(Page<NotificationDto> notifications) {
}
