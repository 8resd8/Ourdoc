package com.ssafy.ourdoc.domain.classroom.service;

import static org.assertj.core.api.Assertions.*;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ssafy.ourdoc.data.entity.UserSample;
import com.ssafy.ourdoc.domain.classroom.dto.CreateClassRequest;
import com.ssafy.ourdoc.domain.classroom.entity.ClassRoom;
import com.ssafy.ourdoc.domain.classroom.entity.School;
import com.ssafy.ourdoc.domain.classroom.repository.ClassRoomRepository;
import com.ssafy.ourdoc.domain.classroom.repository.SchoolRepository;
import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.domain.user.repository.UserRepository;
import com.ssafy.ourdoc.domain.user.teacher.entity.Teacher;
import com.ssafy.ourdoc.domain.user.teacher.repository.TeacherClassRepository;
import com.ssafy.ourdoc.domain.user.teacher.repository.TeacherRepository;
import com.ssafy.ourdoc.global.common.enums.Active;
import com.ssafy.ourdoc.global.common.enums.EmploymentStatus;
import com.ssafy.ourdoc.global.common.enums.Gender;
import com.ssafy.ourdoc.global.common.enums.UserType;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
class ClassServiceTest {

	@Autowired
	private ClassRoomRepository classRoomRepository;
	@Autowired
	private TeacherClassRepository teacherClassRepository;
	@Autowired
	private TeacherRepository teacherRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private SchoolRepository schoolRepository;
	@Autowired
	private ClassService classService;

	private Long teacherId;
	private CreateClassRequest request;
	private Teacher teacher;
	private ClassRoom classRoom;
	private User user;
	private School school;

	@BeforeEach
	void setUp() {
		// 1학년 1반, 2024년
		request = new CreateClassRequest("테스트학교", "테스트주소", 2024, 1, 1);

		// 학교 저장
		school = schoolRepository.save(new School("테스트학교", "테스트주소"));

		// 교사 회원가입
		user = userRepository.save(
			new User(UserType.교사, "테스트이름", "test-id", "test-pwd", new Date(1L), Gender.남, Active.활성));

		teacher = teacherRepository.save(
			new Teacher(user, "teacher@example.com", "010-1234-5678", EmploymentStatus.재직, LocalDateTime.now(), "12345"));
		teacherId = teacher.getId();
	}

	@Test
	@DisplayName("존재하지 않는 userId로 생성 시 실패")
	void createClassWithInvalidTeacherId() {
		User invalidUser = UserSample.user();

		assertThatThrownBy(() -> classService.createClass(invalidUser, request))
			.isInstanceOf(NoSuchElementException.class);
	}
}