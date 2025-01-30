package com.ssafy.ourdoc.domain.user.student.repository;

import com.ssafy.ourdoc.domain.user.student.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
    // 필요하다면 custom query 추가
}
