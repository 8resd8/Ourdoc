package com.ssafy.ourdoc.domain.notification.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.ssafy.ourdoc.domain.notification.dto.NotificationConditionRequest;
import com.ssafy.ourdoc.domain.notification.dto.NotificationDto;

public interface NotificationQueryRepository {

	List<NotificationDto> findAllConditionByUserId(Long userId, NotificationConditionRequest request,
		Pageable pageable);

	NotificationDto findByNotificationId(Long loginUserId, Long notificationId);

}
