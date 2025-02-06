package com.ssafy.ourdoc.domain.debate.entity;

import java.time.LocalDateTime;

import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.global.common.entity.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "room")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Room extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "room_id", unique = true, nullable = false)
	private Long id;

	@Column(name = "session_id")
	private String sessionId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@Column(name = "title", nullable = false)
	private String title;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "max_people", nullable = false)
	private int maxPeople;

	@Column(name = "current_people", nullable = false)
	private int currentPeople;

	@Column(name = "end_at")
	private LocalDateTime endAt;

	@Builder
	public Room(String sessionId, User user, String title, String password, int maxPeople, int currentPeople) {
		this.sessionId = sessionId;
		this.user = user;
		this.title = title;
		this.password = password;
		this.maxPeople = maxPeople;
		this.currentPeople = currentPeople;
	}

}
