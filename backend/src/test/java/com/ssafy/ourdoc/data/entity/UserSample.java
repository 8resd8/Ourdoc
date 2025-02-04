package com.ssafy.ourdoc.data.entity;

import java.sql.Date;

import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.global.common.enums.Active;
import com.ssafy.ourdoc.global.common.enums.Gender;
import com.ssafy.ourdoc.global.common.enums.UserType;

public class UserSample {

	private UserSample() {
	}

	private static String defaultName = "테스트이름";
	private static String defaultLoginId = "test";
	private static String defaultPassword = "test1234";
	private static Active defaultActive = Active.활성;
	private static Gender defaultGender = Gender.남;
	private static UserType defaultUserType = UserType.학생;
	private static Date defaultBirthday = new Date(2000, 1, 18);

	public static User user(UserType type) {
		return User.builder()
			.active(defaultActive)
			.birth(defaultBirthday)
			.loginId(defaultLoginId)
			.password(defaultPassword)
			.userType(type)
			.name(defaultName)
			.gender(defaultGender)
			.build();
	}

	public static User user(UserType type, String name) {
		return User.builder()
			.active(defaultActive)
			.birth(defaultBirthday)
			.loginId(defaultLoginId)
			.password(defaultLoginId)
			.userType(type)
			.name(name)
			.gender(defaultGender)
			.build();
	}

	public static User user(UserType type, String loginId, String password) {
		return User.builder()
			.active(defaultActive)
			.birth(defaultBirthday)
			.loginId(loginId)
			.password(password)
			.userType(type)
			.name(defaultName)
			.gender(defaultGender)
			.build();
	}

	public static User user() {
		return User.builder()
			.active(defaultActive)
			.birth(defaultBirthday)
			.loginId(defaultLoginId)
			.password(defaultPassword)
			.userType(defaultUserType)
			.name(defaultName)
			.gender(defaultGender)
			.build();
	}

}
