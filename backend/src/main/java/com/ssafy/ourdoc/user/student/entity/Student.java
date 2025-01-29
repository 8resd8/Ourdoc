package com.ssafy.ourdoc.user.student.entity;

import com.ssafy.ourdoc.global.entity.BaseTimeEntity;
import com.ssafy.ourdoc.global.enums.AuthStatus;
import com.ssafy.ourdoc.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "student")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Student extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id", nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "temp_password", nullable = true)
    private String tempPassword;

    @Column(name = "auth_status", nullable = true)
    @Enumerated(EnumType.STRING)
    private AuthStatus authStatus;

    @Column(name = "certificate_time", nullable = true)
    private LocalDateTime certificateTime;

    @Builder
    public Student(User user, String tempPassword, AuthStatus authStatus, LocalDateTime certificateTime) {
        this.user = user;
        this.tempPassword = tempPassword;
        this.authStatus = authStatus;
        this.certificateTime = certificateTime;
    }

}
