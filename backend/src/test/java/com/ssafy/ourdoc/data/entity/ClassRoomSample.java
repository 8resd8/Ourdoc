package com.ssafy.ourdoc.data.entity;

import java.time.Year;

import com.ssafy.ourdoc.domain.classroom.entity.ClassRoom;
import com.ssafy.ourdoc.domain.classroom.entity.School;

public class ClassRoomSample {

	private ClassRoomSample() {
	}

	private final static String defaultName = "테스트이름";
	private final static String defaultAddress = "테스트주소";

	public static ClassRoom classRoom(School school) {
		return ClassRoom.builder()
			.school(school)
			.classNumber(1)
			.grade(1)
			.year(Year.of(2025))
			.build();
	}

}
