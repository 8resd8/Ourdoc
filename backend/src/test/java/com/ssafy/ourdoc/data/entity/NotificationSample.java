package com.ssafy.ourdoc.data.entity;

import com.ssafy.ourdoc.domain.notification.entity.Notification;
import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.global.common.enums.NotificationType;

public class NotificationSample {

	private NotificationSample() {
	}

	public static Notification notification(User senderUser, NotificationType type, String content) {
		return Notification.builder()
			.sender(senderUser)
			.content(content)
			.notificationType(type)
			.build();
	}

	public static Notification notification(User senderUser, NotificationType type) {
		return Notification.builder()
			.sender(senderUser)
			.content(type + "test")
			.notificationType(type)
			.build();
	}

	public static Notification notification(User senderUser) {
		return Notification.builder()
			.sender(senderUser)
			.content("알림 test")
			.notificationType(NotificationType.독서록)
			.build();
	}

}
