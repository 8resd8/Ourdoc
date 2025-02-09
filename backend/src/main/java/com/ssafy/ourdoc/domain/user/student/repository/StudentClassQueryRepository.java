package com.ssafy.ourdoc.domain.user.student.repository;

import com.ssafy.ourdoc.global.common.enums.AuthStatus;

public interface StudentClassQueryRepository {
	long updateAuthStatus(Long userId, Long classId, AuthStatus newStatus);
}
