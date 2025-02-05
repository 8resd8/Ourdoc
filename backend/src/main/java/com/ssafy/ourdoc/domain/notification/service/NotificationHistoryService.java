package com.ssafy.ourdoc.domain.notification.service;

import static com.ssafy.ourdoc.global.common.enums.UserType.*;

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
import com.ssafy.ourdoc.global.common.enums.UserType;
import com.ssafy.ourdoc.global.exception.ForbiddenException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class NotificationHistoryService {

	private final NotificationRepository notificationRepository;
	private final NotificationRecipientRepository notificationRecipientRepository;
	private final UserRepository userRepository;

	// 알림 DB 저장, 일단 학생 -> 담당교사만 보낼 수 있음.
	public Notification saveHistory(User sender, NotificationType type, String content) {
		User recipientUser = null;

		if (sender.getUserType().equals(학생)) {
			recipientUser = userRepository.findTeachersByStudentClassId(sender.getId());
		}

		if (!isValidNotification(sender, recipientUser)) {
			throw new ForbiddenException("허용되지 않은 알림 전송입니다.");
		}

		Notification notification = saveSender(sender, type, content);
		saveRecipient(recipientUser, notification);

		return notification;
	}

	// 전송자 저장
	private Notification saveSender(User sender, NotificationType type, String content) {
		Notification notification = Notification.builder()
			.notificationType(type)
			.content(content)
			.sender(sender)
			.build();
		return notificationRepository.save(notification);
	}

	// 수신자 저장
	private void saveRecipient(User recipient, Notification notification) {
		NotificationRecipient recipientEntity = NotificationRecipient.builder()
			.notification(notification)
			.recipient(recipient)
			.build();
		notificationRecipientRepository.save(recipientEntity);
	}

	public void markNotificationRead(User user, Long notificationId) {
		NotificationRecipient recipient = getRecipient(notificationId);

		recipient.markAsRead();
	}

	private boolean isValidNotification(User sender, User recipient) {
		if (sender.getUserType().equals(교사) && recipient.getUserType().equals(학생)) {
			return true;  // 교사 -> 학생 알림전송
		}
		if (sender.getUserType().equals(학생) && recipient.getUserType().equals(교사)) {
			return true;  // 학생 -> 교사 알림전송
		}

		return false;  // 기타 경우는 허용하지 않음
	}

	private NotificationRecipient getRecipient(Long notificationId) {
		return notificationRecipientRepository.findById(notificationId)
			.orElseThrow(() -> new NoSuchElementException("사용자의 알림을 찾을 수 없습니다."));
	}

}
