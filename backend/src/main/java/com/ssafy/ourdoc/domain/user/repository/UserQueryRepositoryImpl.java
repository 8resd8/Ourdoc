package com.ssafy.ourdoc.domain.user.repository;

import static com.ssafy.ourdoc.domain.user.entity.QUser.*;
import static com.ssafy.ourdoc.domain.user.student.entity.QStudent.*;
import static com.ssafy.ourdoc.domain.user.teacher.entity.QTeacherClass.*;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.ourdoc.domain.user.entity.User;

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

	// 학생의 담당교사 찾기
	@Override
	public User findTeachersByStudentClassId(Long studentUserId) {
		// 학생의 classId 가져오기
		Long classId = queryFactory.select(student.classRoom.id)
			.from(student)
			.where(student.user.id.eq(studentUserId))
			.fetchOne();

		// classId로 해당 클래스의 교사 조회
		return queryFactory.select(user)
			.from(teacherClass)
			.join(user).on(teacherClass.user.id.eq(user.id))
			.where(teacherClass.classRoom.id.eq(classId))
			.fetchOne();
	}
}
