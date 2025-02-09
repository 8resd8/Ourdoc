package com.ssafy.ourdoc.domain.user.student.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ssafy.ourdoc.domain.user.teacher.dto.StudentPendingProfileDto;
import com.ssafy.ourdoc.global.common.enums.Active;
import com.ssafy.ourdoc.global.common.enums.AuthStatus;

public interface StudentClassQueryRepository {
	long updateAuthStatusOfStudentClass(Long userId, Long classId, AuthStatus newStatus);

	long updateAuthStatusOfStudent(Long id, AuthStatus authStatus);

	Page<StudentPendingProfileDto> findStudentsByClassIdAndActiveAndAuthStatus(Long classId, Active active, AuthStatus authStatus, Pageable pageable);
}
