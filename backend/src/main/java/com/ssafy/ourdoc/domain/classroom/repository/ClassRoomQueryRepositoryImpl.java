package com.ssafy.ourdoc.domain.classroom.repository;

import static com.ssafy.ourdoc.domain.user.student.entity.QStudentClass.*;

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
		return queryFactory.selectFrom(studentClass.classRoom)
			.where(
				studentClass.classRoom.school.id.eq(schoolId),
				studentClass.classRoom.grade.eq(grade),
				studentClass.active.eq(Active.활성)
			)
			.fetch();
	}
}
