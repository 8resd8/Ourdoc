package com.ssafy.ourdoc.domain.user.teacher.repository;

import com.ssafy.ourdoc.domain.user.dto.TeacherQueryDto;
import com.ssafy.ourdoc.domain.user.teacher.dto.TeacherProfileResponseDto;

public interface TeacherQueryRepository {
	TeacherQueryDto getTeacherLoginDto(Long userId);

	TeacherProfileResponseDto findTeacherProfileByUserId(Long userId);
}
