package com.ssafy.ourdoc.domain.award.repository;

import static com.querydsl.core.types.Projections.*;
import static com.ssafy.ourdoc.domain.award.entity.QAward.*;
import static com.ssafy.ourdoc.domain.classroom.entity.QClassRoom.*;
import static com.ssafy.ourdoc.domain.user.student.entity.QStudentClass.*;
import static com.ssafy.ourdoc.domain.user.teacher.entity.QTeacherClass.*;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.ourdoc.domain.award.dto.AwardDto;
import com.ssafy.ourdoc.domain.award.dto.teacher.AwardTeacherDto;
import com.ssafy.ourdoc.domain.award.dto.teacher.AwardTeacherRequest;
import com.ssafy.ourdoc.domain.user.entity.QUser;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AwardQueryRepositoryImpl implements AwardQueryRepository {

	private final JPAQueryFactory queryFactory;

	@Override
	public List<AwardDto> findAllAwardByUserId(Long userId) {
		return queryFactory
			.select(constructor(AwardDto.class,
				award.id,
				award.imagePath,
				award.title,
				award.createdAt))
			.from(award)
			.where(userEq(userId))
			.fetch();
	}

	private BooleanExpression userEq(Long userId) {
		return award.user.id.eq(userId);
	}

	@Override
	public Optional<AwardDto> findAwardByUserId(Long userId, Long awardId) {
		return Optional.ofNullable(queryFactory
			.select(constructor(AwardDto.class,
				award.id,
				award.imagePath,
				award.title,
				award.createdAt))
			.from(award)
			.where(userEq(userId), award.id.eq(awardId))
			.fetchOne());
	}

	@Override
	public List<AwardTeacherDto> findTeacherClassAwards(Long teacherUserId, AwardTeacherRequest request) {
		// 교사가 소속한 반의 모든 학생들의 상장 조회
		QUser teacherUser = new QUser("teacherUser"); // 교사 User
		QUser studentUser = new QUser("studentUser"); // 학생 User
		return queryFactory
			.select(Projections.constructor(
				AwardTeacherDto.class,
				award.id,
				award.title,
				award.imagePath,
				award.createdAt
			))
			.from(teacherClass)
			.join(teacherClass.user, teacherUser)
			.join(teacherClass.classRoom, classRoom)
			.join(studentClass.classRoom, classRoom)
			.join(studentClass.user, studentUser)
			.join(award.user, studentUser)
			.where(buildWhereCondition(teacherUserId, request))
			.fetch();
	}

	private BooleanBuilder buildWhereCondition(Long teacherUserId, AwardTeacherRequest request) {
		BooleanBuilder builder = new BooleanBuilder();
		// 교사 ID 동일
		builder.and(teacherClass.user.id.eq(teacherUserId));

		// 클래스 ID 동일
		builder.and(classRoom.id.eq(request.classId()));

		// 학생 ID 조건 (선택 조건)
		if (request.studentLoginId() != null) {
			builder.and(studentClass.user.loginId.eq(request.studentLoginId()));
		}

		return builder;
	}

}
