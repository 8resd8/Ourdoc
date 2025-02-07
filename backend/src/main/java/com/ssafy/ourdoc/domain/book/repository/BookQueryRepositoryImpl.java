package com.ssafy.ourdoc.domain.book.repository;

import static com.ssafy.ourdoc.domain.book.entity.QBook.*;
import static com.ssafy.ourdoc.domain.book.entity.QBookRecommend.*;
import static com.ssafy.ourdoc.domain.classroom.entity.QClassRoom.*;
import static com.ssafy.ourdoc.domain.user.student.entity.QStudentClass.*;
import static com.ssafy.ourdoc.domain.user.teacher.entity.QTeacherClass.*;

import java.util.List;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.ourdoc.domain.book.entity.Book;
import com.ssafy.ourdoc.domain.book.entity.BookRecommend;
import com.ssafy.ourdoc.domain.classroom.entity.ClassRoom;
import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.global.common.enums.Active;
import com.ssafy.ourdoc.global.common.enums.UserType;

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

	@Override
	public List<BookRecommend> findRecommendBookList(User user) {
		if (user.getUserType().equals(UserType.교사)) {
			return getTeacherBookRecommends(user);
		}

		if (user.getUserType().equals(UserType.학생)) {
			return getStudentBookRecommends(user);
		}
		return List.of();
	}

	private List<BookRecommend> getStudentBookRecommends(User user) {
		ClassRoom userClassRoom = queryFactory.selectFrom(studentClass.classRoom)
			.where(studentClass.user.eq(user), studentClass.active.eq(Active.활성))
			.fetchOne();
		if (userClassRoom == null) {
			return List.of();
		}
		Long schoolId = userClassRoom.getSchool().getId();
		int grade = userClassRoom.getGrade();

		List<ClassRoom> classRooms = queryFactory.selectFrom(studentClass.classRoom)
			.where(studentClass.classRoom.school.id.eq(schoolId), studentClass.classRoom.grade.eq(grade),
				studentClass.active.eq(Active.활성))
			.fetch();
		return queryFactory.selectFrom(bookRecommend)
			.join(bookRecommend.classRoom, classRoom)
			.where(classRoom.in(classRooms))
			.fetch();
	}

	private List<BookRecommend> getTeacherBookRecommends(User user) {
		ClassRoom userClassRoom = queryFactory.selectFrom(teacherClass.classRoom)
			.where(teacherClass.user.eq(user), teacherClass.active.eq(Active.활성))
			.fetchOne();
		if (userClassRoom == null) {
			return List.of();
		}
		Long schoolId = userClassRoom.getSchool().getId();
		int grade = userClassRoom.getGrade();

		List<ClassRoom> classRooms = queryFactory.selectFrom(teacherClass.classRoom)
			.where(teacherClass.classRoom.school.id.eq(schoolId), teacherClass.classRoom.grade.eq(grade),
				teacherClass.active.eq(Active.활성))
			.fetch();

		return queryFactory.selectFrom(bookRecommend)
			.join(bookRecommend.classRoom, classRoom)
			.where(classRoom.in(classRooms))
			.fetch();
	}
}
