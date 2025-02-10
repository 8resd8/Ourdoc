package com.ssafy.ourdoc.domain.bookreport.repository;

import static com.ssafy.ourdoc.domain.book.entity.QBook.*;
import static com.ssafy.ourdoc.domain.book.entity.QHomework.*;
import static com.ssafy.ourdoc.domain.bookreport.entity.QBookReport.*;
import static com.ssafy.ourdoc.domain.classroom.entity.QClassRoom.*;
import static com.ssafy.ourdoc.domain.classroom.entity.QSchool.*;
import static com.ssafy.ourdoc.domain.user.entity.QUser.*;
import static com.ssafy.ourdoc.domain.user.student.entity.QStudentClass.*;
import static com.ssafy.ourdoc.domain.user.teacher.entity.QTeacherClass.*;
import static com.ssafy.ourdoc.global.common.enums.EvaluatorType.*;

import java.time.Year;
import java.util.List;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.ourdoc.domain.bookreport.dto.BookReportDetailDto;
import com.ssafy.ourdoc.domain.bookreport.dto.QBookReportDetailDto;
import com.ssafy.ourdoc.domain.bookreport.dto.teacher.QReportTeacherDto;
import com.ssafy.ourdoc.domain.bookreport.dto.teacher.QReportTeacherDtoWithId;
import com.ssafy.ourdoc.domain.bookreport.dto.teacher.ReportTeacherDto;
import com.ssafy.ourdoc.domain.bookreport.dto.teacher.ReportTeacherDtoWithId;
import com.ssafy.ourdoc.domain.bookreport.dto.teacher.ReportTeacherRequest;
import com.ssafy.ourdoc.domain.bookreport.entity.QBookReportFeedBack;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BookReportQueryRepositoryImpl implements BookReportQueryRepository {

	private final JPAQueryFactory queryFactory;

	@Override
	public List<ReportTeacherDto> bookReports(Long userId, ReportTeacherRequest request) {
		// 교사가 지금까지 했던 반 정보
		List<Long> teacherContainClass = queryFactory
			.select(teacherClass.classRoom.id)
			.from(teacherClass)
			.where(teacherClass.user.id.eq(userId))
			.fetch();

		return queryFactory
			.select(new QReportTeacherDto(
				book.title,
				studentClass.studentNumber,
				user.name.as("studentName"),
				bookReport.createdAt,
				bookReport.approveTime
			))
			.from(bookReport)
			.join(bookReport.studentClass, studentClass)
			.join(studentClass.user, user)
			.join(studentClass.classRoom, classRoom)
			.join(classRoom.school, school)
			.join(bookReport.book, book)
			.where(
				classRoom.id.in(teacherContainClass),
				eqYear(request.year()),
				eqStudentNumber(request.studentNumber()),
				containsStudentName(request.studentName()),
				eqSchoolName(request.schoolName())
			).fetch();
	}

	@Override
	public BookReportDetailDto bookReportDetail(Long bookReportId) {
		QBookReportFeedBack aiFeedBack = new QBookReportFeedBack("aiFeedBack");
		QBookReportFeedBack teacherFeedBack = new QBookReportFeedBack("teacherFeedBack");

		return queryFactory
			.select(new QBookReportDetailDto(
				bookReport.book.title.as("bookTitle"),
				bookReport.book.author,
				bookReport.book.publisher,
				bookReport.createdAt,
				bookReport.afterContent.as("content"),
				aiFeedBack.comment.as("aiComment"),
				teacherFeedBack.comment.as("teacherComment"),
				bookReport.approveTime
			))
			.from(bookReport)
			.leftJoin(aiFeedBack).on(aiFeedBack.bookReport.id.eq(bookReport.id)
				.and(aiFeedBack.evaluatorType.eq(인공지능)))
			.leftJoin(teacherFeedBack).on(teacherFeedBack.bookReport.id.eq(bookReport.id)
				.and(teacherFeedBack.evaluatorType.eq(교사)))
			.where(bookReport.id.eq(bookReportId))
			.fetchOne();
	}

	@Override
	public List<ReportTeacherDtoWithId> bookReportsHomework(Long homeworkId) {
		return queryFactory.select(new QReportTeacherDtoWithId(
				bookReport.id,
				studentClass.studentNumber,
				user.name.as("studentName"),
				bookReport.createdAt,
				bookReport.approveTime
			))
			.from(bookReport)
			.join(bookReport.studentClass, studentClass)
			.join(studentClass.user, user)
			.join(studentClass.classRoom, classRoom)
			.join(classRoom.school, school)
			.join(bookReport.book, book)
			.join(bookReport.homework, homework)
			.where(
				bookReport.homework.id.eq(homeworkId)
			).fetch();
	}

	private BooleanExpression eqYear(Integer year) {
		return year == null ? null : classRoom.year.eq(Year.of(year));
	}

	private BooleanExpression eqStudentNumber(Integer studentNumber) {
		return studentNumber == null ? null : studentClass.studentNumber.eq(studentNumber);
	}

	private BooleanExpression containsStudentName(String studentName) {
		return (studentName != null && !studentName.isEmpty())
			? user.name.containsIgnoreCase(studentName) : null;
	}

	private BooleanExpression eqSchoolName(String schoolName) {
		return (schoolName != null && !schoolName.isEmpty())
			? school.schoolName.eq(schoolName) : null;
	}
}
