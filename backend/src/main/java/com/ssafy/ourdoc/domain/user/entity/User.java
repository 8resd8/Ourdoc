package com.ssafy.ourdoc.domain.user.entity;

import com.ssafy.ourdoc.global.common.entity.BaseTimeEntity;
import com.ssafy.ourdoc.global.common.enums.Active;
import com.ssafy.ourdoc.global.common.enums.Gender;
import com.ssafy.ourdoc.global.common.enums.UserType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.sql.Date;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long id;

    @Column(name = "user_type", nullable = false)
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
    private Gender gender;

    @Column(name = "profile_image_path", nullable = true)
    private String profileImagePath;

    @Column(name = "active", nullable = false)
    @ColumnDefault("활성")
    private Active active;

}
