package com.ssafy.ourdoc.domain.user.student.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ssafy.ourdoc.domain.user.teacher.dto.StudentProfileDto;
import com.ssafy.ourdoc.global.common.enums.Active;
import com.ssafy.ourdoc.global.common.enums.AuthStatus;

public interface StudentClassQueryRepository {
	List<StudentProfileDto> findStudentsByClassRoomIdAndActiveAndAuthStatus(Long classId, Active active, AuthStatus authStatus);
}