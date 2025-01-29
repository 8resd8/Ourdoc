package com.ssafy.ourdoc.user.teacher.repository;

import com.ssafy.ourdoc.user.teacher.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    // 필요하다면 custom query 추가
}
