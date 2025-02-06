package com.ssafy.ourdoc.domain.notification.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.ssafy.ourdoc.domain.notification.dto.NotificationConditionRequest;
import com.ssafy.ourdoc.domain.notification.dto.NotificationDetailDto;
import com.ssafy.ourdoc.domain.notification.dto.NotificationListResponse;
import com.ssafy.ourdoc.domain.notification.service.NotificationHistoryService;
import com.ssafy.ourdoc.domain.notification.service.NotificationQueryService;
import com.ssafy.ourdoc.domain.notification.service.NotificationService;
import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.global.annotation.Login;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notifications")
public class NotificationController {

	private final NotificationService notificationService;
	private final NotificationHistoryService notificationHistoryService;
	private final NotificationQueryService notificationQueryService;

	@GetMapping(value = "/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public SseEmitter subscribe(@Login User user) {
		return notificationService.subscribe(user);
	}

	@PatchMapping("/{notificationId}/read")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void markAsRead(@Login User user, @PathVariable("notificationId") Long notificationId) {
		notificationHistoryService.markNotificationRead(user, notificationId);
	}

	@GetMapping
	public NotificationListResponse getNotifications(@Login User user,
		@ModelAttribute NotificationConditionRequest request,
		Pageable pageable) {
		return notificationQueryService.getUnreadNotifications(user, request, pageable);
	}

	@GetMapping("/{notificationId}")
	public NotificationDetailDto getNotification(@Login User user,
		@PathVariable("notificationId") Long notificationId) {
		return notificationQueryService.getNotification(user, notificationId);
	}

}
