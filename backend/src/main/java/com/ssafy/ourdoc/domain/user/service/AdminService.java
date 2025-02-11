package com.ssafy.ourdoc.domain.user.service;

import static com.ssafy.ourdoc.global.common.enums.AuthStatus.*;
import static com.ssafy.ourdoc.global.common.enums.EmploymentStatus.*;
import static com.ssafy.ourdoc.global.common.enums.UserType.*;

import java.util.NoSuchElementException;

import com.ssafy.ourdoc.domain.user.dto.TeacherVerificationDto;
import com.ssafy.ourdoc.domain.user.dto.request.TeacherVerificationRequest;
import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.domain.user.teacher.entity.Teacher;
import com.ssafy.ourdoc.domain.user.teacher.repository.TeacherQueryRepository;
import com.ssafy.ourdoc.domain.user.teacher.repository.TeacherRepository;
import com.ssafy.ourdoc.global.common.enums.AuthStatus;
import com.ssafy.ourdoc.global.common.enums.EmploymentStatus;
import com.ssafy.ourdoc.global.common.enums.UserType;
import com.ssafy.ourdoc.global.exception.UserFailedException;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

	private final TeacherQueryRepository teacherQueryRepository;
	private final TeacherRepository teacherRepository;

	public Page<TeacherVerificationDto> getPendingTeachers(User user, Pageable pageable) {
		verifyAdmin(user);
		return teacherQueryRepository.findPendingTeachers(대기, pageable);
	}

	private void verifyAdmin(User user) {
		if (!user.getUserType().equals(관리자)) {
			throw new UserFailedException("조회 권한이 없습니다.");
		}
	}

	public String verifyTeacher(User user, TeacherVerificationRequest request) {
		verifyAdmin(user);
		checkPendingTeacher(request);

		if (request.isApproved()) {
			teacherQueryRepository.approveTeacher(request.teacherId());
			return "교사 인증 요청 처리 완료";
		} else {
			return "교사 인증 승인 요청 거부";
		}
	}

	private void checkPendingTeacher(TeacherVerificationRequest request) {
		Teacher teacher = teacherRepository.findById(request.teacherId())
			.orElseThrow(() -> new NoSuchElementException("조회되는 교사가 없습니다."));

		if (!teacher.getEmploymentStatus().equals(비재직)) {
			throw new IllegalArgumentException("승인 요청 대기 중인 교사가 없습니다.");
		}
	}
}
