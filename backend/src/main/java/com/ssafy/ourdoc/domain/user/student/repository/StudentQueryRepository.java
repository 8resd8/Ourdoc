package com.ssafy.ourdoc.domain.user.student.repository;

import static com.ssafy.ourdoc.domain.classroom.entity.QClassRoom.*;
import static com.ssafy.ourdoc.domain.classroom.entity.QSchool.*;
import static com.ssafy.ourdoc.domain.user.entity.QUser.*;
import static com.ssafy.ourdoc.domain.user.student.entity.QStudentClass.*;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.ourdoc.domain.user.dto.StudentQueryDto;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class StudentQueryRepository {

	private final JPAQueryFactory queryFactory;

	public StudentQueryDto getStudentLoginDto(Long userId) {
		return queryFactory
			.select(Projections.constructor(
				StudentQueryDto.class,
					school.schoolName,
				classRoom.grade,
				classRoom.classNumber,
				studentClass.studentNumber
				))
			.from(studentClass)
			.join(classRoom).on(studentClass.classRoom.id.eq(classRoom.id))
			.join(school).on(classRoom.school.id.eq(school.id))
			.where(studentClass.user.id.eq(userId))
			.fetchOne();
	}
}
