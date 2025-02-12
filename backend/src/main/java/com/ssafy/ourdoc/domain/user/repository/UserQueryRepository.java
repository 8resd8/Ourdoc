package com.ssafy.ourdoc.domain.user.repository;

import com.ssafy.ourdoc.domain.user.entity.User;

public interface UserQueryRepository {
	User findTeachersByStudentClassId(Long studentUserId);

	String findPasswordById(Long userId);

	void updateProfileImage(User user, String profileImageUrl);
}
