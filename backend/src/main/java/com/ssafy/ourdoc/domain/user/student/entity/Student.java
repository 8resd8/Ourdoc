package com.ssafy.ourdoc.domain.user.student.entity;

import java.time.LocalDateTime;

import com.ssafy.ourdoc.domain.classroom.entity.ClassRoom;
import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.global.common.entity.BaseTimeEntity;
import com.ssafy.ourdoc.global.common.enums.AuthStatus;
import com.ssafy.ourdoc.global.common.enums.TempPassword;

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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "student")
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
	@Enumerated(EnumType.STRING)
	private TempPassword tempPassword;

	@Column(name = "auth_status", nullable = true)
	@Enumerated(EnumType.STRING)
	private AuthStatus authStatus;

	@Column(name = "certificate_time", nullable = true)
	private LocalDateTime certificateTime;

	@Builder
	public Student(User user, ClassRoom classRoom, TempPassword tempPassword, AuthStatus authStatus,
		LocalDateTime certificateTime) {
		this.user = user;
		this.classRoom = classRoom;
		this.tempPassword = tempPassword;
		this.authStatus = authStatus;
		this.certificateTime = certificateTime;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "class_id")
	private ClassRoom classRoom;
}
