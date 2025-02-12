package com.ssafy.ourdoc.domain.user.teacher.service;

import static com.ssafy.ourdoc.global.common.enums.Active.*;
import static com.ssafy.ourdoc.global.common.enums.AuthStatus.*;
import static com.ssafy.ourdoc.global.common.enums.EmploymentStatus.*;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.time.Year;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.imageio.ImageIO;

import org.apache.hc.client5.http.utils.Base64;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.ssafy.ourdoc.domain.classroom.dto.SchoolClassDto;
import com.ssafy.ourdoc.domain.classroom.entity.ClassRoom;
import com.ssafy.ourdoc.domain.classroom.entity.School;
import com.ssafy.ourdoc.domain.classroom.repository.ClassRoomRepository;
import com.ssafy.ourdoc.domain.classroom.repository.SchoolRepository;
import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.domain.user.repository.UserQueryRepository;
import com.ssafy.ourdoc.domain.user.repository.UserRepository;
import com.ssafy.ourdoc.domain.user.student.repository.StudentClassQueryRepository;
import com.ssafy.ourdoc.domain.user.student.repository.StudentClassRepository;
import com.ssafy.ourdoc.domain.user.student.repository.StudentRepository;
import com.ssafy.ourdoc.domain.user.teacher.dto.QrResponseDto;
import com.ssafy.ourdoc.domain.user.teacher.dto.StudentListResponse;
import com.ssafy.ourdoc.domain.user.teacher.dto.StudentPendingProfileDto;
import com.ssafy.ourdoc.domain.user.teacher.dto.StudentProfileDto;
import com.ssafy.ourdoc.domain.user.teacher.dto.TeacherProfileResponseDto;
import com.ssafy.ourdoc.domain.user.teacher.dto.TeacherProfileUpdateRequest;
import com.ssafy.ourdoc.domain.user.teacher.dto.TeacherSignupRequest;
import com.ssafy.ourdoc.domain.user.teacher.dto.VerificateAffiliationChangeRequest;
import com.ssafy.ourdoc.domain.user.teacher.entity.Teacher;
import com.ssafy.ourdoc.domain.user.teacher.entity.TeacherClass;
import com.ssafy.ourdoc.domain.user.teacher.repository.TeacherClassRepository;
import com.ssafy.ourdoc.domain.user.teacher.repository.TeacherQueryRepository;
import com.ssafy.ourdoc.domain.user.teacher.repository.TeacherRepository;
import com.ssafy.ourdoc.global.common.enums.Active;
import com.ssafy.ourdoc.global.common.enums.EmploymentStatus;
import com.ssafy.ourdoc.global.common.enums.UserType;
import com.ssafy.ourdoc.global.integration.s3.service.S3StorageService;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class TeacherService {

	private final UserRepository userRepository;
	private final TeacherRepository teacherRepository;
	private final TeacherClassRepository teacherClassRepository;
	private final SchoolRepository schoolRepository;
	private final S3StorageService s3StorageService;
	private final TeacherQueryRepository teacherQueryRepository;
	private final StudentClassQueryRepository studentClassQueryRepository;
	private final StudentClassRepository studentClassRepository;
	private final StudentRepository studentRepository;
	private final ClassRoomRepository classRoomRepository;
	private final EntityManager em;

	@Value("${prod.QrUrl}")
	String prodUrl;

	// 1. 교사 회원가입
	public Long signup(TeacherSignupRequest request, MultipartFile certifiateFile) {

		// 1) 중복 ID 체크
		Optional<User> existingUser = userRepository.findByLoginId(request.loginId());
		if (existingUser.isPresent()) {
			throw new IllegalArgumentException("이미 존재하는 로그인 ID입니다.");
		}

		// 2) 비밀번호 해싱
		String encodedPassword = BCrypt.hashpw(request.password(), BCrypt.gensalt());

		// 3) 재직증명서 S3에 업로드
		String certificateImageUrl = validateAndUploadFile(certifiateFile);

		// 4) User 엔티티 생성
		User user = User.builder()
			.userType(UserType.교사)
			.name(request.name())
			.loginId(request.loginId())
			.password(encodedPassword)
			.birth(request.birth())
			.gender(request.gender())
			.active(활성)
			.build();

		User savedUser = userRepository.save(user);

		// 5) Teacher 엔티티 생성
		Teacher teacher = Teacher.builder()
			.user(savedUser)
			.email(request.email())
			.phone(request.phone())
			.certificateImageUrl(certificateImageUrl)
			.employmentStatus(비재직)
			.build();

		Teacher savedTeacher = teacherRepository.save(teacher);

		return savedTeacher.getId();
	}

	// 2. QR 생성
	public QrResponseDto generateTeacherClassQr(Long teacherId) {
		// 1) 교사 조회
		Teacher teacher = teacherRepository.findById(teacherId)
			.orElseThrow(() -> new IllegalArgumentException("해당 ID의 교사가 없습니다."));

		// 2) 소속 반 정보
		Long userId = teacher.getUser().getId();
		ClassRoom classRoom = teacherClassRepository.findByUserIdAndActive(userId, 활성)
			.map(TeacherClass::getClassRoom)
			.orElseThrow(() -> new NoSuchElementException("활성 상태의 교사 학급 정보가 존재하지 않습니다."));

		String schoolName = classRoom.getSchool().getSchoolName();
		Long schoolId = classRoom.getSchool().getId();
		int grade = classRoom.getGrade();
		int classNumber = classRoom.getClassNumber();

		// 3) QR에 담을 json 데이터
		String QrLink = String.format(prodUrl, schoolName, schoolId, grade, classNumber);

		// json 데이터의 한글이 깨지지 않도록 설정
		Map<EncodeHintType, Object> hints = new HashMap<>();
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");  // 한글 지원
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);  // 에러 정정 수준(L/M/Q/H)

		try {
			// 4) QR BitMatrix 생성
			BitMatrix bitMatrix = new MultiFormatWriter().encode(QrLink, // 여기 데이터를 넣음
				BarcodeFormat.QR_CODE, 300, 300, hints);

			// 5) BitMatrix -> BufferedImage 변환
			BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

			// 6) PNG 형식으로 byte[] 출력
			try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
				ImageIO.write(qrImage, "png", baos);
				String qrImageBase64 = Base64.encodeBase64String(baos.toByteArray());
				return new QrResponseDto(qrImageBase64, QrLink);
			}
		} catch (Exception e) {
			throw new RuntimeException("QR 코드 생성 중 오류가 발생했습니다.", e);
		}
	}

	public String validateAndUploadFile(MultipartFile file) {
		if (file == null || file.isEmpty()) {
			throw new IllegalArgumentException("재직 증명서를 첨부해야 합니다.");
		}

		String fileType = file.getContentType();
		if (fileType == null || (!fileType.equals("application/pdf") && !fileType.startsWith("image/"))) {
			throw new IllegalArgumentException("재직 증명서는 PDF 또는 JPG 형식이어야 합니다.");
		}

		return s3StorageService.uploadFile(file);
	}

	// 본인 학급 학생 목록 조회
	public StudentListResponse getMyClassStudentList(User user, Pageable pageable) {
		Long classId = teacherClassRepository.findByUserIdAndActive(user.getId(), 활성)
			.orElseThrow(() -> new IllegalArgumentException("조회할 학급이 없습니다."))
			.getClassRoom().getId();
		Page<StudentProfileDto> studentProfileDtoList = studentClassQueryRepository.findStudentsByClassRoomIdAndActiveAndAuthStatus(
			classId, 활성, 승인, pageable);
		return new StudentListResponse(studentProfileDtoList);
	}

	// 학생 소속 변경 승인/거부
	public String verificateAffiliationChange(User user, VerificateAffiliationChangeRequest request) {
		Long classId = teacherClassRepository.findClassRoomByUserAndActive(user, 활성).getClassRoom().getId();

		User studentUser = userRepository.findByLoginId(request.studentLoginId())
			.orElseThrow(() -> new IllegalArgumentException("해당 학생을 찾을 수 없습니다."));

		studentClassRepository.findByUserIdAndClassRoomIdAndAuthStatus(studentUser.getId(),
				classId, 대기)
			.orElseThrow(() -> new IllegalArgumentException("해당 학생의 소속 반 신청 정보를 찾을 수 없습니다."));

		if (studentRepository.findByUser(studentUser).getAuthStatus().equals(거절)) {
			throw new IllegalArgumentException("학생 인증이 되지 않은 회원입니다.");
		} else if (studentRepository.findByUser(studentUser).getAuthStatus().equals(대기)) {
			changeAuthStatusOfStudent(request, studentUser);
			changeAuthStatusOfStudentClass(request, studentUser, classId);
		} else {
			changeAuthStatusOfStudentClass(request, studentUser, classId);
		}

		return "학생 소속 변경이 " + (request.isApproved() ? "승인" : "거절") + "되었습니다.";
	}

	private void changeAuthStatusOfStudentClass(VerificateAffiliationChangeRequest request, User studentUser,
		Long classId) {
		if (request.isApproved()) {
			studentClassQueryRepository.updateAuthStatusOfStudentClass(studentUser.getId(), classId, 승인);
		} else {
			studentClassQueryRepository.updateAuthStatusOfStudentClass(studentUser.getId(), classId, 거절);
		}
	}

	private void changeAuthStatusOfStudent(VerificateAffiliationChangeRequest request, User studentUser) {
		if (request.isApproved()) {
			studentClassQueryRepository.updateAuthStatusOfStudent(studentUser.getId(), 승인);
		} else {
			studentClassQueryRepository.updateAuthStatusOfStudent(studentUser.getId(), 거절);
		}
	}

	public Page<StudentPendingProfileDto> getPendingStudentList(User user, Pageable pageable) {
		Long classId = teacherClassRepository.findByUserIdAndActive(user.getId(), 활성)
			.orElseThrow(() -> new IllegalArgumentException("활성화된 학급이 없습니다."))
			.getClassRoom().getId();

		return studentClassQueryRepository.findStudentsByClassIdAndActiveAndAuthStatus(classId, 활성, 대기, pageable);
	}

	public TeacherProfileResponseDto getTeacherProfile(User user) {
		if (user.getActive().equals(활성)) {
			return teacherQueryRepository.findTeacherProfileByUserId(user.getId());
		} else if (user.getActive().equals(비활성)) {
			throw new IllegalArgumentException("재직중인 교사가 아닙니다.");
		}
		throw new IllegalArgumentException("알 수 없는 이유로 조회 실패");
	}

	public List<ClassRoom> getClassRoomsTeacher(Long userId) {
		return classRoomRepository.findByTeacher(userId);
	}

	public List<SchoolClassDto> getClassRoomsTeacherAndYear(Long userId, Year year) {
		List<SchoolClassDto> schoolClassDtos = classRoomRepository.findByTeacherAndYear(userId, year);
		if (schoolClassDtos.isEmpty()) {
			throw new NoSuchElementException("해당하는 연도와 사용자에 해당하는 학급 정보가 없습니다.");
		}
		return schoolClassDtos;
	}

	public void updateTeacherProfile(User user, MultipartFile profileImage, TeacherProfileUpdateRequest request) {

		// 프로필 이미지 수정
		if (profileImage != null && !profileImage.isEmpty()) {
			String profileImageUrl = s3StorageService.uploadFile(profileImage);
			userRepository.updateProfileImage(user, profileImageUrl);
		}
		em.flush();
		em.clear();

		// 학교 제외 정보 수정 (동적 쿼리로 수정해야 함)
		teacherQueryRepository.updateTeacherProfile(user, request);
		em.flush();
		em.clear();

		// 학교 정보 확인 (학급이 확인되면 다음 로직 진행. 없으면 return)
		School school = (request.schoolId() != null) ?
			schoolRepository.findById(request.schoolId()).orElse(null) : null;
		if (school == null || request.grade() == null || request.classNumber() == null || request.year() == null) {
			return;
		}

		// 이미 본인이 생성한 동일한 학급이 있는 확인. 있으면 updated_at 업데이트
		List<ClassRoom> classRoomList = classRoomRepository.findBySchoolAndGradeAndClassNumberAndYear(
			school, request.grade(), request.classNumber(), Year.of(request.year()))
			.orElse(null);

		if (classRoomList != null) {
			for (ClassRoom classRoom : classRoomList) {
				TeacherClass teacherClass = teacherClassRepository.findByUserIdAndClassRoomId(user.getId(), classRoom.getId())
					.orElse(null);
				if (teacherClass != null) {
					teacherClassRepository.updateUpdatedAt(user.getId(), classRoom.getId());
					return;
				}
			}
		}

		// class 테이블에 학급 생성(중복 상관 없음. classId로 구분할 것)
		ClassRoom classRoom = ClassRoom.builder()
			.school(school)
			.grade(request.grade())
			.classNumber(request.classNumber())
			.year(Year.of(request.year()))
			.build();

		classRoomRepository.save(classRoom);

		// teacherClass 테이블에 생성
		TeacherClass teacherClass = TeacherClass.builder()
			.user(user)
			.classRoom(classRoom)
			.active(비활성)
			.build();

		teacherClassRepository.save(teacherClass);
	}
}
