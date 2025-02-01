package com.ssafy.ourdoc.domain.user.student.entity;

import java.time.LocalDateTime;

import com.ssafy.ourdoc.domain.classroom.entity.ClassRoom;
import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.global.common.entity.BaseTimeEntity;
import com.ssafy.ourdoc.global.common.enums.Active;
import com.ssafy.ourdoc.global.common.enums.AuthStatus;

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
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "student_class")
public class StudentClass extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "student_class_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "class_id", nullable = false)
	private ClassRoom classRoom;

	@Column(name = "student_number")
	private int studentNumber;

	@Column(name = "active", nullable = false)
	@Enumerated(EnumType.STRING)
	private Active active;

	@Column(name = "auth_status")
	@Enumerated(EnumType.STRING)
	private AuthStatus authStatus;

	@Column(name = "certificate_time")
	private LocalDateTime certificateTime;

	@Builder
	public StudentClass(User user, ClassRoom classRoom, int studentNumber, Active active, AuthStatus authStatus) {
		this.user = user;
		this.classRoom = classRoom;
		this.studentNumber = studentNumber;
		this.active = active;
		this.authStatus = authStatus;
	}
}
