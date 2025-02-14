package com.ssafy.ourdoc.domain.notification.service;

import static com.ssafy.ourdoc.global.common.enums.NotificationType.*;
import static com.ssafy.ourdoc.global.common.enums.UserType.*;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.ourdoc.domain.notification.entity.Notification;
import com.ssafy.ourdoc.domain.notification.entity.NotificationRecipient;
import com.ssafy.ourdoc.domain.notification.repository.NotificationRecipientRepository;
import com.ssafy.ourdoc.domain.notification.repository.NotificationRepository;
import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.domain.user.repository.UserRepository;
import com.ssafy.ourdoc.domain.user.student.entity.StudentClass;
import com.ssafy.ourdoc.domain.user.student.repository.StudentClassRepository;
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
	private final StudentClassRepository studentClassRepository;

	// 학생 -> 담당교사
	public NotificationRecipient saveNotifyStudent(User studentUser, NotificationType type, String content) {
		if (studentUser.getUserType() != UserType.학생) {
			throw new ForbiddenException("학생만 알림을 보낼 수 있습니다.");
		}

		User recipientUser = userRepository.findTeachersByStudentClassId(studentUser.getId());

		validateUser(studentUser, recipientUser);

		Notification notification = saveSender(studentUser, type, content);
		return saveRecipient(recipientUser, notification);
	}

	// 교사 -> 학생
	public NotificationRecipient saveNotifyTeacher(User teacherUser, Long studentClassId, String content) {
		if (teacherUser.getUserType() != UserType.교사) {
			throw new IllegalArgumentException("교사만 알림을 보낼 수 있습니다.");
		}

		StudentClass studentClass = studentClassRepository.findById(studentClassId).get();
		User recipientUser = studentClass.getUser();

		validateUser(teacherUser, recipientUser); // 학생 -> 교사 검증

		Notification notification = saveSender(teacherUser, 독서록, content);

		return saveRecipient(recipientUser, notification);
	}

	private void validateUser(User sender, User recipientUser) {
		if (!isValidNotification(sender, recipientUser)) {
			throw new ForbiddenException("허용되지 않은 알림 전송입니다.");
		}
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
	private NotificationRecipient saveRecipient(User recipient, Notification notification) {
		NotificationRecipient recipientEntity = NotificationRecipient.builder()
			.notification(notification)
			.recipient(recipient)
			.build();
		return notificationRecipientRepository.save(recipientEntity);
	}

	public void markNotificationRead(User user, Long notificationId) {
		NotificationRecipient recipient = getRecipient(user.getId(), notificationId);

		recipient.markAsRead();
	}

	private boolean isValidNotification(User sender, User recipient) {
		if (sender.getUserType() == 교사 && recipient.getUserType() == 학생) {
			return true;  // 교사 -> 학생 알림전송
		}
		if (sender.getUserType() == 학생 && recipient.getUserType() == 교사) {
			return true;  // 학생 -> 교사 알림전송
		}

		return false;  // 기타 경우는 허용하지 않음
	}

	private NotificationRecipient getRecipient(Long userId, Long notificationId) {
		return notificationRecipientRepository.findByNotificationRecipient(userId, notificationId)
			.orElseThrow(() -> new NoSuchElementException("사용자의 알림을 찾을 수 없습니다."));
	}

}
