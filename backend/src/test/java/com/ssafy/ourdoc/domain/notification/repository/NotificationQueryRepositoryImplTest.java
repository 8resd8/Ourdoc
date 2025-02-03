package com.ssafy.ourdoc.domain.notification.repository;

import static com.ssafy.ourdoc.global.common.enums.NotificationType.*;
import static com.ssafy.ourdoc.global.common.enums.UserType.*;
import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import com.ssafy.ourdoc.data.entity.NotificationRecipientSample;
import com.ssafy.ourdoc.data.entity.NotificationSample;
import com.ssafy.ourdoc.data.entity.UserSample;
import com.ssafy.ourdoc.domain.notification.dto.NotificationDto;
import com.ssafy.ourdoc.domain.notification.entity.Notification;
import com.ssafy.ourdoc.domain.notification.entity.NotificationRecipient;
import com.ssafy.ourdoc.domain.notification.exception.SubscribeException;
import com.ssafy.ourdoc.domain.notification.service.NotificationService;
import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.domain.user.repository.UserRepository;

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
	private Notification notification;
	private NotificationRecipient recipient;

	@BeforeEach
	void setUp() {
		senderUser = UserSample.user(학생);
		userRepository.save(senderUser);

		recipientUser = UserSample.user(교사);
		userRepository.save(recipientUser);

		notification = NotificationSample.notification(senderUser, 가입, "독서록알림");
		notificationRepository.save(notification);

		recipient = NotificationRecipientSample.notificationRecipient(notification, recipientUser);
		notificationRecipientRepository.save(recipient);
	}

	@Test
	@DisplayName("구독없이 알림전송")
	void testFindAllUnreadNotificationsByUserId() {
		assertThatThrownBy(() -> notificationService.sendNotification(senderUser.getId(), 독서록, "독서록등록 테스트"))
			.isInstanceOf(SubscribeException.class);
	}

	@Test
	@DisplayName("미확인 알림 조회")
	void sendAndRetrieveUnreadNotifications() {
		List<NotificationDto> unreadNotifications = notificationQueryRepository.findAllByUserId(recipientUser.getId(),
			PageRequest.of(0, 10));

		assertThat(unreadNotifications).hasSize(1);
		assertThat(unreadNotifications.get(0).content()).isEqualTo("독서록알림");
		assertThat(unreadNotifications.get(0).type()).isEqualTo(가입);
	}

}
