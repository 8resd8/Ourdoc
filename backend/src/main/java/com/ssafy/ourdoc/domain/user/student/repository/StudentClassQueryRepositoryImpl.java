package com.ssafy.ourdoc.domain.user.student.repository;

import static com.ssafy.ourdoc.domain.user.entity.QUser.*;
import static com.ssafy.ourdoc.domain.user.student.entity.QStudent.*;
import static com.ssafy.ourdoc.domain.user.student.entity.QStudentClass.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.ourdoc.domain.user.teacher.dto.StudentProfileDto;
import com.ssafy.ourdoc.global.common.enums.Active;
import com.ssafy.ourdoc.global.common.enums.AuthStatus;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class StudentClassQueryRepositoryImpl implements StudentClassQueryRepository {

	private final JPAQueryFactory queryFactory;

	@Override
	public List<StudentProfileDto> findStudentsByClassRoomIdAndActiveAndAuthStatus(Long classId, Active active,
		AuthStatus authStatus) {
		return queryFactory
			.select(Projections.constructor(StudentProfileDto.class,
				user.name,
				user.loginId,
				user.birth,
				user.gender,
				studentClass.studentNumber,
				student.certificateTime
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
			.fetch();
	}
}
