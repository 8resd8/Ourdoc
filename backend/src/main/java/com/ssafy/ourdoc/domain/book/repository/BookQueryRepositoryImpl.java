package com.ssafy.ourdoc.domain.book.repository;

import static com.ssafy.ourdoc.domain.book.entity.QBook.*;
import static com.ssafy.ourdoc.domain.bookreport.entity.QBookReport.*;
import static com.ssafy.ourdoc.domain.classroom.entity.QClassRoom.*;
import static com.ssafy.ourdoc.domain.user.student.entity.QStudentClass.*;
import static com.ssafy.ourdoc.domain.user.teacher.entity.QTeacherClass.*;

import java.time.LocalDateTime;
import java.time.Year;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.ourdoc.domain.book.dto.BookDetailResponse;
import com.ssafy.ourdoc.domain.book.dto.BookMostDto;
import com.ssafy.ourdoc.domain.book.entity.Book;
import com.ssafy.ourdoc.global.common.enums.Active;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BookQueryRepositoryImpl implements BookQueryRepository {
	private final JPAQueryFactory queryFactory;

	public List<Book> findBookList(String title, String author, String publisher) {
		BooleanBuilder builder = new BooleanBuilder();

		if (title != null && !title.isEmpty()) {
			builder.and(book.title.containsIgnoreCase(title));
		}
		if (author != null && !author.isEmpty()) {
			builder.and(book.author.containsIgnoreCase(author));
		}
		if (publisher != null && !publisher.isEmpty()) {
			builder.and(book.publisher.containsIgnoreCase(publisher));
		}

		return queryFactory
			.selectFrom(book)
			.where(builder)
			.fetch();
	}

	public BookMostDto findBookGradeMost(Long userId) {
		int year = Optional.ofNullable(
				queryFactory
					.select(classRoom.year)
					.from(teacherClass)
					.join(teacherClass.classRoom, classRoom)
					.where(
						teacherClass.user.id.eq(userId),
						teacherClass.active.eq(Active.활성)
					).fetchOne())
			.map(Year::getValue)
			.orElse(0);

		return queryFactory
			.select(Projections.constructor(
				BookMostDto.class,
				Projections.constructor(
					BookDetailResponse.class,
					book.id,
					book.title,
					book.author,
					book.genre,
					book.description,
					book.publisher,
					book.publishTime,
					book.imageUrl
				), bookReport.count().intValue()
			)).from(book)
			.leftJoin(bookReport)
			.on(bookReport.book.eq(book)
				.and(bookReport.approveTime.isNotNull()))
			.where(
				bookReport.createdAt.between(startDate(year), endDate(year))
			).groupBy(book.id)
			.orderBy(bookReport.count().desc())
			.limit(1)
			.fetchOne();
	}

	public BookMostDto findBookClassMost(Long userId) {
		int year = Optional.ofNullable(
				queryFactory
					.select(classRoom.year)
					.from(teacherClass)
					.join(teacherClass.classRoom, classRoom)
					.where(
						teacherClass.user.id.eq(userId),
						teacherClass.active.eq(Active.활성)
					).fetchOne())
			.map(Year::getValue)
			.orElse(0);

		Long classRoomId = queryFactory
			.select(teacherClass.classRoom.id)
			.from(teacherClass)
			.where(
				teacherClass.user.id.eq(userId),
				teacherClass.active.eq(Active.활성)
			).fetchOne();

		return queryFactory
			.select(Projections.constructor(
				BookMostDto.class,
				Projections.constructor(
					BookDetailResponse.class,
					book.id,
					book.title,
					book.author,
					book.genre,
					book.description,
					book.publisher,
					book.publishTime,
					book.imageUrl
				), bookReport.count().intValue()
			)).from(bookReport)
			.join(bookReport.book, book)
			.join(bookReport.studentClass, studentClass)
			.leftJoin(bookReport)
			.on(bookReport.studentClass.eq(studentClass)
				.and(bookReport.approveTime.isNotNull())
			)
			.where(
				classRoom.id.eq(classRoomId),
				bookReport.createdAt.between(startDate(year), endDate(year))
			).groupBy(book.id)
			.orderBy(bookReport.count().desc())
			.limit(1)
			.fetchOne();
	}

	private LocalDateTime startDate(int year) {
		return YearMonth.of(year, 3).atDay(1).atStartOfDay();
	}

	private LocalDateTime endDate(int year) {
		return YearMonth.of(year + 1, 2).atEndOfMonth().atTime(23, 59, 59);
	}
}
