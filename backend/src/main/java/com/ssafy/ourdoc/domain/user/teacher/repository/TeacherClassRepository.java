package com.ssafy.ourdoc.domain.user.teacher.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.ourdoc.domain.user.teacher.entity.TeacherClass;

public interface TeacherClassRepository extends JpaRepository<TeacherClass, Long> {
	Optional<TeacherClass> findByUserIdAndClassRoomId(Long userId, Long classRoomId);
}
