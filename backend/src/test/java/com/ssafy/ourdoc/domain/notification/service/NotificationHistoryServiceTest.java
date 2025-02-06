package com.ssafy.ourdoc.domain.notification.service;

import static com.ssafy.ourdoc.global.common.enums.NotificationType.*;
import static com.ssafy.ourdoc.global.common.enums.UserType.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ssafy.ourdoc.data.entity.ClassRoomSample;
import com.ssafy.ourdoc.data.entity.SchoolSample;
import com.ssafy.ourdoc.data.entity.StudentClassSample;
import com.ssafy.ourdoc.data.entity.TeacherClassSample;
import com.ssafy.ourdoc.data.entity.UserSample;
import com.ssafy.ourdoc.domain.classroom.entity.ClassRoom;
import com.ssafy.ourdoc.domain.classroom.entity.School;
import com.ssafy.ourdoc.domain.notification.entity.NotificationRecipient;
import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.domain.user.student.entity.StudentClass;
import com.ssafy.ourdoc.domain.user.teacher.entity.TeacherClass;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
class NotificationHistoryServiceTest {

	@Autowired
	private NotificationHistoryService notificationHistoryService;

	@Autowired
	private EntityManager em;

	private User teacher;
	private User student;

	private TeacherClass teacherClass;
	private StudentClass studentClass;

	private School school;
	private ClassRoom classRoom;

	@BeforeEach
	void setUp() {
		teacher = UserSample.user(교사);
		student = UserSample.user(학생);
		em.persist(teacher);
		em.persist(student);

		school = SchoolSample.school();
		em.persist(school);
		classRoom = ClassRoomSample.classRoom(school);
		em.persist(classRoom);

		teacherClass = TeacherClassSample.teacherClass(teacher, classRoom);
		studentClass = StudentClassSample.studentClass(student, classRoom);
		em.persist(studentClass);
		em.persist(teacherClass);

		em.flush();
		em.clear();
	}

	@Test
	@DisplayName("학생 -> 교사 알림 후 저장")
	void studentToTeacher() {
		NotificationRecipient recipient = notificationHistoryService.saveNotifyStudent(student, 독서록, "선생님 독서록작성했습니다.");

		assertThat(recipient).isNotNull();
		assertThat(recipient.getRecipient().getUserType()).isEqualTo(교사);
	}

	@Test
	@DisplayName("교사 -> 학생 알림 후 저장")
	void teacherToStudent() {
		NotificationRecipient recipient = notificationHistoryService.saveNotifyTeacher(teacher, studentClass.getId(),
			"칭찬도장 찍음알림");

		assertThat(recipient).isNotNull();
		assertThat(recipient.getRecipient().getUserType()).isEqualTo(학생);
	}
}