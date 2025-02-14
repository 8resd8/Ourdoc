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

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.ourdoc.domain.book.dto.BookDetailDto;
import com.ssafy.ourdoc.domain.book.dto.most.BookMostDto;
import com.ssafy.ourdoc.domain.book.entity.Book;
import com.ssafy.ourdoc.global.common.enums.Active;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BookQueryRepositoryImpl implements BookQueryRepository {
	private final JPAQueryFactory queryFactory;

	@Override
	public Page<Book> findBookPage(String title, String author, String publisher, Pageable pageable) {
		List<Book> books = queryFactory
			.selectFrom(book)
			.where(
				containsTitle(title),
				containsAuthor(author),
				containsPublisher(publisher)
			)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		long total = findBookList(title, author, publisher).size();
		return new PageImpl<>(books, pageable, total);
	}

	@Override
	public List<Book> findBookList(String title, String author, String publisher) {
		return queryFactory
			.selectFrom(book)
			.where(
				containsTitle(title),
				containsAuthor(author),
				containsPublisher(publisher)
			)
			.fetch();
	}

	public BookMostDto findBookGradeMost(Long userId) {
		Tuple tuple = queryFactory
			.select(classRoom.year, classRoom.grade)
			.from(teacherClass)
			.join(teacherClass.classRoom, classRoom)
			.where(
				teacherClass.user.id.eq(userId),
				teacherClass.active.eq(Active.활성)
			).fetchOne();

		int year = Optional.ofNullable(tuple)
			.map(t -> t.get(classRoom.year))
			.map(Year::getValue)
			.orElse(0);
		int grade = Optional.ofNullable(tuple)
			.map(t -> t.get(classRoom.grade))
			.orElse(0);

		return queryFactory
			.select(Projections.constructor(
				BookMostDto.class,
				Projections.constructor(
					BookDetailDto.class,
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
				.and(bookReport.approveTime.isNotNull())
				.and(bookReport.createdAt.between(startDate(year), endDate(year)))
			)
			.leftJoin(bookReport.studentClass, studentClass)
			.leftJoin(studentClass.classRoom, classRoom)
			.where(
				classRoom.grade.eq(grade).or(bookReport.isNull())
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
					BookDetailDto.class,
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
				.and(bookReport.createdAt.between(startDate(year), endDate(year)))
				.and(bookReport.approveTime.isNotNull())
			)
			.leftJoin(bookReport.studentClass, studentClass)
			.leftJoin(studentClass.classRoom, classRoom)
			.where(
				classRoom.id.eq(classRoomId).or(bookReport.isNull())
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

	private BooleanExpression containsTitle(String title) {
		return (title != null && !title.isEmpty()) ? book.title.containsIgnoreCase(title) : null;
	}

	private BooleanExpression containsAuthor(String author) {
		return (author != null && !author.isEmpty()) ? book.author.containsIgnoreCase(author) : null;
	}

	private BooleanExpression containsPublisher(String publisher) {
		return (publisher != null && !publisher.isEmpty()) ? book.publisher.containsIgnoreCase(publisher) : null;
	}
}
