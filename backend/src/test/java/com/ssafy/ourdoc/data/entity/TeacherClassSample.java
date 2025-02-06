package com.ssafy.ourdoc.data.entity;

import com.ssafy.ourdoc.domain.classroom.entity.ClassRoom;
import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.domain.user.teacher.entity.TeacherClass;
import com.ssafy.ourdoc.global.common.enums.Active;

public class TeacherClassSample {

	private TeacherClassSample() {
	}

	public static TeacherClass teacherClass(User user, ClassRoom classRoom) {
		return TeacherClass.builder()
			.user(user)
			.classRoom(classRoom)
			.active(Active.활성)
			.build();
	}
}
