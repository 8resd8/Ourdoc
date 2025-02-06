package com.ssafy.ourdoc.data.entity;

import com.ssafy.ourdoc.domain.classroom.entity.ClassRoom;
import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.domain.user.student.entity.Student;
import com.ssafy.ourdoc.global.common.enums.AuthStatus;

public class StudentSample {

	private StudentSample() {
	}

	public static Student student(User user, ClassRoom classRoom) {
		return Student.builder()
			.user(user)
			.classRoom(classRoom)
			.authStatus(AuthStatus.승인)
			.build();
	}
}
