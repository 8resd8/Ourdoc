package com.ssafy.ourdoc.domain.notification.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ssafy.ourdoc.domain.notification.dto.request.NotificationConditionRequest;
import com.ssafy.ourdoc.domain.notification.dto.NotificationDetailDto;
import com.ssafy.ourdoc.domain.notification.dto.NotificationDto;

public interface NotificationQueryRepository {

	Page<NotificationDto> findAllConditionByUserId(Long userId, NotificationConditionRequest request,
		Pageable pageable);

	NotificationDetailDto findByNotificationId(Long loginUserId, Long notificationId);

}
