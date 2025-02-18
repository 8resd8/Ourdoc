package com.ssafy.ourdoc.domain.notification.dto.response;

import org.springframework.data.domain.Page;

import com.ssafy.ourdoc.domain.notification.dto.NotificationDtoConvert;

public record NotificationListResponse(Page<NotificationDtoConvert> notifications) {
}
