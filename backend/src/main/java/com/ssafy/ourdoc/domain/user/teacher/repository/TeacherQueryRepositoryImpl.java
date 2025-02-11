package com.ssafy.ourdoc.domain.user.teacher.repository;

import static com.ssafy.ourdoc.domain.classroom.entity.QClassRoom.*;
import static com.ssafy.ourdoc.domain.classroom.entity.QSchool.*;
import static com.ssafy.ourdoc.domain.user.entity.QUser.*;
import static com.ssafy.ourdoc.domain.user.teacher.entity.QTeacher.*;
import static com.ssafy.ourdoc.domain.user.teacher.entity.QTeacherClass.*;
import static com.ssafy.ourdoc.global.common.enums.Active.*;
import static com.ssafy.ourdoc.global.common.enums.EmploymentStatus.*;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import com.ssafy.ourdoc.domain.classroom.entity.School;
import com.ssafy.ourdoc.domain.user.dto.TeacherQueryDto;
import com.ssafy.ourdoc.domain.user.dto.TeacherVerificationDto;
import com.ssafy.ourdoc.domain.user.entity.QUser;
import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.domain.user.teacher.dto.TeacherProfileResponseDto;
import com.ssafy.ourdoc.domain.user.teacher.dto.TeacherProfileUpdateRequest;
import com.ssafy.ourdoc.domain.user.teacher.entity.QTeacher;
import com.ssafy.ourdoc.global.common.enums.Active;
import com.ssafy.ourdoc.global.common.enums.AuthStatus;
import com.ssafy.ourdoc.global.common.enums.EmploymentStatus;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
@Transactional
public class TeacherQueryRepositoryImpl implements TeacherQueryRepository {

	private final JPAQueryFactory queryFactory;

	@Override
	public TeacherQueryDto getTeacherLoginDto(Long userId) {
		return queryFactory
			.select(Projections.constructor(
				TeacherQueryDto.class,
				school.schoolName.coalesce("null"),
				classRoom.grade,
				classRoom.classNumber
			))
			.from(teacherClass)
			.join(classRoom).on(teacherClass.classRoom.id.eq(classRoom.id))
			.join(school).on(classRoom.school.id.eq(school.id))
			.where(teacherClass.user.id.eq(userId))
			.fetchOne();
	}

	@Override
	public TeacherProfileResponseDto findTeacherProfileByUserId(Long userId) {
		return queryFactory
			.select(Projections.constructor(TeacherProfileResponseDto.class,
				user.profileImagePath,
				user.name,
				user.loginId,
				teacher.email,
				classRoom.school.schoolName,
				classRoom.grade,
				classRoom.classNumber,
				teacher.phone
			))
			.from(user)
			.join(teacher).on(teacher.user.id.eq(user.id))
			.join(teacherClass).on(teacherClass.user.id.eq(user.id))
			.join(teacherClass.classRoom, classRoom)
			.join(classRoom.school, school)
			.where(user.id.eq(userId),
				teacherClass.active.eq(활성))
			.fetchOne();
	}

	@Override
	public Page<TeacherVerificationDto> findPendingTeachers(AuthStatus authStatus, Pageable pageable) {
		List<TeacherVerificationDto> content = queryFactory
			.select(Projections.constructor(TeacherVerificationDto.class,
				teacher.id,
				user.loginId,
				user.name,
				teacher.email,
				teacher.phone,
				teacher.certificateImageUrl
			))
			.from(teacher)
			.join(user).on(teacher.user.id.eq(user.id))
			.where(teacher.employmentStatus.eq(비재직), user.active.eq(활성))
			.orderBy(teacher.createdAt.asc())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		long totalCount = queryFactory
			.select(teacher.count())
			.from(teacher)
			.where(teacher.employmentStatus.eq(비재직), user.active.eq(활성))
			.fetchOne();

		return new PageImpl<>(content, pageable, totalCount);
	}

	@Override
	public void approveTeacher(Long teacherId) {
		queryFactory.update(teacher)
			.set(teacher.employmentStatus, 재직)
			.set(teacher.certificateTime, LocalDateTime.now())
			.set(teacher.updatedAt, LocalDateTime.now())
			.where(teacher.id.eq(teacherId).and(teacher.employmentStatus.eq(비재직)))
			.execute();
	}

	@Override
	public void updateTeacherProfile(User user, TeacherProfileUpdateRequest request) {
		JPAUpdateClause userUpdate = queryFactory.update(QUser.user)
			.where(QUser.user.id.eq(user.getId()));

		boolean isUserUpdated = false;

		if (request.name() != null && !request.name().isEmpty()) {
			userUpdate.set(QUser.user.name, request.name());
			isUserUpdated = true;
		}

		if (isUserUpdated) {
			userUpdate.execute();
		}

		JPAUpdateClause teacherUpdate = queryFactory.update(teacher)
			.where(teacher.user.eq(user));

		boolean isTeacherUpdated = false;

		if (request.email() != null && !request.email().isEmpty()) {
			teacherUpdate.set(teacher.email, request.email());
			isTeacherUpdated = true;
		}
		if (request.phone() != null && !request.phone().isEmpty()) {
			teacherUpdate.set(teacher.phone, request.phone());
			isTeacherUpdated = true;
		}

		if (isTeacherUpdated) {
			teacherUpdate.execute();
		}
	}
}
