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

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

	public static final long TIMEOUT = 30 * 60 * 1000L; // 연결 시간, 30분
	public static final int PERIOD = 60000; // 1분 간격 연결 유지
	private final NotificationHistoryService notificationHistoryService;
	private final ConcurrentHashMap<Long, List<SseEmitter>> emitters = new ConcurrentHashMap<>();

	// 알림 구독
	public SseEmitter subscribe() {
		Long userId = 8L; // 로그인 한 사용자 정보 토큰에서 가져오기. (임시 하드코딩)
		SseEmitter emitter = new SseEmitter(TIMEOUT);

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
		}, 0, PERIOD);

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

	// 알림 전송
	public void sendNotification(NotificationType type, String content) {
		Long userId = 8L;

		// 알림 전송 성공 시에만 DB 저장
		if (sendToEmitters(userId, type, content)) {
			notificationHistoryService.saveHistory(userId, type, content);
		}
	}

	private boolean sendToEmitters(Long userId, NotificationType type, String content) {
		List<SseEmitter> userEmitters = emitters.get(userId);
		boolean isSuccess = false;

		if (userEmitters != null) {
			NotificationResponse response = new NotificationResponse(type, content, LocalDateTime.now());
			for (SseEmitter emitter : userEmitters) {
				try {
					emitter.send(SseEmitter.event().name("notification").data(response));
					isSuccess = true;  // 전송 성공 여부 체크
				} catch (IOException e) {
					log.error("알림 전송 실패: userId = {}, content = {}", userId, content, e);
					removeEmitter(userId, emitter);
					emitter.complete();
				}
			}
		}
		return isSuccess;
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
