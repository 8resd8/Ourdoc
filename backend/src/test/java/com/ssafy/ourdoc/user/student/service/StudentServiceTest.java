package com.ssafy.ourdoc.user.student.service;

import static com.ssafy.ourdoc.global.common.enums.Active.*;
import static com.ssafy.ourdoc.global.common.enums.AuthStatus.*;
import static com.ssafy.ourdoc.global.common.enums.TempPassword.*;
import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.sql.Date;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ssafy.ourdoc.domain.classroom.entity.ClassRoom;
import com.ssafy.ourdoc.domain.classroom.entity.School;
import com.ssafy.ourdoc.domain.classroom.repository.ClassRoomRepository;
import com.ssafy.ourdoc.domain.classroom.repository.SchoolRepository;
import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.domain.user.repository.UserRepository;
import com.ssafy.ourdoc.domain.user.student.dto.StudentSignupRequest;
import com.ssafy.ourdoc.domain.user.student.entity.Student;
import com.ssafy.ourdoc.domain.user.student.entity.StudentClass;
import com.ssafy.ourdoc.domain.user.student.repository.StudentClassRepository;
import com.ssafy.ourdoc.domain.user.student.repository.StudentRepository;
import com.ssafy.ourdoc.domain.user.student.service.StudentService;
import com.ssafy.ourdoc.global.common.enums.Active;
import com.ssafy.ourdoc.global.common.enums.AuthStatus;
import com.ssafy.ourdoc.global.common.enums.Gender;
import com.ssafy.ourdoc.global.common.enums.TempPassword;
import com.ssafy.ourdoc.global.common.enums.UserType;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {
	@Mock
	private UserRepository userRepository;
	@Mock
	private StudentRepository studentRepository;
	@Mock
	private SchoolRepository schoolRepository;
	@Mock
	private ClassRoomRepository classRoomRepository;
	@Mock
	private StudentClassRepository studentClassRepository;

	@InjectMocks
	private StudentService studentService;

	private School mockSchool;
	private ClassRoom mockClassRoom;

	private StudentSignupRequest signupRequest;

	@BeforeEach
	void setUp() {
		signupRequest = new StudentSignupRequest(
			"김싸피",
			"ssafy",
			"1234",
			"싸피초등학교",
			1,
			2,
			10,
			Date.valueOf("2025-02-01"),
			Gender.남);

		mockSchool = School.builder()
			.schoolName("싸피초등학교")
			.build();

		mockClassRoom = ClassRoom.builder()
			.school(mockSchool)
			.grade(2)
			.classNumber(3)
			.build();
	}

	@Test
	@DisplayName("학생 회원 가입 성공")
	void studentSignUpSuccess() {
		given(userRepository.findByLoginId(signupRequest.loginId())).willReturn(Optional.empty());
		given(schoolRepository.findBySchoolName(signupRequest.schoolName())).willReturn(Optional.of(mockSchool));
		given(classRoomRepository.findBySchoolAndGradeAndClassNumber(
			mockSchool, signupRequest.grade(), signupRequest.classNumber()
		)).willReturn(Optional.of(mockClassRoom));

		User mockUser = User.builder()
			.userType(UserType.학생)
			.name(signupRequest.name())
			.loginId(signupRequest.loginId())
			.password(signupRequest.password())
			.birth(signupRequest.birth())
			.gender(signupRequest.gender())
			.active(활성)
			.build();
		given(userRepository.save(any(User.class))).willReturn(mockUser);

		Student mockStudent = Student.builder()
			.user(mockUser)
			.classRoom(mockClassRoom)
			.tempPassword(N)
			.authStatus(대기)
			.build();
		given(studentRepository.save(any(Student.class))).willReturn(mockStudent);

		given(studentClassRepository.save(any(StudentClass.class)))
			.willReturn(StudentClass.builder().build());

		// When
		Long studentId = studentService.signup(signupRequest);

		// Then
		assertThat(studentId).isEqualTo(mockStudent.getId());
		verify(userRepository).save(any(User.class));
		verify(studentRepository).save(any(Student.class));
		verify(studentClassRepository).save(any(StudentClass.class));
	}

	@Test
	@DisplayName("학생 회원 가입 실패_중복 아이디")
	void studentSignupFailDuplicatedLoginId() {
		// Given
		given(userRepository.findByLoginId(signupRequest.loginId()))
			.willReturn(Optional.of(User.builder().build()));

		// When & Then
		IllegalArgumentException exception = assertThrows(
			IllegalArgumentException.class,
			() -> studentService.signup(signupRequest)
		);

		assertThat(exception.getMessage()).isEqualTo("이미 존재하는 로그인 ID입니다.");
		verify(userRepository, never()).save(any(User.class));
	}

	@Test
	@DisplayName("학생 회원 가입 실패_학교 없음")
	void studentSignupFailNoExistSchool() {
		// Given
		given(userRepository.findByLoginId(signupRequest.loginId())).willReturn(Optional.empty());
		given(schoolRepository.findBySchoolName(signupRequest.schoolName()))
			.willReturn(Optional.empty());

		// When & Then
		IllegalArgumentException exception = assertThrows(
			IllegalArgumentException.class,
			() -> studentService.signup(signupRequest)
		);

		assertThat(exception.getMessage()).contains("해당 학교를 찾을 수 없습니다");
		verify(userRepository, never()).save(any(User.class));
	}

	@Test
	@DisplayName("학생 회원 가입 실패_학급 없음")
	void studentSignupFailNoExistClassRoom() {
		// Given
		School school = School.builder().schoolName("싸피초등학교").build();

		when(userRepository.findByLoginId(signupRequest.loginId())).thenReturn(Optional.empty());
		when(schoolRepository.findBySchoolName(signupRequest.schoolName())).thenReturn(Optional.of(school));
		when(classRoomRepository.findBySchoolAndGradeAndClassNumber(any(), anyInt(), anyInt()))
			.thenReturn(Optional.empty());

		// When & Then
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			studentService.signup(signupRequest);
		});

		assertTrue(exception.getMessage().contains("해당 학년 및 반 정보를 찾을 수 없습니다"));
	}
}
