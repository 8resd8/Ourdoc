package com.ssafy.ourdoc.domain.user.service;

import static com.ssafy.ourdoc.global.common.enums.AuthStatus.*;
import static com.ssafy.ourdoc.global.common.enums.UserType.*;

import com.ssafy.ourdoc.domain.user.dto.TeacherVerificationDto;
import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.domain.user.teacher.repository.TeacherQueryRepository;
import com.ssafy.ourdoc.global.common.enums.AuthStatus;
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

	public Page<TeacherVerificationDto> getPendingTeachers(User user, Pageable pageable) {
		verifyAdmin(user);
		return teacherQueryRepository.findPendingTeachers(대기, pageable);
	}

	private void verifyAdmin(User user) {
		if (!user.getUserType().equals(관리자)) {
			throw new UserFailedException("조회 권한이 없습니다.");
		}
	}
}
