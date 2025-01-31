package com.ssafy.ourdoc.domain.notification.entity;

import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.global.common.entity.BaseTimeEntity;
import com.ssafy.ourdoc.global.common.enums.NotificationType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "notification_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sender_user_id", nullable = false)
	private User sender;

	@Enumerated(EnumType.STRING)
	@Column(name = "notification_type", columnDefinition = "enum('가입','독서록') default '가입'")
	private NotificationType notificationType;

	@Column(name = "content")
	private String content;

	@Builder
	public Notification(User sender, NotificationType notificationType, String content) {
		this.sender = sender;
		this.notificationType = notificationType;
		this.content = content;
	}
}
