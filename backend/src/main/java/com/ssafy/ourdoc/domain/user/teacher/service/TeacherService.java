package com.ssafy.ourdoc.domain.user.teacher.service;

import static com.ssafy.ourdoc.global.common.enums.Active.*;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.imageio.ImageIO;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

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
import com.ssafy.ourdoc.domain.user.teacher.dto.TeacherSignupRequest;
import com.ssafy.ourdoc.domain.user.teacher.entity.Teacher;
import com.ssafy.ourdoc.domain.user.teacher.entity.TeacherClass;
import com.ssafy.ourdoc.domain.user.teacher.repository.TeacherClassRepository;
import com.ssafy.ourdoc.domain.user.teacher.repository.TeacherRepository;
import com.ssafy.ourdoc.global.common.enums.Active;
import com.ssafy.ourdoc.global.common.enums.UserType;

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

	// 1. 교사 회원가입
	public Long signup(TeacherSignupRequest request) {

		// 1) 중복 ID 체크
		Optional<User> existingUser = userRepository.findByLoginId(request.loginId());
		if (existingUser.isPresent()) {
			throw new IllegalArgumentException("이미 존재하는 로그인 ID입니다.");
		}

		// 2) 비밀번호 해싱
		String encodedPassword = BCrypt.hashpw(request.password(), BCrypt.gensalt());

		// 3) User 엔티티 생성
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

		// 4) Teacher 엔티티 생성
		Teacher teacher = Teacher.builder().user(savedUser).email(request.email()).phone(request.phone())
			.build();

		Teacher savedTeacher = teacherRepository.save(teacher);

		return savedTeacher.getId();
	}

	// 2. QR 생성
	public byte[] generateTeacherClassQr(Long teacherId) {
		// 1) 교사 조회
		Teacher teacher = teacherRepository.findById(teacherId)
			.orElseThrow(() -> new IllegalArgumentException("해당 ID의 교사가 없습니다: " + teacherId));

		// 2) 소속 반 정보
		Long userId = teacher.getUser().getId();
		ClassRoom classRoom = teacherClassRepository.findByUserIdAndActive(userId, 활성).getClassRoom();
		if (classRoom == null) {
			throw new IllegalStateException("교사에 연결된 ClassRoom 정보가 없습니다.");
		}

		Long schoolId = classRoom.getSchool().getId();
		String schoolName = classRoom.getSchool().getSchoolName();
		int grade = classRoom.getGrade();
		int classNumber = classRoom.getClassNumber();

		// 3) QR에 담을 json 데이터
		// schoolName, 학년, 반 (url은 추후 수정 필요)
		String googleFormLink = String.format(
			"https://docs.google.com/forms/d/e/1FAIpQLSfxNCzcxL07Kzo27rCINXu4PHxco7Y4aT8iyi3ys-3PU5j5fg/viewform?usp=pp_url&entry.93879875=%s&entry.631690045=%d&entry.581945071=%d",
			schoolName,		// 학교
			grade,			// 학년
			classNumber		// 반
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

}
