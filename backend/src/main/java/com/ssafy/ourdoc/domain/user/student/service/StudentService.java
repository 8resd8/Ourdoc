package com.ssafy.ourdoc.domain.user.student.service;

import static com.ssafy.ourdoc.global.common.enums.Active.*;
import static com.ssafy.ourdoc.global.common.enums.AuthStatus.*;
import static com.ssafy.ourdoc.global.common.enums.TempPassword.*;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.ourdoc.domain.classroom.entity.ClassRoom;
import com.ssafy.ourdoc.domain.classroom.entity.School;
import com.ssafy.ourdoc.domain.classroom.repository.ClassRoomRepository;
import com.ssafy.ourdoc.domain.classroom.repository.SchoolRepository;
import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.domain.user.repository.UserRepository;
import com.ssafy.ourdoc.domain.user.student.dto.InactiveStudentProfileResponseDto;
import com.ssafy.ourdoc.domain.user.student.dto.StudentAffiliationChangeRequest;
import com.ssafy.ourdoc.domain.user.student.dto.StudentProfileResponseDto;
import com.ssafy.ourdoc.domain.user.student.dto.StudentSignupRequest;
import com.ssafy.ourdoc.domain.user.student.dto.ValidatedEntities;
import com.ssafy.ourdoc.domain.user.student.entity.Student;
import com.ssafy.ourdoc.domain.user.student.entity.StudentClass;
import com.ssafy.ourdoc.domain.user.student.repository.StudentClassQueryRepository;
import com.ssafy.ourdoc.domain.user.student.repository.StudentClassRepository;
import com.ssafy.ourdoc.domain.user.student.repository.StudentRepository;
import com.ssafy.ourdoc.global.common.enums.Active;
import com.ssafy.ourdoc.global.common.enums.AuthStatus;
import com.ssafy.ourdoc.global.common.enums.TempPassword;
import com.ssafy.ourdoc.global.common.enums.UserType;
import com.ssafy.ourdoc.global.integration.s3.service.S3StorageService;

import jakarta.persistence.EntityManager;
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
	private final StudentClassQueryRepository studentClassQueryRepository;
	private final S3StorageService s3StorageService;
	private final EntityManager em;

	// 1. 학생 회원가입
	public Long signup(StudentSignupRequest request) {

		validateSignupRequest(request);
		ValidatedEntities validatedEntities = validateAndRetrieveEntities(request);

		// User 엔티티 생성
		User user = User.builder()
			.userType(UserType.학생)
			.name(request.name())
			.loginId(request.loginId())
			.password(validatedEntities.encodedPassword())
			.birth(request.birth())
			.gender(request.gender())
			.active(활성)
			.build();
		User savedUser = userRepository.save(user);

		// Student 엔티티 생성
		Student student = Student.builder()
			.user(savedUser)
			.classRoom(validatedEntities.classRoom())
			.authStatus(대기)
			.tempPassword(N)
			.build();
		Student savedStudent = studentRepository.save(student);

		// StudentClass 엔티티 생성
		StudentClass studentClass = StudentClass.builder()
			.user(savedUser)
			.classRoom(validatedEntities.classRoom())
			.studentNumber(request.studentNumber())
			.active(활성)
			.authStatus(대기)
			.build();
		studentClassRepository.save(studentClass);

		return savedStudent.getId();
	}

	private void validateSignupRequest(StudentSignupRequest request) {
		if (request == null || request.name() == null || request.name().isBlank()
		|| request.loginId() == null || request.loginId().isBlank()
		|| request.password() == null || request.password().isBlank()
		|| request.schoolId() == null || request.grade() == null
		|| request.classNumber() == null || request.studentNumber() == null
		|| request.birth() == null || request.gender() == null) {
			throw new IllegalArgumentException("입력되지 않은 정보가 있습니다.");
		}
		if (request.grade() <= 0 || request.grade() > 6) {
			throw new IllegalArgumentException("학년은 1 ~ 6 사이의 값이어야 합니다.");
		}
		if (request.classNumber() <= 0) {
			throw new IllegalArgumentException("반 번호는 1 이상이어야 합니다.");
		}
		if (request.studentNumber() <= 0) {
			throw new IllegalArgumentException("학생 번호는 1 이상이어야 합니다.");
		}
	}


	public ValidatedEntities validateAndRetrieveEntities(StudentSignupRequest request) {
		// 1) 아이디 중복 체크
		if (userRepository.findByLoginId(request.loginId()).isPresent()) {
			throw new IllegalArgumentException("이미 존재하는 로그인 ID입니다.");
		}

		// 2) 비밀번호 해싱
		String encodedPassword = BCrypt.hashpw(request.password(), BCrypt.gensalt());

		// 3) 학교 조회
		School school = schoolRepository.findById(request.schoolId())
			.orElseThrow(() -> new NoSuchElementException("해당 학교를 찾을 수 없습니다."));

		// 4) 학년 및 반 정보 조회
		ClassRoom classRoom = classRoomRepository.findBySchoolAndGradeAndClassNumber(
			school, request.grade(), request.classNumber()
		).orElseThrow(() -> new IllegalArgumentException("해당 학년 및 반 정보를 찾을 수 없습니다."));

		return new ValidatedEntities(encodedPassword, school, classRoom);
	}

	public void requestStudentAffiliationChange(User user, StudentAffiliationChangeRequest request) {
		ClassRoom classRoom = validateAffiliation(user, request);

		StudentClass newStudentClass = StudentClass.builder()
			.user(user)
			.classRoom(classRoom)
			.studentNumber(request.studentNumber())
			.authStatus(AuthStatus.대기)
			.active(Active.활성) // 활성 상태로 추가
			.build();

		studentClassRepository.save(newStudentClass);
	}

	private ClassRoom validateAffiliation(User user, StudentAffiliationChangeRequest request) {
		// 학교 조회
		School school = schoolRepository.findById(request.schoolId())
			.orElseThrow(() -> new NoSuchElementException("해당 학교를 찾을 수 없습니다."));

		// 학년 및 반 조회
		ClassRoom classRoom = classRoomRepository.findBySchoolAndGradeAndClassNumber(
				school, request.grade(), request.classNumber())
			.orElseThrow(() -> new IllegalArgumentException("해당 학년 및 반 정보를 찾을 수 없습니다."));

		// 기존 학생 엔티티 조회
		Student student = studentRepository.findByUser(user);
		if (student == null) {
			throw new NoSuchElementException("해당 학생 정보를 찾을 수 없습니다.");
		}

		// 이미 승인 요청이 되어 있는 경우 조회
		StudentClass studentClass = studentClassRepository.findByUserAndClassRoom(user, classRoom);
		if (studentClass != null && studentClass.getAuthStatus().equals(대기)) {
			throw new IllegalArgumentException("이미 승인 요청 중입니다.");
		} else if (studentClass != null && studentClass.getAuthStatus().equals(승인)) {
			throw new IllegalArgumentException("이미 소속된 학급입니다.");
		}

		return classRoom;
	}

	public ResponseEntity<?> getStudentProfile(User user) {
		Active active = user.getActive();
		if (active.equals(활성)) {
			StudentProfileResponseDto response = studentClassQueryRepository.findStudentProfileByUserId(user.getId());
			return ResponseEntity.ok().body(response);
		} else {
			InactiveStudentProfileResponseDto response = studentClassQueryRepository.findInactiveStudentProfileByUserId(user.getId());
			return ResponseEntity.ok().body(response);
		}
	}

	public void updateProfileImage(User user, MultipartFile profileImage) {
		if (profileImage != null && !profileImage.isEmpty()) {
			String profileImageUrl = s3StorageService.uploadFile(profileImage);
			userRepository.updateProfileImage(user, profileImageUrl);
		}
		em.flush();
		em.clear();
	}
}
