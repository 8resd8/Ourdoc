package com.ssafy.ourdoc.data.entity;

import java.time.Year;

import com.ssafy.ourdoc.domain.classroom.entity.ClassRoom;
import com.ssafy.ourdoc.domain.classroom.entity.School;

public class ClassRoomSample {

	private ClassRoomSample() {
	}

	public static ClassRoom classRoom(School school) {
		return ClassRoom.builder()
			.school(school)
			.classNumber(1)
			.grade(1)
			.year(Year.of(2025))
			.build();
	}

	public static ClassRoom classRoom(School school, int classNumber, int grade, int year) {
		return ClassRoom.builder()
			.school(school)
			.classNumber(classNumber)
			.grade(grade)
			.year(Year.of(year))
			.build();
	}

}
