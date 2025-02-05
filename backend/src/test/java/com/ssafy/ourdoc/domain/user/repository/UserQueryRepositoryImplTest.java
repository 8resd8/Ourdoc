package com.ssafy.ourdoc.domain.user.repository;

import static com.ssafy.ourdoc.global.common.enums.UserType.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.ourdoc.data.entity.ClassRoomSample;
import com.ssafy.ourdoc.data.entity.SchoolSample;
import com.ssafy.ourdoc.data.entity.StudentClassSample;
import com.ssafy.ourdoc.data.entity.StudentSample;
import com.ssafy.ourdoc.data.entity.TeacherClassSample;
import com.ssafy.ourdoc.data.entity.UserSample;
import com.ssafy.ourdoc.domain.classroom.entity.ClassRoom;
import com.ssafy.ourdoc.domain.classroom.entity.School;
import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.domain.user.student.entity.Student;
import com.ssafy.ourdoc.domain.user.student.entity.StudentClass;
import com.ssafy.ourdoc.domain.user.teacher.entity.TeacherClass;

import jakarta.persistence.EntityManager;

@SpringBootTest
@Transactional
class UserQueryRepositoryImplTest {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private EntityManager em;

	private User studentUser;
	private User teacherUser;
	private Student student;
	private StudentClass studentClass;
	private ClassRoom classRoom;
	private School school;
	private TeacherClass teacherClass;

	@BeforeEach
	void setUp() {
		// 0. School 생성
		school = SchoolSample.school();
		em.persist(school);

		// 1. ClassRoom 생성
		classRoom = ClassRoomSample.classRoom(school);
		em.persist(classRoom);

		// 2. 학생 생성 및 ClassRoom에 할당
		studentUser = UserSample.user(학생, "학생입니다");
		teacherUser = UserSample.user(교사, "교사입니다");
		em.persist(studentUser);
		em.persist(teacherUser);

		student = StudentSample.student(studentUser, classRoom);
		em.persist(student);

		studentClass = StudentClassSample.studentClass(studentUser, classRoom);
		teacherClass = TeacherClassSample.teacherClass(teacherUser, classRoom);
		em.persist(studentClass);
		em.persist(teacherClass);

		em.flush();
		em.clear();
	}

	@Test
	void 학급학생_담당교사찾기() {
		// given
		Long studentUserId = studentUser.getId();

		// when
		User result = userRepository.findTeachersByStudentClassId(studentUserId);

		// then
		assertThat(result).isNotNull();
		assertThat(result.getName()).isEqualTo("교사입니다");
	}

}

