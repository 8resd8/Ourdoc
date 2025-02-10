package com.ssafy.ourdoc.domain.user.student.repository;

import static com.ssafy.ourdoc.domain.classroom.entity.QClassRoom.*;
import static com.ssafy.ourdoc.domain.user.entity.QUser.*;
import static com.ssafy.ourdoc.domain.user.student.entity.QStudent.*;
import static com.ssafy.ourdoc.domain.user.student.entity.QStudentClass.*;
import static com.ssafy.ourdoc.global.common.enums.Active.*;
import static com.ssafy.ourdoc.global.common.enums.AuthStatus.*;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.ourdoc.domain.user.student.dto.InactiveStudentProfileResponseDto;
import com.ssafy.ourdoc.domain.user.student.dto.StudentProfileResponseDto;
import com.ssafy.ourdoc.domain.user.teacher.dto.StudentPendingProfileDto;
import com.ssafy.ourdoc.domain.user.teacher.dto.StudentProfileDto;
import com.ssafy.ourdoc.global.common.enums.Active;
import com.ssafy.ourdoc.global.common.enums.AuthStatus;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class StudentClassQueryRepositoryImpl implements StudentClassQueryRepository {

	private final JPAQueryFactory queryFactory;

	@Override
	public Page<StudentProfileDto> findStudentsByClassRoomIdAndActiveAndAuthStatus(Long classId, Active active,
		AuthStatus authStatus, Pageable pageable) {
		List<StudentProfileDto> content =  queryFactory
			.select(Projections.constructor(StudentProfileDto.class,
				user.name,
				user.loginId,
				user.birth,
				user.gender,
				studentClass.studentNumber,
				student.certificateTime,
				user.profileImagePath
			))
			.from(studentClass)
			.join(studentClass.user, user)
			.join(student).on(student.user.id.eq(studentClass.user.id))
			.where(
				studentClass.classRoom.id.eq(classId),
				studentClass.active.eq(active),
				studentClass.authStatus.eq(authStatus)
			)
			.orderBy(studentClass.studentNumber.asc())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		long totalCount = queryFactory
				.select(studentClass.count())
				.from(studentClass)
				.where(
					studentClass.classRoom.id.eq(classId),
					studentClass.active.eq(active),
					studentClass.authStatus.eq(authStatus)
				)
				.fetchOne();

		return new PageImpl<>(content, pageable, totalCount);
	}

	@Override
	public long updateAuthStatusOfStudentClass(Long userId, Long classId, AuthStatus newStatus) {
		return queryFactory
			.update(studentClass)
			.set(studentClass.authStatus, newStatus)
			.set(studentClass.certificateTime, Expressions.constant(LocalDateTime.now()))
			.set(studentClass.updatedAt, Expressions.constant(LocalDateTime.now()))
			.where(
				studentClass.user.id.eq(userId),
				studentClass.classRoom.id.eq(classId),
				studentClass.authStatus.eq(대기)
			)
			.execute();
	}

	@Override
	public long updateAuthStatusOfStudent(Long userId, AuthStatus newStatus) {
		return queryFactory
			.update(student)
			.set(student.authStatus, newStatus)
			.set(student.certificateTime, Expressions.constant(LocalDateTime.now()))
			.set(student.updatedAt, Expressions.constant(LocalDateTime.now()))
			.where(
				student.user.id.eq(userId),
				student.authStatus.eq(대기)
			)
			.execute();
	}

	@Override
	public StudentProfileResponseDto findStudentProfileByUserId(Long userId) {
		return queryFactory
			.select(Projections.constructor(StudentProfileResponseDto.class,
				user.profileImagePath,
				user.name,
				user.loginId,
				classRoom.school.schoolName,
				classRoom.grade,
				classRoom.classNumber,
				studentClass.studentNumber,
				user.active
			))
			.from(user)
			.join(studentClass).on(user.id.eq(studentClass.user.id))
			.join(studentClass.classRoom, classRoom)
			.where(user.id.eq(userId))
			.fetchOne();
	}

	@Override
	public InactiveStudentProfileResponseDto findInactiveStudentProfileByUserId(Long userid) {
		return queryFactory
			.select(Projections.constructor(InactiveStudentProfileResponseDto.class,
				user.profileImagePath,
				user.name,
				user.loginId,
				user.active
			))
			.from(user)
			.join(studentClass).on(user.id.eq(studentClass.user.id))
			.join(studentClass.classRoom, classRoom)
			.where(user.id.eq(userid))
			.fetchOne();
	}

	public Page<StudentPendingProfileDto> findStudentsByClassIdAndActiveAndAuthStatus(Long classId, Active active,
		AuthStatus authStatus, Pageable pageable) {
		List<StudentPendingProfileDto> content = queryFactory
			.select(Projections.constructor(StudentPendingProfileDto.class,
				studentClass.studentNumber,
				user.name,
				user.loginId,
				user.birth,
				user.gender,
				studentClass.createdAt
			))
			.from(studentClass)
			.join(studentClass.user, user)
			.where(
				studentClass.classRoom.id.eq(classId),
				studentClass.active.eq(active),
				studentClass.authStatus.eq(authStatus)
			)
			.orderBy(studentClass.createdAt.desc()) // 이름순 정렬
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		long totalCount = queryFactory
				.select(studentClass.count())
				.from(studentClass)
				.where(
					studentClass.classRoom.id.eq(classId),
					studentClass.active.eq(active),
					studentClass.authStatus.eq(authStatus)
				)
				.fetchOne();

		return new PageImpl<>(content, pageable, totalCount);
	}
}
