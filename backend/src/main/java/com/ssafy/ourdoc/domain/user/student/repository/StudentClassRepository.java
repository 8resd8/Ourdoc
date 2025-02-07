package com.ssafy.ourdoc.domain.user.student.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.ourdoc.domain.classroom.entity.ClassRoom;
import com.ssafy.ourdoc.domain.user.student.entity.StudentClass;
import com.ssafy.ourdoc.global.common.enums.Active;

public interface StudentClassRepository extends JpaRepository<StudentClass, Long> {
	Optional<StudentClass> findStudentClassByUserId(Long userId);

	StudentClass findByUserIdAndActive(Long userId, Active active);

	int countByClassRoom(ClassRoom userClassRoom);
}
