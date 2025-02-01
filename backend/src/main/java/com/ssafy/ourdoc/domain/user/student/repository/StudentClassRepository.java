package com.ssafy.ourdoc.domain.user.student.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.ourdoc.domain.user.student.entity.StudentClass;

public interface StudentClassRepository extends JpaRepository<StudentClass, Long> {

}
