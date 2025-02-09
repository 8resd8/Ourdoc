package com.ssafy.ourdoc.domain.user.teacher.service;

import static com.ssafy.ourdoc.global.common.enums.Active.*;
import static com.ssafy.ourdoc.global.common.enums.AuthStatus.*;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.imageio.ImageIO;

import org.mindrot.jbcrypt.BCrypt;
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
import com.ssafy.ourdoc.domain.classroom.entity.ClassRoom;
import com.ssafy.ourdoc.domain.classroom.repository.SchoolRepository;
import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.domain.user.repository.UserRepository;
import com.ssafy.ourdoc.domain.user.student.entity.StudentClass;
import com.ssafy.ourdoc.domain.user.student.repository.StudentClassQueryRepository;
import com.ssafy.ourdoc.domain.user.student.repository.StudentClassRepository;
import com.ssafy.ourdoc.domain.user.student.repository.StudentRepository;
import com.ssafy.ourdoc.domain.user.teacher.dto.StudentPendingProfileDto;
import com.ssafy.ourdoc.domain.user.teacher.dto.TeacherSignupRequest;
import com.ssafy.ourdoc.domain.user.teacher.dto.VerificateAffiliationChangeRequest;
import com.ssafy.ourdoc.domain.user.teacher.entity.Teacher;
import com.ssafy.ourdoc.domain.user.teacher.entity.TeacherClass;
import com.ssafy.ourdoc.domain.user.teacher.repository.TeacherClassRepository;
import com.ssafy.ourdoc.domain.user.teacher.repository.TeacherRepository;
import com.ssafy.ourdoc.global.common.enums.Active;
import com.ssafy.ourdoc.global.common.enums.AuthStatus;
import com.ssafy.ourdoc.global.common.enums.UserType;
import com.ssafy.ourdoc.global.integration.s3.service.S3StorageService;

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
	private final StudentClassRepository studentClassRepository;
	private final StudentClassQueryRepository studentClassQueryRepository;
	private final StudentRepository studentRepository;

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
			.build();

		Teacher savedTeacher = teacherRepository.save(teacher);

		return savedTeacher.getId();
	}

	// 2. QR 생성
	public byte[] generateTeacherClassQr(Long teacherId) {
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
		// schoolName, 학년, 반 (url은 추후 수정 필요)
		String googleFormLink = String.format(
			"https://docs.google.com/forms/d/e/1FAIpQLSfxNCzcxL07Kzo27rCINXu4PHxco7Y4aT8iyi3ys-3PU5j5fg/viewform?usp=pp_url&entry.93879875=%s&entry.640235071=%d&entry.631690045=%d&entry.581945071=%d",
			schoolName,        // 학교
			schoolId,    // 주소
			grade,            // 학년
			classNumber        // 반
		);

		// json 데이터의 한글이 깨지지 않도록 설정
		Map<EncodeHintType, Object> hints = new HashMap<>();
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");  // 한글 지원
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);  // 에러 정정 수준(L/M/Q/H)

		try {
			// 4) QR BitMatrix 생성
			BitMatrix bitMatrix = new MultiFormatWriter().encode(googleFormLink, // 여기 데이터를 넣음
				BarcodeFormat.QR_CODE, 300, // width
				300, // height
				hints);

			// 5) BitMatrix -> BufferedImage 변환
			BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

			// 6) PNG 형식으로 byte[] 출력
			try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
				ImageIO.write(qrImage, "png", baos);
				return baos.toByteArray();
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

	// 학생 소속 변경 승인/거부
	public String verificateAffiliationChange(User user, VerificateAffiliationChangeRequest request) {
		Long classId = teacherClassRepository.findClassRoomByUserAndActive(user, 활성).getClassRoom().getId();

		User studentUser = userRepository.findByLoginId(request.studentLoginId())
			.orElseThrow(() -> new IllegalArgumentException("해당 학생을 찾을 수 없습니다."));

		studentClassRepository.findByUserIdAndClassRoomIdAndAuthStatus(studentUser.getId(),
				classId, 대기)
			.orElseThrow(() -> new IllegalArgumentException("해당 학생의 소속 반 신청 정보를 찾을 수 없습니다."));

		if (studentRepository.findByUser(studentUser).getAuthStatus() == 거절) {
			throw new IllegalArgumentException("학생 인증이 되지 않은 회원입니다.");
		} else if (studentRepository.findByUser(studentUser).getAuthStatus() == 대기) {
			changeAuthStatusOfStudent(request, studentUser);
			changeAuthStatusOfStudentClass(request, studentUser, classId);
		} else {
			changeAuthStatusOfStudentClass(request, studentUser, classId);
		}

		return "학생 소속 변경이 " + (request.isApproved() ? "승인" : "거절") + "되었습니다.";
	}

	private void changeAuthStatusOfStudentClass(VerificateAffiliationChangeRequest request, User studentUser, Long classId) {
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

		return studentClassQueryRepository.findStudentsByClassIdAndActiveAndAuthStatus(classId, 활성, AuthStatus.대기, pageable);
	}
}
