package com.ssafy.ourdoc.domain.notification.service;

import static com.ssafy.ourdoc.global.common.enums.NotificationType.*;

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

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

	public static final long TIMEOUT = 30 * 60 * 1000L; // 연결 시간, 30분
	public static final int PERIOD = 60000; // 1분 간격 연결 유지
	private final NotificationHistoryService notificationHistoryService;
	@Getter
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

	// 학생이 알림전송
	public void sendNotifyStudentFromTeacher(User sender, NotificationType type) {
		String content = getStudentContent(sender, type);
		NotificationRecipient recipient = notificationHistoryService.saveNotifyStudent(sender, type, content);
		sendNotification(recipient);
	}

	// 교사가 알림전송
	public void sendNotifyTeacherFromStudent(User sender, Long studentClassId) {
		NotificationRecipient recipient = notificationHistoryService.saveNotifyTeacher(sender, studentClassId,
			"선생님이 칭찬 도장을 주셨어요!");

		sendNotification(recipient);
	}

	private void sendNotification(NotificationRecipient recipient) {
		sendToEmitters(recipient.getRecipient().getId(), recipient.getNotification());
	}

	private String getStudentContent(User sender, NotificationType type) {
		StringBuilder sb = new StringBuilder();
		String name = sender.getName();
		if (type.equals(가입)) {
			sb.append(name).append(" 학생 회원가입요청입니다.");
		} else if (type.equals(독서록)) {
			sb.append(name).append(" 학생이 독서록을 제출했습니다.");
		}
		return sb.toString();
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
			notification.getCreatedAt(),
			notification.getSender().getName()
		);

		for (SseEmitter emitter : userEmitters) {
			try {
				emitter.send(SseEmitter.event().name("알림: ").data(response));
			} catch (IOException e) {
				log.error("알림 전송 실패: recipientUserId = {}, content = {}", recipientUserId, notification.getContent(),
					e);
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
