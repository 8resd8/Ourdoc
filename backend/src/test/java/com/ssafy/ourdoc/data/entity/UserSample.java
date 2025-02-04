package com.ssafy.ourdoc.data.entity;

import java.sql.Date;

import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.global.common.enums.Active;
import com.ssafy.ourdoc.global.common.enums.Gender;
import com.ssafy.ourdoc.global.common.enums.UserType;

import groovy.transform.builder.Builder;

public class UserSample {

	private UserSample() {
	}

	public static User user(UserType type) {
		return User.builder()
			.active(Active.활성)
			.birth(new Date(System.currentTimeMillis()))
			.loginId("test")
			.password("testpwd")
			.userType(type)
			.name("테스트이름")
			.gender(Gender.남)
			.build();
	}

	public static User user(UserType type, String name) {
		return User.builder()
			.active(Active.활성)
			.birth(new Date(System.currentTimeMillis()))
			.loginId("test")
			.password("testpwd")
			.userType(type)
			.name(name)
			.gender(Gender.남)
			.build();
	}

	public static User user(UserType type, String loginId, String password) {
		return User.builder()
			.active(Active.활성)
			.birth(new Date(System.currentTimeMillis()))
			.loginId(loginId)
			.password(password)
			.userType(type)
			.name("테스트이름")
			.gender(Gender.남)
			.build();
	}

}
