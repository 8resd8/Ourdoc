package com.ssafy.ourdoc.domain.notification.service;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.ssafy.ourdoc.domain.notification.dto.NotificationDto;
import com.ssafy.ourdoc.domain.notification.entity.Notification;
import com.ssafy.ourdoc.domain.notification.entity.NotificationRecipient;
import com.ssafy.ourdoc.domain.notification.exception.SubscribeException;
import com.ssafy.ourdoc.domain.user.entity.User;
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
	public SseEmitter subscribe(User user) {
		SseEmitter emitter = new SseEmitter(TIMEOUT);

		// 하트비트 설정
		Timer timer = new Timer(true);
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				try {
					emitter.send(SseEmitter.event().name("연결정보 갱신").data("ping"));
				} catch (IOException e) {
					timer.cancel();
					removeEmitter(user.getId(), emitter);
					emitter.complete();
				}
			}
		}, 0, PERIOD);

		emitter.onCompletion(() -> {
			timer.cancel();
			removeEmitter(user.getId(), emitter);
		});
		emitter.onTimeout(() -> {
			timer.cancel();
			removeEmitter(user.getId(), emitter);
		});
		emitter.onError((e) -> {
			timer.cancel();
			removeEmitter(user.getId(), emitter);
		});

		emitters.computeIfAbsent(user.getId(), key -> new CopyOnWriteArrayList<>()).add(emitter);
		return emitter;
	}

	// 알림 전송
	public void sendNotification(User sender, NotificationType type, String content) {
		NotificationRecipient recipient = notificationHistoryService.saveHistory(sender, type, content);

		sendToEmitters(recipient.getId(), recipient.getNotification());
	}

	private void sendToEmitters(Long recipientUserId, Notification notification) {
		List<SseEmitter> userEmitters = emitters.get(recipientUserId);

		if (userEmitters == null || userEmitters.isEmpty()) {
			throw new SubscribeException("구독을 먼저 해야 알림을 받을 수 있습니다.");
		}

		NotificationDto response = new NotificationDto(
			notification.getId(),
			notification.getNotificationType(),
			notification.getContent(),
			notification.getCreatedAt()
		);

		for (SseEmitter emitter : userEmitters) {
			try {
				emitter.send(SseEmitter.event().name("알림: ").data(response));
			} catch (IOException e) {
				log.error("알림 전송 실패: recipientUserId = {}, content = {}", recipientUserId, notification.getContent(), e);
				removeEmitter(recipientUserId, emitter);
				emitter.complete();
			}
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
