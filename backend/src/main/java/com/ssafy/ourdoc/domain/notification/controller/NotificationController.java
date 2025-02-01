package com.ssafy.ourdoc.domain.notification.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.ssafy.ourdoc.domain.notification.service.NotificationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notification")
public class NotificationController {

	private final NotificationService notificationService;

	@GetMapping("/subscribe")
	public SseEmitter subscribe() {
		Long userId = 8L; // 로그인 한 사용자 정보 토큰에서 가져오기.
		return notificationService.subscribe(userId);
	}

}
