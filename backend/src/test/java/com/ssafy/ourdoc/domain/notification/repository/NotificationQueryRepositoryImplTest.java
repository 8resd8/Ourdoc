package com.ssafy.ourdoc.domain.notification.repository;

import static com.ssafy.ourdoc.global.common.enums.NotificationType.*;
import static com.ssafy.ourdoc.global.common.enums.UserType.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.ssafy.ourdoc.data.entity.NotificationRecipientSample;
import com.ssafy.ourdoc.data.entity.NotificationSample;
import com.ssafy.ourdoc.data.entity.UserSample;
import com.ssafy.ourdoc.domain.notification.dto.NotificationConditionRequest;
import com.ssafy.ourdoc.domain.notification.dto.NotificationDetailDto;
import com.ssafy.ourdoc.domain.notification.dto.NotificationDto;
import com.ssafy.ourdoc.domain.notification.entity.Notification;
import com.ssafy.ourdoc.domain.notification.entity.NotificationRecipient;
import com.ssafy.ourdoc.domain.notification.exception.SubscribeException;
import com.ssafy.ourdoc.domain.notification.service.NotificationService;
import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.domain.user.repository.UserRepository;
import com.ssafy.ourdoc.global.common.enums.NotificationStatus;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
public class NotificationQueryRepositoryImplTest {

	@Autowired
	private NotificationQueryRepository notificationQueryRepository;
	@Autowired
	private NotificationRepository notificationRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private NotificationRecipientRepository notificationRecipientRepository;
	@Autowired
	private NotificationService notificationService;

	private User senderUser;
	private User recipientUser;
	private Notification notification, notification2;
	private NotificationRecipient recipient;

	@BeforeEach
	void setUp() {
		senderUser = UserSample.user(학생, "이름멈춰");
		userRepository.save(senderUser);

		recipientUser = UserSample.user(교사);
		userRepository.save(recipientUser);
	}

	@Test
	@DisplayName("구독없이 알림전송")
	void testFindAllUnreadNotificationsByUserId() {
		assertThatThrownBy(
			() -> notificationService.sendNotification(senderUser, 독서록, "독서록등록 테스트")).isInstanceOf(
			SubscribeException.class);
	}

	@Test
	void 안읽은독서록알림조회() {
		notification = NotificationSample.notification(senderUser, 독서록, "독서록알림");
		notificationRepository.save(notification);

		notification2 = NotificationSample.notification(senderUser, 가입, "가입알림");
		notificationRepository.save(notification);

		recipient = NotificationRecipientSample.notificationRecipient(notification, recipientUser);
		notificationRecipientRepository.save(recipient);

		recipient = NotificationRecipientSample.notificationRecipient(notification2, recipientUser);

		NotificationConditionRequest request = new NotificationConditionRequest(NotificationStatus.안읽음, 독서록);
		Page<NotificationDto> unreadNotifications = notificationQueryRepository.findAllConditionByUserId(
			recipientUser.getId(), request, PageRequest.of(0, 10));

		assertThat(unreadNotifications.getTotalElements()).isEqualTo(1);
		assertThat(unreadNotifications.getContent()).extracting(NotificationDto::content).containsExactly("독서록알림");
		assertThat(unreadNotifications.getContent()).extracting(NotificationDto::type).containsExactly(독서록);
	}

	@Test
	void 안읽은가입알림조회() {
		notification = NotificationSample.notification(senderUser, 가입, "가입알림");
		notificationRepository.save(notification);

		recipient = NotificationRecipientSample.notificationRecipient(notification, recipientUser);
		notificationRecipientRepository.save(recipient);

		NotificationConditionRequest request = new NotificationConditionRequest(NotificationStatus.안읽음, 가입);
		Page<NotificationDto> unreadNotifications = notificationQueryRepository.findAllConditionByUserId(
			recipientUser.getId(), request, PageRequest.of(0, 10));

		assertThat(unreadNotifications.getTotalElements()).isEqualTo(1);
		assertThat(unreadNotifications.getContent()).extracting(NotificationDto::content).containsExactly("가입알림");
		assertThat(unreadNotifications.getContent()).extracting(NotificationDto::type).containsExactly(가입);
	}

	@Test
	void 안읽은알림_모두조회() {
		Notification notification1 = NotificationSample.notification(senderUser, 가입, "가입알림");
		notificationRepository.save(notification1);

		Notification notification2 = NotificationSample.notification(senderUser, 가입, "가입알림");
		notificationRepository.save(notification2);

		NotificationRecipient recipient1 = NotificationRecipientSample.notificationRecipient(notification1,
			recipientUser);

		NotificationRecipient recipient2 = NotificationRecipientSample.notificationRecipient(notification2,
			recipientUser);

		notificationRecipientRepository.save(recipient1);
		notificationRecipientRepository.save(recipient2);

		NotificationConditionRequest request = new NotificationConditionRequest(NotificationStatus.안읽음, null);
		Page<NotificationDto> unreadNotifications = notificationQueryRepository.findAllConditionByUserId(
			recipientUser.getId(), request, PageRequest.of(0, 10));

		assertThat(unreadNotifications.getContent().size()).isEqualTo(2);

		// 조건이 없으면 읽지 않은알림 모두 조회
		NotificationConditionRequest request2 = new NotificationConditionRequest(null, null);
		Page<NotificationDto> unreadNotifications2 = notificationQueryRepository.findAllConditionByUserId(
			recipientUser.getId(), request2, PageRequest.of(0, 10));

		assertThat(unreadNotifications2.getContent().size()).isEqualTo(2);
	}

	@Test
	@DisplayName("단일알림 상세 조회")
	void findByNotificationIdTest() {
		// 학생이 교사에게 알림 보내기
		Notification notification = NotificationSample.notification(senderUser, 가입, "가입알림");
		notificationRepository.save(notification);

		NotificationRecipient recipient = NotificationRecipientSample.notificationRecipient(notification,
			recipientUser);
		notificationRecipientRepository.save(recipient);

		NotificationDetailDto findNotification = notificationQueryRepository.
			findByNotificationId(recipient.getRecipient().getId(), notification.getId());

		assertThat(findNotification).isNotNull();
		assertThat(findNotification.content()).isEqualTo("가입알림");
		assertThat(findNotification.type()).isEqualTo(가입);
		assertThat(notification.getSender()).isEqualTo(senderUser);
		assertThat(notification.getSender().getName()).isEqualTo(findNotification.senderName());
	}

}
