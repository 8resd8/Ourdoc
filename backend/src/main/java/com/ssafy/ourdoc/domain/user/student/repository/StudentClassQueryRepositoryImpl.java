package com.ssafy.ourdoc.domain.user.student.repository;

import static com.ssafy.ourdoc.domain.user.student.entity.QStudent.*;
import static com.ssafy.ourdoc.domain.user.student.entity.QStudentClass.*;
import static com.ssafy.ourdoc.global.common.enums.AuthStatus.*;

import java.time.LocalDateTime;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.ourdoc.global.common.enums.AuthStatus;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class StudentClassQueryRepositoryImpl implements StudentClassQueryRepository {

	private final JPAQueryFactory queryFactory;

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
}
