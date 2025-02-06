package com.ssafy.ourdoc.data.entity;

import com.ssafy.ourdoc.domain.classroom.entity.School;

public class SchoolSample {

	private SchoolSample() {
	}

	private final static String defaultName = "테스트이름";
	private final static String defaultAddress = "테스트주소";


	public static School school(String name, String address) {
		return School.builder()
			.schoolName(name)
			.address(address)
			.build();
	}

	public static School school(String name) {
		return School.builder()
			.schoolName(name)
			.address(defaultAddress)
			.build();
	}

	public static School school() {
		return School.builder()
			.schoolName(defaultName)
			.address(defaultAddress)
			.build();
	}


}
