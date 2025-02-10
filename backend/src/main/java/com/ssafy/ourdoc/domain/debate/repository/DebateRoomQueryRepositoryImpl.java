package com.ssafy.ourdoc.domain.debate.repository;

import static com.ssafy.ourdoc.domain.debate.entity.QRoomOnline.*;

import java.util.List;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.ourdoc.domain.classroom.entity.QClassRoom;
import com.ssafy.ourdoc.domain.classroom.entity.QSchool;
import com.ssafy.ourdoc.domain.debate.dto.OnlineUserDto;
import com.ssafy.ourdoc.domain.debate.entity.QRoomOnline;
import com.ssafy.ourdoc.domain.debate.entity.RoomOnline;
import com.ssafy.ourdoc.domain.user.entity.QUser;
import com.ssafy.ourdoc.domain.user.student.entity.QStudentClass;
import com.ssafy.ourdoc.domain.user.teacher.entity.QTeacherClass;
import com.ssafy.ourdoc.global.common.enums.UserType;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DebateRoomQueryRepositoryImpl implements DebateRoomQueryRepository {
	private final JPAQueryFactory queryFactory;

	public Long countCurrentPeople(Long roomId) {
		return queryFactory.select(roomOnline.count())
			.from(roomOnline)
			.where(
				roomOnline.room.id.eq(roomId)
					.and(roomOnline.createdAt.eq(roomOnline.updatedAt))
			).fetchOne();
	}

	public List<RoomOnline> findAllActiveByRoomId(Long roomId) {
		return queryFactory.selectFrom(roomOnline)
			.where(
				roomOnline.room.id.eq(roomId)
					.and(roomOnline.createdAt.eq(roomOnline.updatedAt))
			).fetch();
	}

	public List<OnlineUserDto> findOnlineUsersByRoomId(Long roomId) {
		QRoomOnline roomOnline = QRoomOnline.roomOnline;
		QUser user = QUser.user;

		QTeacherClass teacherClass = QTeacherClass.teacherClass;
		QClassRoom teacherClassRoom = new QClassRoom("teacherClassRoom");
		QSchool teacherSchool = new QSchool("teacherSchool");

		QStudentClass studentClass = QStudentClass.studentClass;
		QClassRoom studentClassRoom = new QClassRoom("studentClassRoom");
		QSchool studentSchool = new QSchool("studentSchool");

		return queryFactory
			.select(
				Projections.constructor(
					OnlineUserDto.class,
					Expressions.stringTemplate(
						"CASE WHEN {0} = {1} THEN {2} ELSE {3} END",
						roomOnline.user.userType,
						Expressions.constant(UserType.교사),
						teacherSchool.schoolName,
						studentSchool.schoolName
					),
					roomOnline.user.name,
					roomOnline.user.userType,
					roomOnline.user.profileImagePath
				)
			).from(roomOnline)
			.leftJoin(roomOnline.user, user)
			.leftJoin(teacherClass).on(teacherClass.user.id.eq(roomOnline.user.id))
			.leftJoin(teacherClass.classRoom, teacherClassRoom)
			.leftJoin(teacherClassRoom.school, teacherSchool)
			.leftJoin(studentClass).on(studentClass.user.id.eq(roomOnline.user.id))
			.leftJoin(studentClass.classRoom, studentClassRoom)
			.leftJoin(studentClassRoom.school, studentSchool)
			.where(
				roomOnline.room.id.eq(roomId),
				roomOnline.createdAt.eq(roomOnline.updatedAt)
			).fetch();
	}
}
