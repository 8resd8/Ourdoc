package com.ssafy.ourdoc.domain.user.teacher.service;

import static com.ssafy.ourdoc.global.common.enums.Active.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.io.IOException;
import java.sql.Date;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.google.zxing.WriterException;
import com.ssafy.ourdoc.domain.classroom.entity.ClassRoom;
import com.ssafy.ourdoc.domain.classroom.entity.School;
import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.domain.user.repository.UserRepository;
import com.ssafy.ourdoc.domain.user.teacher.dto.TeacherSignupRequest;
import com.ssafy.ourdoc.domain.user.teacher.entity.Teacher;
import com.ssafy.ourdoc.domain.user.teacher.entity.TeacherClass;
import com.ssafy.ourdoc.domain.user.teacher.repository.TeacherClassRepository;
import com.ssafy.ourdoc.domain.user.teacher.repository.TeacherRepository;
import com.ssafy.ourdoc.global.common.enums.Active;
import com.ssafy.ourdoc.global.common.enums.Gender;
import com.ssafy.ourdoc.global.common.enums.UserType;

@ExtendWith(MockitoExtension.class)
class TeacherServiceTest {

	@Mock
	private UserRepository userRepository;

	@Mock
	private TeacherRepository teacherRepository;

	@Mock
	private TeacherClassRepository teacherClassRepository;

	@InjectMocks
	private TeacherService teacherService;

	private TeacherSignupRequest signupRequest;
	private User mockUser;
	private Teacher mockTeacher;

	@BeforeEach
	void setUp() {
		signupRequest = new TeacherSignupRequest(
			"김선생", // name
			"teacher123", // loginId
			"password123", // password
			Date.valueOf("1985-06-15"), // birth
			Gender.남, // gender
			"teacher@example.com", // email
			"010-1234-5678" // phone
		);

		mockUser = User.builder()
			.userType(UserType.교사)
			.name(signupRequest.name())
			.loginId(signupRequest.loginId())
			.password(signupRequest.password())
			.birth(signupRequest.birth())
			.gender(signupRequest.gender())
			.active(활성)
			.build();

		mockTeacher = Teacher.builder()
			.user(mockUser)
			.email(signupRequest.email())
			.phone(signupRequest.phone())
			.build();

		// ✅ Mock Teacher 엔티티에 id 값을 수동으로 설정
		ReflectionTestUtils.setField(mockTeacher, "id", 1L);
	}

	@Test
	@DisplayName("교사 회원가입 성공")
	void signup_Success() {
		// Given: 중복 로그인 ID가 없는 경우
		given(userRepository.findByLoginId(signupRequest.loginId())).willReturn(Optional.empty());
		given(userRepository.save(any(User.class))).willReturn(mockUser);
		given(teacherRepository.save(any(Teacher.class))).willReturn(mockTeacher);

		// When: 회원가입 실행
		Long teacherId = teacherService.signup(signupRequest);

		// Then: 생성된 ID가 null이 아니어야 함
		assertNotNull(teacherId);
		assertThat(teacherId).isEqualTo(mockTeacher.getId());
		verify(userRepository).save(any(User.class));
		verify(teacherRepository).save(any(Teacher.class));
	}

	@Test
	@DisplayName("교사 회원가입 실패 - 중복된 로그인 ID")
	void signup_Fail_DuplicateLoginId() {
		// Given: 중복 로그인 ID 존재
		given(userRepository.findByLoginId(signupRequest.loginId())).willReturn(Optional.of(mockUser));

		// When & Then: 예외 발생 검증
		IllegalArgumentException exception = assertThrows(
			IllegalArgumentException.class,
			() -> teacherService.signup(signupRequest)
		);

		assertThat(exception.getMessage()).isEqualTo("이미 존재하는 로그인 ID입니다.");

		// Verify: teacherRepository.save()가 호출되지 않음을 검증
		verify(teacherRepository, never()).save(any(Teacher.class));
	}

	@Test
	@DisplayName("QR 코드 생성 성공 - 정상적인 교사 ID 제공")
	void generateTeacherClassQr_Success() throws WriterException, IOException {
		// 0) 우선 mockTeacher의 user 필드에 id를 설정해 준다.
		//    (테스트할 때 teacher.getUser().getId()가 null이면 인자 불일치 발생)
		ReflectionTestUtils.setField(mockTeacher.getUser(), "id", 777L);

		// 1) teacherRepository 스텁
		given(teacherRepository.findById(1L))
			.willReturn(Optional.of(mockTeacher));

		// 2) TeacherClass / ClassRoom / School 모두 Mock 생성
		TeacherClass mockTeacherClass = mock(TeacherClass.class);
		ClassRoom mockClassRoom = mock(ClassRoom.class);
		School mockSchool = mock(School.class);

		// 3) teacherClassRepository 스텁
		//    - 실제 코드에서 findByUserIdAndActive( 777L, Active.활성 ) 로 불릴 것
		given(teacherClassRepository.findByUserIdAndActive(eq(777L), eq(활성)))
			.willReturn(mockTeacherClass);

		// 4) mockTeacherClass → mockClassRoom
		given(mockTeacherClass.getClassRoom()).willReturn(mockClassRoom);

		// 5) mockClassRoom → mockSchool, grade, classNumber
		given(mockClassRoom.getSchool()).willReturn(mockSchool);
		given(mockSchool.getId()).willReturn(1L);
		given(mockSchool.getSchoolName()).willReturn("테스트초등학교");
		given(mockClassRoom.getGrade()).willReturn(3);
		given(mockClassRoom.getClassNumber()).willReturn(4);

		// When: QR 코드 생성
		byte[] qrBytes = teacherService.generateTeacherClassQr(1L);

		// Then: QR 코드 byte[]가 비어 있지 않아야 함
		assertNotNull(qrBytes);
		assertTrue(qrBytes.length > 0);

		// Verify
		verify(teacherRepository, times(1)).findById(1L);
		verify(teacherClassRepository, times(1))
			.findByUserIdAndActive(777L, 활성);
	}

	@Test
	@DisplayName("QR 코드 생성 실패 - 존재하지 않는 교사 ID 제공")
	void generateTeacherClassQr_TeacherNotFound() {
		// Given: Teacher가 존재하지 않는 경우
		given(teacherRepository.findById(99L)).willReturn(Optional.empty());

		// When & Then: 예외 발생 검증
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			teacherService.generateTeacherClassQr(99L);
		});

		// Then: 예외 메시지 검증
		assertThat(exception.getMessage()).isEqualTo("해당 ID의 교사가 없습니다: 99");

		// Verify: findById가 한 번 호출되었는지 확인
		verify(teacherRepository, times(1)).findById(99L);
	}
}
