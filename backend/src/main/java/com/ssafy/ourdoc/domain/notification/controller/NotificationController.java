package com.ssafy.ourdoc.domain.notification.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
import com.ssafy.ourdoc.global.common.enums.NotificationType;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notifications")
public class NotificationController {

	private final NotificationService notificationService;
	private final NotificationHistoryService notificationHistoryService;
	private final NotificationQueryService notificationQueryService;

	@GetMapping(value = "/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public SseEmitter subscribe() {
		Long userId = 8L;
		return notificationService.subscribe(userId);
	}

	@PatchMapping("/{notificationId}/read")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void markAsRead(@PathVariable("notificationId") Long notificationId) {
		notificationHistoryService.markNotificationRead(notificationId);
	}

	@GetMapping
	public NotificationListResponse getNotifications(@ModelAttribute NotificationConditionRequest request,
		Pageable pageable) {
		long loginUserId = 9L; // 로그인 한 정보에서 추출
		return notificationQueryService.getUnreadNotifications(loginUserId, request, pageable);
	}

	@GetMapping("/{notificationId}")
	public NotificationDetailDto getNotification(@PathVariable("notificationId") Long notificationId) {
		long loginUserId = 9L; // 로그인 한 정보에서 추출
		return notificationQueryService.getNotification(loginUserId, notificationId);
	}

	@PostMapping("/send")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void sendNotificationTest() {
		Long userId = 8L;
		notificationService.sendNotification(userId, NotificationType.독서록, "독서록 저장했어.");
	}

}
