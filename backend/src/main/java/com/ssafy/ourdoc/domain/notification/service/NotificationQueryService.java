package com.ssafy.ourdoc.domain.notification.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ssafy.ourdoc.domain.notification.dto.NotificationConditionRequest;
import com.ssafy.ourdoc.domain.notification.dto.NotificationDetailDto;
import com.ssafy.ourdoc.domain.notification.dto.NotificationDetailResponse;
import com.ssafy.ourdoc.domain.notification.dto.NotificationDto;
import com.ssafy.ourdoc.domain.notification.dto.NotificationListResponse;
import com.ssafy.ourdoc.domain.notification.repository.NotificationQueryRepository;
import com.ssafy.ourdoc.domain.user.entity.User;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class NotificationQueryService {

	private final NotificationQueryRepository notificationQueryRepository;

	public NotificationListResponse getUnreadNotifications(User user, NotificationConditionRequest request,
		Pageable pageable) {
		Page<NotificationDto> notificationDtoList = notificationQueryRepository.
			findAllConditionByUserId(user.getId(), request, pageable);

		return new NotificationListResponse(notificationDtoList);
	}

	public NotificationDetailResponse getNotification(User user, Long notificationId) {
		return new NotificationDetailResponse(notificationQueryRepository.findByNotificationId(user.getId(), notificationId));
	}
}
