package com.ssafy.ourdoc.domain.notification.service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.ourdoc.domain.notification.entity.Notification;
import com.ssafy.ourdoc.domain.notification.entity.NotificationRecipient;
import com.ssafy.ourdoc.domain.notification.repository.NotificationRecipientRepository;
import com.ssafy.ourdoc.domain.notification.repository.NotificationRepository;
import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.domain.user.repository.UserRepository;
import com.ssafy.ourdoc.global.common.enums.NotificationType;
import com.ssafy.ourdoc.global.exception.ForbiddenException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationHistoryService {

	private final NotificationRepository notificationRepository;
	private final NotificationRecipientRepository notificationRecipientRepository;
	private final UserRepository userRepository;

	// 알림 DB 저장
	@Transactional
	public void saveHistory(Long userId, NotificationType type, String content) {
		Notification notification = saveSender(userId, type, content);
		saveRecipient(userId, notification);
	}

	// 전송자 저장
	private Notification saveSender(Long userId, NotificationType type, String content) {
		User sender = getUser(userId);
		Notification notification = Notification.builder()
			.notificationType(type)
			.content(content)
			.sender(sender)
			.build();
		return notificationRepository.save(notification);
	}

	// 수신자 저장
	private void saveRecipient(Long userId, Notification notification) {
		User recipient = getUser(userId);
		NotificationRecipient recipientEntity = NotificationRecipient.builder()
			.notification(notification)
			.recipient(recipient)
			.build();
		notificationRecipientRepository.save(recipientEntity);
	}

	// 알림 읽음 기록
	public void markNotificationRead(Long recipientUserId) {
		Long userId = 8L; // 현재 로그인 한 사용자 정보 토큰에서 가져오기.

		NotificationRecipient recipient = getRecipient(recipientUserId);

		if (!recipient.getRecipient().getId().equals(userId)) {
			throw new ForbiddenException("해당 알림을 읽을 권한이 없습니다.");
		}

		recipient.markAsRead(LocalDateTime.now());
	}

	private User getUser(Long userId) {
		return userRepository.findById(userId)
			.orElseThrow(() -> new NoSuchElementException("사용자를 찾을 수 없습니다. userId: " + userId));
	}

	private NotificationRecipient getRecipient(Long recipientUserId) {
		return notificationRecipientRepository.findById(recipientUserId)
			.orElseThrow(
				() -> new NoSuchElementException("해당 사용자의 알림을 찾을 수 없습니다. recipientUserId: " + recipientUserId));
	}

}
