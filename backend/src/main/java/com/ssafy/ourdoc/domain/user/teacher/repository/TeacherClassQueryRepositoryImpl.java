package com.ssafy.ourdoc.domain.user.teacher.repository;

import static com.ssafy.ourdoc.domain.user.teacher.entity.QTeacherClass.*;

import java.time.LocalDateTime;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
@Transactional
public class TeacherClassQueryRepositoryImpl implements TeacherClassQueryRepository {

	private final JPAQueryFactory queryFactory;

	@Override
	public void updateUpdatedAt(Long userId, Long classId) {
		queryFactory
			.update(teacherClass)
			.set(teacherClass.updatedAt, LocalDateTime.now()) // updated_at 갱신
			.where(teacherClass.user.id.eq(userId), teacherClass.classRoom.id.eq(classId))
			.execute();
	}
}
