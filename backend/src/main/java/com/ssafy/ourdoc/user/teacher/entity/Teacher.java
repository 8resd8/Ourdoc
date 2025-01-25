package com.ssafy.ourdoc.user.teacher.entity;

import com.ssafy.ourdoc.global.entity.BaseTimeEntity;
import com.ssafy.ourdoc.global.enums.EmploymentStatus;
import com.ssafy.ourdoc.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Teacher extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
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
}
