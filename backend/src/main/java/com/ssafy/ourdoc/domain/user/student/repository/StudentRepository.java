package com.ssafy.ourdoc.domain.user.student.repository;

import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.domain.user.student.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
	Student findByUser(User user);
}
