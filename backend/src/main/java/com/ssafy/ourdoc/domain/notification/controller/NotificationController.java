package com.ssafy.ourdoc.domain.notification.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.ssafy.ourdoc.domain.notification.service.NotificationHistoryService;
import com.ssafy.ourdoc.domain.notification.service.NotificationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notification")
public class NotificationController {

	private final NotificationService notificationService;
	private final NotificationHistoryService notificationHistoryService;

	@GetMapping("/subscribe")
	public SseEmitter subscribe() {
		return notificationService.subscribe();
	}

	@PatchMapping("/{recipientId}/read")
	public void markAsRead(@PathVariable("recipientId") Long recipientId) {
		notificationHistoryService.markNotificationRead(recipientId);
	}

}
