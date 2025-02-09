package com.ssafy.ourdoc.domain.book.repository;

import static com.ssafy.ourdoc.domain.book.entity.QHomework.*;
import static com.ssafy.ourdoc.domain.classroom.entity.QClassRoom.*;
import static com.ssafy.ourdoc.domain.user.entity.QUser.*;

import java.time.Year;
import java.util.List;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.ourdoc.domain.book.entity.Homework;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class HomeworkQueryRepositoryImpl implements HomeworkQueryRepository {
	private final JPAQueryFactory queryFactory;

	@Override
	public List<Homework> findByUserIdAndYear(Long userId, Year year) {
		return queryFactory.select(homework)
			.join(homework.user, user)
			.join(homework.classRoom, classRoom)
			.where(
				homework.user.id.eq(userId),
				classRoom.year.eq(year)
			)
			.fetch();
	}

	@Override
	public List<Homework> findByClassIn(List<Long> classIds) {
		return queryFactory.selectFrom(homework)
			.join(homework.classRoom, classRoom)
			.where(classRoom.id.in(classIds))
			.fetch();
	}
}
