package com.ssafy.ourdoc.domain.user.repository;

import com.ssafy.ourdoc.domain.user.entity.User;

public interface UserQueryRepository {
	Long findTeacherIdByStudentId(Long studentId);

	User findTeachersByStudentClassId(Long studentUserId);
}
