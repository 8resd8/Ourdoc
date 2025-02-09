package com.ssafy.ourdoc.domain.classroom.repository;

import static com.ssafy.ourdoc.domain.classroom.entity.QClassRoom.*;
import static com.ssafy.ourdoc.domain.classroom.entity.QSchool.*;
import static com.ssafy.ourdoc.domain.user.teacher.entity.QTeacherClass.*;

import java.time.Year;
import java.util.List;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.ourdoc.domain.classroom.entity.ClassRoom;
import com.ssafy.ourdoc.global.common.enums.Active;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ClassRoomQueryRepositoryImpl implements ClassRoomQueryRepository {
	private final JPAQueryFactory queryFactory;

	@Override
	public List<ClassRoom> findActiveClassBySchoolAndGrade(Long schoolId, int grade) {
		return queryFactory.selectFrom(classRoom)
			.join(teacherClass)
			.on(teacherClass.classRoom.id.eq(classRoom.id))
			.where(
				classRoom.school.id.eq(schoolId),
				classRoom.grade.eq(grade),
				teacherClass.active.eq(Active.활성)
			)
			.fetch();
	}

	@Override
	public List<ClassRoom> findByTeacher(Long userId) {
		return queryFactory.selectFrom(classRoom)
			.join(classRoom, teacherClass.classRoom)
			.join(classRoom.school, school)
			.where(teacherClass.user.id.eq(userId))
			.fetch();
	}

	@Override
	public List<ClassRoom> findByTeacherAndYear(Long userId, Year year) {
		return queryFactory.selectFrom(classRoom)
			.join(classRoom, teacherClass.classRoom)
			.join(classRoom.school, school)
			.where(
				teacherClass.user.id.eq(userId),
				classRoom.year.eq(year)
			)
			.fetch();
	}
}
