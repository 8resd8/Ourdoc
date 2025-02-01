package com.ssafy.ourdoc.domain.user.repository;

import static com.ssafy.ourdoc.domain.user.student.entity.QStudent.*;
import static com.ssafy.ourdoc.domain.user.teacher.entity.QTeacherClass.*;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserQueryRepositoryImpl implements UserQueryRepository {

	private final JPAQueryFactory queryFactory;

	@Override
	public Long findTeacherIdByStudentId(Long studentId) {
		return queryFactory
			.select(teacherClass.id)
			.from(student) // StudentClass로 변경해야함.
			.join(teacherClass).on(student.classRoom.id.eq(teacherClass.classRoom.id))
			.where(student.user.id.eq(studentId))
			.fetchOne();
	}
}
