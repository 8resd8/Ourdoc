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
import com.ssafy.ourdoc.global.exception.ForbiddenException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class NotificationHistoryService {

	private final NotificationRepository notificationRepository;
	private final NotificationRecipientRepository notificationRecipientRepository;
	private final UserRepository userRepository;

	// 알림 DB 저장
	public Notification saveHistory(Long senderId, NotificationType type, String content) {
		User sender = getUser(senderId);
		// 일단 학생 -> 교사 단방향 알림 보내기 (독서록 작성 시 알림)
		// Long teacherId = userRepository.findTeacherIdByStudentId(senderId);  // 담당 교사 ID 조회
		Long teacherId = 9L; // 임시 하드코딩, 학급이 없음
		User recipient = getUser(teacherId); // 알림 수신자는 본인의 담당교사, class -> teacher_class한 userId

		if (!isValidNotification(sender, recipient)) {
			throw new ForbiddenException("허용되지 않은 알림 전송입니다.");
		}

		Notification notification = saveSender(sender, type, content);
		saveRecipient(recipient, notification);

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

	// 알림 읽음 기록, 누른 알림의 PK ID필요
	public void markNotificationRead(Long notificationId) {
		Long userId = 9L; // 현재 로그인 한 사용자 정보 토큰에서 가져오기.

		NotificationRecipient recipient = getRecipient(notificationId);

		if (!recipient.getRecipient().getId().equals(userId)) {
			throw new ForbiddenException("해당 알림을 읽을 권한이 없습니다.");
		}

		recipient.markAsRead(LocalDateTime.now());
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

	private User getUser(Long senderId) {
		return userRepository.findById(senderId)
			.orElseThrow(() -> new NoSuchElementException("사용자를 찾을 수 없습니다. senderId: " + senderId));
	}

	private NotificationRecipient getRecipient(Long notificationId) {
		return notificationRecipientRepository.findById(notificationId)
			.orElseThrow(() -> new NoSuchElementException("사용자의 알림을 찾을 수 없습니다."));
	}

}
