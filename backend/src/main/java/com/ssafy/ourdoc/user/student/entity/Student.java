package com.ssafy.ourdoc.user.student.entity;

import java.time.LocalDateTime;

import com.ssafy.ourdoc.classroom.entity.ClassRoom;
import com.ssafy.ourdoc.global.entity.BaseTimeEntity;
import com.ssafy.ourdoc.global.enums.AuthStatus;
import com.ssafy.ourdoc.user.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Student extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "student_id", nullable = false)
	private Long id;

	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;

	@Column(name = "temp_password", nullable = true)
	private String tempPassword;

	@Column(name = "auth_status", nullable = true)
	private AuthStatus authStatus;

	@Column(name = "certificate_time", nullable = true)
	private LocalDateTime certificateTime;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "class_id")
	private ClassRoom classRoom;
}
