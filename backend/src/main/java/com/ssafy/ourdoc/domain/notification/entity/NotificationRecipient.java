package com.ssafy.ourdoc.domain.notification.entity;

import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.global.common.entity.BaseTimeEntity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(
	name = "notification_recipient"
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NotificationRecipient extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "notification_recipient_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "notification_id", nullable = false)
	private Notification notification;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "recipient_user_id", nullable = false)
	private User recipient;

	@Column(name = "read_time")
	private LocalDateTime readTime;

	@Builder
	public NotificationRecipient(Notification notification, User recipient, LocalDateTime readTime) {
		this.notification = notification;
		this.recipient = recipient;
		this.readTime = readTime;
	}
}
