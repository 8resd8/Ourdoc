package com.ssafy.ourdoc.domain.user.repository;

public interface UserQueryRepository {
	Long findTeacherIdByStudentId(Long studentId);
}
