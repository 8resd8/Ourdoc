package com.ssafy.ourdoc.domain.book.repository;

import static com.ssafy.ourdoc.domain.book.entity.QBook.*;
import static com.ssafy.ourdoc.domain.bookreport.entity.QBookReport.*;
import static com.ssafy.ourdoc.domain.classroom.entity.QClassRoom.*;
import static com.ssafy.ourdoc.domain.classroom.entity.QSchool.*;
import static com.ssafy.ourdoc.domain.user.student.entity.QStudentClass.*;
import static com.ssafy.ourdoc.domain.user.teacher.entity.QTeacherClass.*;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

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
		int grade = Optional.ofNullable(queryFactory
			.select(classRoom.grade)
			.from(teacherClass)
			.join(teacherClass.classRoom, classRoom)
			.where(
				teacherClass.user.id.eq(userId),
				teacherClass.active.eq(Active.활성)
			).fetchOne()).orElse(0);

		if (grade == 0) {

		}

		Long schoolId = Optional.ofNullable(queryFactory
			.select(school.id)
			.from(teacherClass)
			.join(teacherClass.classRoom, classRoom)
			.join(classRoom.school, school)
			.where(
				teacherClass.user.id.eq(userId),
				teacherClass.active.eq(Active.활성)
			).fetchOne()).orElse(0L);

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
					book.publishYear,
					book.imageUrl
				), bookReport.count().intValue()
			)).from(book)
			.leftJoin(bookReport)
			.on(bookReport.book.eq(book))
			.leftJoin(bookReport.studentClass, studentClass)
			.leftJoin(studentClass.classRoom, classRoom)
			.leftJoin(classRoom.school, school)
			.where(
				school.id.eq(schoolId)
					.and(classRoom.grade.eq(grade))
					.or(bookReport.isNull())
			).groupBy(book.id)
			.orderBy(bookReport.count().desc())
			.fetchFirst();
	}

	public BookMostDto findBookClassMost(Long userId) {
		Long classRoomId = Optional.ofNullable(queryFactory
			.select(teacherClass.classRoom.id)
			.from(teacherClass)
			.where(
				teacherClass.user.id.eq(userId),
				teacherClass.active.eq(Active.활성)
			).fetchOne()).orElse(0L);

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
					book.publishYear,
					book.imageUrl
				), bookReport.count().intValue()
			)).from(book)
			.leftJoin(bookReport)
			.on(bookReport.book.eq(book)
			).leftJoin(bookReport.studentClass, studentClass)
			.leftJoin(studentClass.classRoom, classRoom)
			.where(
				classRoom.id.eq(classRoomId).or(bookReport.isNull())
			).groupBy(book.id)
			.orderBy(bookReport.count().desc())
			.fetchFirst();
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
