package com.ssafy.ourdoc.domain.user.entity;

import com.ssafy.ourdoc.global.common.entity.BaseTimeEntity;
import com.ssafy.ourdoc.global.common.enums.Active;
import com.ssafy.ourdoc.global.common.enums.Gender;
import com.ssafy.ourdoc.global.common.enums.UserType;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserType userType;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "login_id", nullable = false)
    private String loginId;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "birth", nullable = false)
    private Date birth;

    @Column(name = "gender", nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "profile_image_path")
    private String profileImagePath;

    @Column(name = "active", nullable = false)
    @Enumerated(EnumType.STRING)
    private Active active;

    @Builder
    public User(UserType userType,
                String name,
                String loginId,
                String password,
                Date birth,
                Gender gender,
                Active active) {
        this.userType = userType;
        this.name = name;
        this.loginId = loginId;
        this.password = password;
        this.birth = birth;
        this.gender = gender;
        this.active = active;
    }
}
