package com.ssafy.ourdoc.domain.user.teacher.repository;

import static com.ssafy.ourdoc.domain.classroom.entity.QClassRoom.*;
import static com.ssafy.ourdoc.domain.classroom.entity.QSchool.*;
import static com.ssafy.ourdoc.domain.user.entity.QUser.*;
import static com.ssafy.ourdoc.domain.user.teacher.entity.QTeacher.*;
import static com.ssafy.ourdoc.domain.user.teacher.entity.QTeacherClass.*;

import org.springframework.stereotype.Repository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.ourdoc.domain.user.dto.TeacherQueryDto;
import com.ssafy.ourdoc.domain.user.teacher.dto.TeacherProfileResponseDto;
import com.ssafy.ourdoc.domain.user.teacher.entity.QTeacher;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class TeacherQueryRepositoryImpl implements TeacherQueryRepository {

	private final JPAQueryFactory queryFactory;

	@Override
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

	@Override
	public TeacherProfileResponseDto findTeacherProfileByUserId(Long userId) {
		return queryFactory
			.select(Projections.constructor(TeacherProfileResponseDto.class,
				user.profileImagePath,
				user.name,
				user.loginId,
				teacher.email,
				classRoom.school.schoolName,
				classRoom.grade,
				classRoom.classNumber,
				teacher.phone
			))
			.from(user)
			.join(teacher).on(teacher.user.id.eq(user.id))
			.join(teacherClass).on(teacherClass.user.id.eq(user.id))
			.join(teacherClass.classRoom, classRoom)
			.join(classRoom.school, school)
			.where(user.id.eq(userId))
			.fetchOne();
	}
}
