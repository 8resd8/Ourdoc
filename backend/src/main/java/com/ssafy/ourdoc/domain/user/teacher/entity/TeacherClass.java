package com.ssafy.ourdoc.domain.user.teacher.entity;

import java.time.LocalDateTime;

import com.ssafy.ourdoc.domain.classroom.entity.ClassRoom;
import com.ssafy.ourdoc.global.common.entity.BaseTimeEntity;
import com.ssafy.ourdoc.global.common.enums.Active;

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
@Table(name = "teacher_class")
public class TeacherClass extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "teacher_class_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private Teacher teacher;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "class_id", nullable = false)
	private ClassRoom classRoom;

	@Column(name = "active", nullable = false)
	@Enumerated(EnumType.STRING)
	private Active active;

	@Column(name = "work_start_time")
	private LocalDateTime workStartTime;

	@Column(name = "work_end_time")
	private LocalDateTime workEndTime;

	@Builder
	public TeacherClass(Teacher teacher, ClassRoom classRoom, Active active) {
		this.teacher = teacher;
		this.classRoom = classRoom;
		this.active = active;
	}
}
