package com.ssafy.ourdoc.domain.notification.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ssafy.ourdoc.domain.notification.dto.NotificationConditionRequest;
import com.ssafy.ourdoc.domain.notification.dto.NotificationDto;
import com.ssafy.ourdoc.domain.notification.dto.NotificationResponse;
import com.ssafy.ourdoc.domain.notification.repository.NotificationQueryRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class NotificationQueryService {

	private final NotificationQueryRepository notificationQueryRepository;

	public NotificationResponse getUnreadNotifications(Long userId, NotificationConditionRequest request,
		Pageable pageable) {
		List<NotificationDto> notificationDtoList = notificationQueryRepository.
			findAllConditionByUserId(userId, request, pageable);

		return new NotificationResponse(notificationDtoList);
	}
}
