package com.ssafy.ourdoc.user.teacher.entity;

import java.time.LocalDateTime;

import com.ssafy.ourdoc.classroom.entity.ClassRoom;
import com.ssafy.ourdoc.global.entity.BaseTimeEntity;
import com.ssafy.ourdoc.global.enums.EmploymentStatus;
import com.ssafy.ourdoc.user.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
public class Teacher extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "teacher_id", nullable = false)
	private Long id;

	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;

	@Column(name = "email", nullable = false)
	private String email;

	@Column(name = "phone", nullable = false)
	private String phone;

	@Column(name = "employment_status", nullable = true)
	private EmploymentStatus employmentStatus;

	@Column(name = "certificate_time", nullable = true)
	private LocalDateTime certificateTime;

	@ManyToOne
	@JoinColumn(name = "class_id")
	private ClassRoom classRoom;
}
