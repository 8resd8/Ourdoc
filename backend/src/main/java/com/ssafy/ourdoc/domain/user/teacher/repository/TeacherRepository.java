package com.ssafy.ourdoc.domain.user.teacher.repository;

import java.util.Optional;

import com.ssafy.ourdoc.domain.user.teacher.entity.Teacher;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {

	Optional<Teacher> findTeacherByUserId(Long userId);
}
