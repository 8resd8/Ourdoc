package com.ssafy.ourdoc.data.entity;

import com.ssafy.ourdoc.domain.notification.entity.Notification;
import com.ssafy.ourdoc.domain.notification.entity.NotificationRecipient;
import com.ssafy.ourdoc.domain.user.entity.User;

public class NotificationRecipientSample {

	private NotificationRecipientSample() {
	}

	public static NotificationRecipient notificationRecipient(Notification notification, User recipientUser) {
		return NotificationRecipient.builder()
			.notification(notification)
			.recipient(recipientUser)
			.build();
	}

}
