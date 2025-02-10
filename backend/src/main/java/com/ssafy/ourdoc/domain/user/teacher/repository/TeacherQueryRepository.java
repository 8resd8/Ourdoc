package com.ssafy.ourdoc.domain.user.teacher.repository;

import static com.ssafy.ourdoc.domain.classroom.entity.QClassRoom.*;
import static com.ssafy.ourdoc.domain.classroom.entity.QSchool.*;
import static com.ssafy.ourdoc.domain.user.entity.QUser.*;
import static com.ssafy.ourdoc.domain.user.student.entity.QStudentClass.*;
import static com.ssafy.ourdoc.domain.user.teacher.entity.QTeacherClass.*;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.ourdoc.domain.classroom.entity.QClassRoom;
import com.ssafy.ourdoc.domain.user.dto.StudentQueryDto;
import com.ssafy.ourdoc.domain.user.dto.TeacherQueryDto;
import com.ssafy.ourdoc.domain.user.teacher.entity.QTeacherClass;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class TeacherQueryRepository {

	private final JPAQueryFactory queryFactory;

	public TeacherQueryDto getTeacherLoginDto(Long userId) {
		return queryFactory
			.select(Projections.constructor(
				TeacherQueryDto.class,
				school.schoolName,
				classRoom.grade,
				classRoom.classNumber
			))
			.from(teacherClass)
			.join(classRoom).on(teacherClass.classRoom.id.eq(classRoom.id))
			.join(school).on(classRoom.school.id.eq(school.id))
			.where(teacherClass.user.id.eq(userId))
			.fetchOne();
	}
}
