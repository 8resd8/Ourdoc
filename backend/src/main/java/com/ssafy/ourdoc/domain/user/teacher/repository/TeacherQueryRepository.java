package com.ssafy.ourdoc.domain.user.teacher.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ssafy.ourdoc.domain.user.dto.TeacherQueryDto;
import com.ssafy.ourdoc.domain.user.dto.TeacherVerificationDto;
import com.ssafy.ourdoc.domain.user.teacher.dto.TeacherProfileResponseDto;
import com.ssafy.ourdoc.global.common.enums.AuthStatus;

public interface TeacherQueryRepository {
	TeacherQueryDto getTeacherLoginDto(Long userId);

	TeacherProfileResponseDto findTeacherProfileByUserId(Long userId);

	Page<TeacherVerificationDto> findPendingTeachers(AuthStatus authStatus, Pageable pageable);
}
