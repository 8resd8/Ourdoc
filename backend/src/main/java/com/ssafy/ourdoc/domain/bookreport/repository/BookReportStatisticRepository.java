package com.ssafy.ourdoc.domain.bookreport.repository;

import static com.ssafy.ourdoc.domain.bookreport.entity.QBookReport.*;
import static com.ssafy.ourdoc.domain.bookreport.entity.QBookReportFeedBack.*;
import static com.ssafy.ourdoc.domain.classroom.entity.QClassRoom.*;
import static com.ssafy.ourdoc.domain.user.student.entity.QStudentClass.*;
import static com.ssafy.ourdoc.domain.user.teacher.entity.QTeacherClass.*;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.ourdoc.domain.bookreport.dto.BookReportDailyStatisticsDto;
import com.ssafy.ourdoc.domain.bookreport.dto.BookReportMonthlyStatisticsDto;
import com.ssafy.ourdoc.domain.bookreport.dto.BookReportMyRankDto;
import com.ssafy.ourdoc.domain.bookreport.dto.BookReportRankDto;
import com.ssafy.ourdoc.global.common.enums.Active;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class BookReportStatisticRepository {

	private final JPAQueryFactory queryFactory;

	public long myBookReportsCount(Long userId, int grade) {
		return Optional.ofNullable(
			queryFactory.select(bookReport.count())
				.from(bookReport)
				.join(bookReport.studentClass, studentClass)
				.join(studentClass.classRoom, classRoom)
				.where(
					studentClass.user.id.eq(userId),
					classRoom.grade.eq(grade)
				).fetchOne()
		).orElse(0L);
	}

	public double classAverageBookReportsCount(Long userId, int grade) {
		Long classRoomId = Optional.ofNullable(queryFactory
			.select(studentClass.classRoom.id)
			.from(studentClass)
			.join(studentClass.classRoom, classRoom)
			.where(
				studentClass.user.id.eq(userId),
				classRoom.grade.eq(grade)
			).orderBy(classRoom.createdAt.desc())
			.fetchFirst()).orElse(0L);

		if (classRoomId == 0L) {
			return 0.0;
		}

		long count = Optional.ofNullable(
				queryFactory
					.select(bookReport.count())
					.from(bookReport)
					.join(bookReport.studentClass, studentClass)
					.join(studentClass.classRoom, classRoom)
					.where(
						classRoom.id.eq(classRoomId)
					).fetchOne())
			.orElse(0L);

		if (count == 0L) {
			return 0.0;
		}

		long studentCount = Optional.ofNullable(
			queryFactory
				.select(studentClass.count())
				.from(studentClass)
				.join(studentClass.classRoom, classRoom)
				.where(classRoom.id.eq(classRoomId))
				.fetchOne()
		).orElse(0L);

		return (studentCount == 0) ? 0.0 : (double)count / studentCount;
	}

	public long classHighestBookReportCount(Long userId, int grade) {
		Long classRoomId = Optional.ofNullable(queryFactory
			.select(studentClass.classRoom.id)
			.from(studentClass)
			.join(studentClass.classRoom, classRoom)
			.where(
				studentClass.user.id.eq(userId),
				classRoom.grade.eq(grade)
			).orderBy(classRoom.createdAt.desc())
			.fetchFirst()).orElse(0L);

		if (classRoomId == 0L) {
			return 0L;
		}

		return Optional.ofNullable(
				queryFactory
					.select(bookReport.count())
					.from(bookReport)
					.join(bookReport.studentClass, studentClass)
					.join(studentClass.classRoom, classRoom)
					.where(
						classRoom.id.eq(classRoomId)
					).groupBy(studentClass.id)
					.orderBy(bookReport.count().desc())
					.limit(1)
					.fetchOne())
			.orElse(0L);
	}

	public List<BookReportMonthlyStatisticsDto> myMonthlyBookReportCount(Long userId, int grade) {
		Year year = Optional.ofNullable(
				queryFactory
					.select(classRoom.year)
					.from(studentClass)
					.join(studentClass.classRoom, classRoom)
					.where(
						studentClass.user.id.eq(userId),
						classRoom.grade.eq(grade)
					).fetchFirst())
			.orElse(Year.of(0));

		List<Tuple> tuples = queryFactory
			.select(monthExpression, bookReport.count())
			.from(bookReport)
			.join(bookReport.studentClass, studentClass)
			.join(studentClass.classRoom, classRoom)
			.where(
				studentClass.user.id.eq(userId),
				classRoom.grade.eq(grade),
				classRoom.year.eq(year)
			).groupBy(monthExpression)
			.fetch();

		return getMonthlyBookReportCountDtos(tuples);
	}

	public List<BookReportMonthlyStatisticsDto> classMonthlyBookReportCount(Long userId) {
		Long classRoomId = findActiveClassRoomId(userId);

		List<Tuple> tuples = queryFactory
			.select(monthExpression, bookReport.count())
			.from(bookReport)
			.join(bookReport.studentClass, studentClass)
			.join(studentClass.classRoom, classRoom)
			.where(
				classRoom.id.eq(classRoomId)
			).groupBy(monthExpression)
			.fetch();

		return getMonthlyBookReportCountDtos(tuples);
	}

	private List<BookReportMonthlyStatisticsDto> getMonthlyBookReportCountDtos(List<Tuple> tuples) {
		Map<Integer, Long> reportCountByMonth = tuples.stream()
			.collect(Collectors.toMap(
				tuple -> tuple.get(monthExpression),
				tuple -> tuple.get(bookReport.count())
			));

		List<BookReportMonthlyStatisticsDto> monthlyReports = new ArrayList<>();
		for (int m = 3; m <= 12; m++) {
			int count = reportCountByMonth.getOrDefault(m, 0L).intValue();
			monthlyReports.add(new BookReportMonthlyStatisticsDto(m, count));
		}

		for (int m = 1; m <= 2; m++) {
			int count = reportCountByMonth.getOrDefault(m, 0L).intValue();
			monthlyReports.add(new BookReportMonthlyStatisticsDto(m, count));
		}

		return monthlyReports;
	}

	public List<BookReportDailyStatisticsDto> myDailyBookReportCount(Long userId, int grade, int month) {
		Year year = Optional.ofNullable(
				queryFactory
					.select(classRoom.year)
					.from(studentClass)
					.join(studentClass.classRoom, classRoom)
					.where(
						studentClass.user.id.eq(userId),
						classRoom.grade.eq(grade)
					).fetchFirst())
			.orElse(Year.of(0));

		List<Tuple> tuples = queryFactory
			.select(dayExpression, bookReport.count())
			.from(bookReport)
			.join(bookReport.studentClass, studentClass)
			.join(studentClass.classRoom, classRoom)
			.where(
				studentClass.user.id.eq(userId),
				classRoom.grade.eq(grade),
				classRoom.year.eq(year),
				bookReport.createdAt.month().eq(month)
			).groupBy(dayExpression)
			.fetch();

		return getDailyBookReportCountDtos(tuples);
	}

	public List<BookReportDailyStatisticsDto> classDailyBookReportCount(Long userId, int month) {
		Year year = Optional.ofNullable(
				queryFactory
					.select(classRoom.year)
					.from(teacherClass)
					.join(teacherClass.classRoom, classRoom)
					.where(
						teacherClass.user.id.eq(userId),
						teacherClass.active.eq(Active.활성)
					).fetchOne())
			.orElse(Year.of(0));

		Long classRoomId = findActiveClassRoomId(userId);

		List<Tuple> tuples = queryFactory
			.select(dayExpression, bookReport.count())
			.from(bookReport)
			.join(bookReport.studentClass, studentClass)
			.join(studentClass.classRoom, classRoom)
			.where(
				classRoom.id.eq(classRoomId),
				classRoom.year.eq(year),
				bookReport.createdAt.month().eq(month)
			).groupBy(dayExpression)
			.fetch();

		return getDailyBookReportCountDtos(tuples);
	}

	public List<BookReportRankDto> bookReportRank(Long userId) {
		Long classRoomId = findActiveClassRoomId(userId);

		return queryFactory
			.select(Projections.constructor(
				BookReportRankDto.class,
				studentClass.studentNumber,
				studentClass.user.name,
				bookReport.count().intValue(),
				Expressions.constant(0),
				studentClass.user.profileImagePath
			)).from(studentClass)
			.join(studentClass.classRoom, classRoom)
			.leftJoin(bookReport)
			.on(
				bookReport.studentClass.eq(studentClass)
			).where(
				classRoom.id.eq(classRoomId)
			).groupBy(studentClass.studentNumber)
			.orderBy(bookReport.count().desc())
			.fetch();
	}

	public List<BookReportMyRankDto> myBookReportRank(Long userId) {
		Long classRoomId = Optional.ofNullable(queryFactory
			.select(studentClass.classRoom.id)
			.from(studentClass)
			.where(
				studentClass.user.id.eq(userId),
				studentClass.active.eq(Active.활성)
			).fetchOne()).orElse(0L);

		return queryFactory
			.select(Projections.constructor(
				BookReportMyRankDto.class,
				studentClass.user.id,
				bookReport.count().intValue(),
				Expressions.constant(0)
			)).from(studentClass)
			.join(studentClass.classRoom, classRoom)
			.leftJoin(bookReport)
			.on(
				bookReport.studentClass.eq(studentClass)
			).where(
				classRoom.id.eq(classRoomId)
			).groupBy(studentClass.studentNumber)
			.orderBy(bookReport.count().desc())
			.fetch();
	}

	public String getLatestAiFeedback(Long userId) {
		Long studentClassId = queryFactory
			.select(studentClass.id)
			.from(studentClass)
			.where(studentClass.user.id.eq(userId), studentClass.active.eq(Active.활성))
			.fetchOne();

		return queryFactory
			.select(bookReportFeedBack.comment)
			.from(bookReport)
			.join(bookReport.bookReportFeedBack, bookReportFeedBack)
			.where(bookReport.studentClass.id.eq(studentClassId))
			.orderBy(bookReport.id.desc())
			.limit(1)
			.fetchOne();
	}

	private List<BookReportDailyStatisticsDto> getDailyBookReportCountDtos(List<Tuple> tuples) {
		Map<Integer, Long> reportCountByDay = tuples.stream()
			.collect(Collectors.toMap(
				tuple -> tuple.get(dayExpression),
				tuple -> tuple.get(bookReport.count())
			));

		List<BookReportDailyStatisticsDto> dailyReports = new ArrayList<>();
		for (int d = 1; d <= 31; d++) {
			int count = reportCountByDay.getOrDefault(d, 0L).intValue();
			if (count > 0) {
				dailyReports.add(new BookReportDailyStatisticsDto(d, count));
			}
		}

		return dailyReports;
	}

	private final NumberExpression<Integer> monthExpression = Expressions.numberTemplate(
		Integer.class, "function('MONTH', {0})", bookReport.createdAt
	);

	private final NumberExpression<Integer> dayExpression = Expressions.numberTemplate(
		Integer.class, "function('DAY', {0})", bookReport.createdAt
	);

	private Long findActiveClassRoomId(Long userId) {
		return Optional.ofNullable(queryFactory
			.select(teacherClass.classRoom.id)
			.from(teacherClass)
			.where(
				teacherClass.user.id.eq(userId),
				teacherClass.active.eq(Active.활성)
			).fetchOne()).orElse(0L);
	}

}