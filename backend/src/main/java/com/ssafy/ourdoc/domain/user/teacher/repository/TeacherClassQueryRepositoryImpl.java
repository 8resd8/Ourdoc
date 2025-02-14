package com.ssafy.ourdoc.domain.user.teacher.repository;

import static com.ssafy.ourdoc.domain.user.teacher.entity.QTeacherClass.*;
import static com.ssafy.ourdoc.global.common.enums.Active.*;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.domain.user.teacher.entity.TeacherClass;
import com.ssafy.ourdoc.global.common.enums.Active;

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

	@Override
	public Optional<TeacherClass> findLatestClass(User user) {
		return Optional.ofNullable(
			queryFactory
				.selectFrom(teacherClass)
				.where(teacherClass.user.id.eq(user.getId()))
				.orderBy(teacherClass.updatedAt.desc())
				.fetchFirst()
		);
	}
}
