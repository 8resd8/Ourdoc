package com.ssafy.ourdoc.domain.book.repository;

import static com.ssafy.ourdoc.domain.book.entity.QBook.*;
import static com.ssafy.ourdoc.domain.book.entity.QHomework.*;
import static com.ssafy.ourdoc.domain.classroom.entity.QClassRoom.*;
import static com.ssafy.ourdoc.domain.user.entity.QUser.*;

import java.time.Year;
import java.util.List;

import com.querydsl.core.types.dsl.BooleanExpression;
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

	@Override
	public List<Homework> findByClassIdAndSearchBook(Long classId, String title, String author, String publisher) {
		return queryFactory.selectFrom(homework)
			.join(homework.book, book)
			.where(
				homework.classRoom.id.eq(classId),
				titleContains(title),
				authorContains(author),
				publisherContains(publisher)
			)
			.fetch();
	}

	private BooleanExpression titleContains(String title) {
		return (title != null && !title.isBlank())
			? book.title.containsIgnoreCase(title)
			: null;
	}

	private BooleanExpression authorContains(String author) {
		return (author != null && !author.isBlank())
			? book.author.containsIgnoreCase(author)
			: null;
	}

	private BooleanExpression publisherContains(String publisher) {
		return (publisher != null && !publisher.isBlank())
			? book.publisher.containsIgnoreCase(publisher)
			: null;
	}
}
