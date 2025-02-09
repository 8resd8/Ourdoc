package com.ssafy.ourdoc.domain.user.student.repository;

import com.ssafy.ourdoc.global.common.enums.AuthStatus;

public interface StudentClassQueryRepository {
	long updateAuthStatusOfStudentClass(Long userId, Long classId, AuthStatus newStatus);

	long updateAuthStatusOfStudent(Long id, AuthStatus authStatus);
}
