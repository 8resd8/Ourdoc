package com.ssafy.ourdoc.domain.user.student.service;

import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

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
import com.ssafy.ourdoc.global.common.enums.Active;
import com.ssafy.ourdoc.global.common.enums.AuthStatus;
import com.ssafy.ourdoc.global.common.enums.UserType;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class StudentService {

	private final UserRepository userRepository;
	private final StudentRepository studentRepository;
	private final SchoolRepository schoolRepository;
	private final ClassRoomRepository classRoomRepository;
	private final StudentClassRepository studentClassRepository;

	// 1. 학생 회원가입
	public Long signup(StudentSignupRequest request) {

		// 1) 아이디 중복 체크
		Optional<User> existingUser = userRepository.findByLoginId(request.loginId());
		if (existingUser.isPresent()) {
			throw new IllegalArgumentException("이미 존재하는 로그인 ID입니다.");
		}

		// 2) 비밀번호 해싱
		String encodedPassword = BCrypt.hashpw(request.password(), BCrypt.gensalt());

		// 3) 학교 조회
		School school = schoolRepository.findBySchoolName(request.schoolName())
			.orElseThrow(() -> new IllegalArgumentException("해당 학교를 찾을 수 없습니다: " + request.schoolName()));

		// 4) 학년 및 반 정보 조회
		ClassRoom classRoom = classRoomRepository.findBySchoolAndGradeAndClassNumber(
			school, request.grade(), request.classNumber()
		).orElseThrow(() -> new IllegalArgumentException("해당 학년 및 반 정보를 찾을 수 없습니다."));

		// 5) User 엔티티 생성
		User user = User.builder()
			.userType(UserType.학생)
			.name(request.name())
			.loginId(request.loginId())
			.password(encodedPassword)
			.birth(request.birth())
			.gender(request.gender())
			.active(Active.활성)
			.build();
		User savedUser = userRepository.save(user);

		// 4) Student 엔티티 생성
		Student student = Student.builder()
			.user(savedUser)
			.classRoom(classRoom)
			.build();
		Student savedStudent = studentRepository.save(student);

		StudentClass studentClass = StudentClass.builder()
			.user(savedUser)
			.classRoom(classRoom)
			.studentNumber(request.studentNumber())
			.active(request.active())
			.authStatus(AuthStatus.대기)
			.build();
		studentClassRepository.save(studentClass);

		return savedStudent.getId();
	}
}
