package com.ssafy.ourdoc.domain.notification.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.ssafy.ourdoc.domain.notification.dto.NotificationResponse;
import com.ssafy.ourdoc.global.common.enums.NotificationType;

@Service
public class NotificationService {

	// 사용자별 SSE 연결 관리
	private final ConcurrentHashMap<Long, List<SseEmitter>> emitters = new ConcurrentHashMap<>();

	// 알림 구독
	public SseEmitter subscribe(Long userId) {
		SseEmitter emitter = new SseEmitter(30 * 60 * 1000L);

		// 하트비트 설정
		Timer timer = new Timer(true);
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				try {
					emitter.send(SseEmitter.event().name("heartbeat").data("ping"));
				} catch (IOException e) {
					timer.cancel();
					removeEmitter(userId, emitter);
					emitter.complete();
				}
			}
		}, 0, 15000);

		emitter.onCompletion(() -> {
			timer.cancel();
			removeEmitter(userId, emitter);
		});
		emitter.onTimeout(() -> {
			timer.cancel();
			removeEmitter(userId, emitter);
		});
		emitter.onError((e) -> {
			timer.cancel();
			removeEmitter(userId, emitter);
		});

		emitters.computeIfAbsent(userId, key -> new CopyOnWriteArrayList<>()).add(emitter);
		return emitter;
	}

	// 메시지 보내기
	public void sendNotification(Long userId, NotificationType type, String content) {
		List<SseEmitter> userEmitters = emitters.get(userId);
		if (userEmitters != null) {
			NotificationResponse notification = new NotificationResponse(type, content, LocalDateTime.now());
			userEmitters.forEach(emitter -> {
				try {
					emitter.send(SseEmitter.event().name("알림사항: ").data(notification));
				} catch (IOException e) {
					removeEmitter(userId, emitter); // 문제생기면 삭제
					emitter.complete();
				}
			});
		}
	}

	private void removeEmitter(Long userId, SseEmitter emitter) {
		List<SseEmitter> userEmitters = emitters.get(userId);
		if (userEmitters != null) {
			userEmitters.remove(emitter);
			if (userEmitters.isEmpty()) {
				emitters.remove(userId);
			}
		}
	}

}
