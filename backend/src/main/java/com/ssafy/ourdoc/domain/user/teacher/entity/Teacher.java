package com.ssafy.ourdoc.domain.user.teacher.entity;

import java.time.LocalDateTime;

import com.ssafy.ourdoc.domain.classroom.entity.ClassRoom;
import com.ssafy.ourdoc.global.common.entity.BaseTimeEntity;
import com.ssafy.ourdoc.global.common.enums.EmploymentStatus;
import com.ssafy.ourdoc.domain.user.entity.User;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "teacher")
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
    @Enumerated(EnumType.STRING)
    private EmploymentStatus employmentStatus;

    @Column(name = "certificate_time", nullable = true)
    private LocalDateTime certificateTime;

    @Builder
    public Teacher(User user,
                   String email,
                   String phone,
                   EmploymentStatus employmentStatus,
                   LocalDateTime certificateTime) {
        this.user = user;
        this.email = email;
        this.phone = phone;
        this.employmentStatus = employmentStatus;
        this.certificateTime = certificateTime;
    }

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "class_id")
	private ClassRoom classRoom;
}
