package com.ssafy.ourdoc.domain.award.entity;

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
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Award extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "award_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@Column(name = "image_path", nullable = false)
	private String imagePath;

	@Column(name = "title", nullable = false)
	private String title;

	@Builder
	public Award(User user, String imagePath, String title) {
		this.user = user;
		this.imagePath = imagePath;
		this.title = title;
	}
}
