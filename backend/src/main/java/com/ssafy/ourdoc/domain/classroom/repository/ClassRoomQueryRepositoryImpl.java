package com.ssafy.ourdoc.domain.classroom.repository;

import static com.ssafy.ourdoc.domain.classroom.entity.QClassRoom.*;
import static com.ssafy.ourdoc.domain.classroom.entity.QSchool.*;
import static com.ssafy.ourdoc.domain.user.entity.QUser.*;
import static com.ssafy.ourdoc.domain.user.student.entity.QStudentClass.*;
import static com.ssafy.ourdoc.domain.user.teacher.entity.QTeacherClass.*;

import java.time.Year;
import java.util.List;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.ourdoc.domain.classroom.dto.QSchoolClassDto;
import com.ssafy.ourdoc.domain.classroom.dto.SchoolClassDto;
import com.ssafy.ourdoc.domain.classroom.dto.teacher.QTeacherRoomStudentDto;
import com.ssafy.ourdoc.domain.classroom.dto.teacher.QTeachersRoomDto;
import com.ssafy.ourdoc.domain.classroom.dto.teacher.TeacherClassRequest;
import com.ssafy.ourdoc.domain.classroom.dto.teacher.TeacherRoomStudentDto;
import com.ssafy.ourdoc.domain.classroom.dto.teacher.TeachersRoomDto;
import com.ssafy.ourdoc.domain.classroom.entity.ClassRoom;
import com.ssafy.ourdoc.global.common.enums.Active;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ClassRoomQueryRepositoryImpl implements ClassRoomQueryRepository {

	private final JPAQueryFactory queryFactory;

	@Override
	public List<ClassRoom> findActiveClassBySchoolAndGrade(Long schoolId, int grade) {
		return queryFactory.selectFrom(classRoom)
			.join(teacherClass)
			.on(teacherClass.classRoom.id.eq(classRoom.id))
			.where(
				classRoom.school.id.eq(schoolId),
				classRoom.grade.eq(grade),
				teacherClass.active.eq(Active.활성)
			)
			.fetch();
	}

	@Override
	public List<SchoolClassDto> findByTeacher(Long userId) {
		return queryFactory.select(new QSchoolClassDto(
				classRoom.id,
				school.schoolName,
				classRoom.grade,
				classRoom.classNumber,
				classRoom.year,
				studentClass.count().intValue()))
			.from(classRoom)
			.join(classRoom.school, school)
			.join(teacherClass).on(teacherClass.classRoom.eq(classRoom))
			.leftJoin(studentClass).on(studentClass.classRoom.eq(classRoom))
			.where(teacherClassEq(userId))
			.groupBy(classRoom.id)
			.fetch();
	}

	@Override
	public List<SchoolClassDto> findByTeacherAndYear(Long userId, Year year) {
		return queryFactory.select(new QSchoolClassDto(
				classRoom.id,
				school.schoolName,
				classRoom.grade,
				classRoom.classNumber,
				classRoom.year,
				studentClass.count().intValue()))
			.from(classRoom)
			.join(classRoom.school, school)
			.join(teacherClass).on(teacherClass.classRoom.eq(classRoom))
			.leftJoin(studentClass).on(studentClass.classRoom.eq(classRoom))
			.where(
				teacherClassEq(userId),
				classRoom.year.eq(year)
			)
			.groupBy(classRoom.id)
			.fetch();
	}

	@Override
	public List<TeachersRoomDto> findByTeachersRoom(Long userId, TeacherClassRequest request) {
		return queryFactory
			.select(new QTeachersRoomDto(
				classRoom.school.schoolName,
				classRoom.year,
				classRoom.grade,
				classRoom.classNumber
			))
			.from(teacherClass)
			.join(teacherClass.classRoom, classRoom)
			.where(teacherClassEq(userId), classEq(request.classId()))
			.fetch();
	}

	@Override
	public List<TeacherRoomStudentDto> findByTeachersRoomStudent(Long userId, Long classId) {
		return queryFactory
			.select(new QTeacherRoomStudentDto(
				user.name.as("studentName"),
				studentClass.studentNumber
			))
			.from(studentClass)
			.where(classEq(classId))
			.orderBy(studentClass.studentNumber.asc())
			.fetch();
	}

	@Override
	public List<SchoolClassDto> findByStudent(Long userId) {
		return queryFactory.select(new QSchoolClassDto(
				classRoom.id,
				school.schoolName,
				classRoom.grade,
				classRoom.classNumber,
				classRoom.year,
				studentClass.count().intValue()))
			.from(classRoom)
			.join(classRoom.school, school)
			.join(studentClass).on(studentClass.classRoom.eq(classRoom))
			.leftJoin(teacherClass).on(teacherClass.classRoom.eq(classRoom))
			.where(studentClassEq(userId))
			.groupBy(classRoom.id)
			.fetch();
	}

	private BooleanExpression classEq(Long classId) {
		return studentClass.classRoom.id.eq(classId);
	}

	private BooleanExpression teacherClassEq(Long userId) {
		return teacherClass.user.id.eq(userId);
	}

	private static BooleanExpression studentClassEq(Long userId) {
		return studentClass.user.id.eq(userId);
	}
}
