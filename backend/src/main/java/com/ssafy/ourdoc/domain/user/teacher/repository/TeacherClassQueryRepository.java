package com.ssafy.ourdoc.domain.user.teacher.repository;

import java.util.Optional;

import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.domain.user.teacher.entity.TeacherClass;

public interface TeacherClassQueryRepository {
	void updateUpdatedAt(Long userId, Long classId);

	Optional<TeacherClass> findLatestClass(User user);
}
