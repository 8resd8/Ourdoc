package com.ssafy.ourdoc.domain.user.student.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.ourdoc.domain.classroom.entity.ClassRoom;
import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.domain.user.student.entity.StudentClass;
import com.ssafy.ourdoc.global.common.enums.Active;
import com.ssafy.ourdoc.global.common.enums.AuthStatus;

public interface StudentClassRepository extends JpaRepository<StudentClass, Long> {
	Optional<StudentClass> findStudentClassByUserId(Long userId);

	StudentClass findByUserAndClassRoom(User user, ClassRoom classRoom);

	Optional<StudentClass> findByUserIdAndActive(Long userId, Active active);

	int countByClassRoom(ClassRoom userClassRoom);

	Optional<StudentClass> findByUserIdAndClassRoomIdAndAuthStatus(Long id, Long classId, AuthStatus authStatus);
}
