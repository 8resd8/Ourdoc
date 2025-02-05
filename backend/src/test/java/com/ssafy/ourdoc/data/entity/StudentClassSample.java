package com.ssafy.ourdoc.data.entity;

import com.ssafy.ourdoc.domain.classroom.entity.ClassRoom;
import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.domain.user.student.entity.StudentClass;
import com.ssafy.ourdoc.global.common.enums.Active;
import com.ssafy.ourdoc.global.common.enums.AuthStatus;

public class StudentClassSample {

	private StudentClassSample() {
	}

	public static StudentClass studentClass(User user, ClassRoom classRoom) {
		return StudentClass.builder()
			.user(user)
			.classRoom(classRoom)
			.studentNumber(1)
			.active(Active.활성)
			.authStatus(AuthStatus.승인)
			.build();
	}
}
