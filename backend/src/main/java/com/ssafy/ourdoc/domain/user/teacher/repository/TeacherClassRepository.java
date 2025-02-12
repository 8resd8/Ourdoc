package com.ssafy.ourdoc.domain.user.teacher.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.domain.user.teacher.entity.TeacherClass;
import com.ssafy.ourdoc.global.common.enums.Active;

public interface TeacherClassRepository extends JpaRepository<TeacherClass, Long>, TeacherClassQueryRepository {
	Optional<TeacherClass> findByUserIdAndClassRoomId(Long userId, Long classRoomId);

	Optional<TeacherClass> findByUserIdAndActive(Long userId, Active active);

	TeacherClass findClassRoomByUserAndActive(User user, Active active);
}
