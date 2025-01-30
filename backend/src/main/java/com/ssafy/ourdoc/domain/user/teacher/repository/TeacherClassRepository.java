package com.ssafy.ourdoc.domain.user.teacher.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.ourdoc.domain.user.teacher.entity.TeacherClass;

public interface TeacherClassRepository extends JpaRepository<TeacherClass, Long> {
}
