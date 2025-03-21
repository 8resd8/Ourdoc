package com.ssafy.ourdoc.domain.user.repository;

import static com.ssafy.ourdoc.domain.user.entity.QUser.*;
import static com.ssafy.ourdoc.domain.user.student.entity.QStudentClass.*;
import static com.ssafy.ourdoc.domain.user.teacher.entity.QTeacherClass.*;

import java.time.LocalDateTime;

import org.springframework.transaction.annotation.Transactional;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.ourdoc.domain.user.entity.QUser;
import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.global.common.enums.Active;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserQueryRepositoryImpl implements UserQueryRepository {

	private final JPAQueryFactory queryFactory;

	// 학생의 담당교사 찾기
	@Override
	public User findTeachersByStudentClassId(Long studentUserId) {
		// 학생의 classId 가져오기
		Long classId = queryFactory.select(studentClass.classRoom.id)
			.from(studentClass)
			.where(studentClass.user.id.eq(studentUserId), studentClass.active.eq(Active.활성))
			.fetchOne();

		// classId로 해당 클래스의 교사 조회
		return queryFactory.select(user)
			.from(teacherClass)
			.join(user).on(teacherClass.user.id.eq(user.id))
			.where(teacherClass.classRoom.id.eq(classId))
			.fetchOne();
	}

	// 비밀번호 일치 여부 확인
	@Override
	public String findPasswordById(Long userId) {
		return queryFactory
			.select(user.password)
			.from(user)
			.where(user.id.eq(userId))
			.fetchOne();
	}

	@Override
	@Transactional
	public void updateProfileImage(User user, String profileImageUrl) {
		queryFactory.update(QUser.user)
			.set(QUser.user.profileImagePath, profileImageUrl)
			.set(QUser.user.updatedAt, LocalDateTime.now())
			.where(QUser.user.id.eq(user.getId()))
			.execute();
	}

	@Override
	@Transactional
	public void updatePassword(User user, String newHashedPassword) {
		queryFactory.update(QUser.user)
			.set(QUser.user.password, newHashedPassword)
			.set(QUser.user.updatedAt, LocalDateTime.now())
			.where(QUser.user.id.eq(user.getId()))
			.execute();
	}
}
