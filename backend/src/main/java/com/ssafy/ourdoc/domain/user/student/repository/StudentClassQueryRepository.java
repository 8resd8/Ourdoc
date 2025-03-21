package com.ssafy.ourdoc.domain.user.student.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.domain.user.student.dto.InactiveStudentProfileResponseDto;
import com.ssafy.ourdoc.domain.user.student.dto.StudentProfileResponseDto;
import com.ssafy.ourdoc.domain.user.student.entity.StudentClass;
import com.ssafy.ourdoc.domain.user.teacher.dto.StudentPendingProfileDto;
import com.ssafy.ourdoc.domain.user.teacher.dto.StudentProfileDto;
import com.ssafy.ourdoc.global.common.enums.Active;
import com.ssafy.ourdoc.global.common.enums.AuthStatus;

public interface StudentClassQueryRepository {
	long updateAuthStatusOfStudentClass(Long userId, Long classId, Integer studentNumber, Active newActive, AuthStatus newStatus);

	long updateAuthStatusOfStudent(Long id, AuthStatus authStatus);

	Page<StudentProfileDto> findStudentsByClassRoomIdAndActiveAndAuthStatus(Long classId, Active active, AuthStatus authStatus, Pageable pageable);

	Page<StudentPendingProfileDto> findStudentsByClassIdAndActiveAndAuthStatus(Long classId, Active active, AuthStatus authStatus, Pageable pageable);
	
	StudentProfileResponseDto findStudentProfileByUserId(Long userId);

	InactiveStudentProfileResponseDto findInactiveStudentProfileByUserId(Long userid);

	Optional<StudentClass> findLatestStudentClass(User studentUser);
}
