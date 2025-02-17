package com.ssafy.ourdoc.global.aop;

import java.util.NoSuchElementException;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.ssafy.ourdoc.domain.bookreport.repository.BookReportRepository;
import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.global.annotation.CheckOwner;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class OwnershipAspect {

	private final BookReportRepository bookReportRepository;

	// 방법1: 매개변수 순서 강제
	// @annotation(checkOwner) : @CheckOwner가 달린 메서드를 대상으로
	// args(user, bookReportId, ..) : 메서드 파라미터 중 User, Long 타입 순서를 맞춘다
	// @Around(value = "@annotation(checkOwner) && args(user, bookReportId, ..)",
	// 	argNames = "pjp,checkOwner,user,bookReportId")
	// public Object checkOwnership(
	// 	ProceedingJoinPoint pjp,
	// 	CheckOwner checkOwner,
	// 	User user,
	// 	Long bookReportId
	// ) throws Throwable {
	// 	log.info("[AOP] CheckOwner 진입 / user={}, bookReportId={}", user, bookReportId);
	//
	// 	// (1) user / bookReportId가 제대로 들어오는지 확인
	// 	if (user == null || bookReportId == null) {
	// 		throw new IllegalArgumentException("리소스 소유권 검증 불가능: user 또는 bookReportId가 null");
	// 	}
	//
	// 	// (2) DB에서 (user.getId() / bookReportId)에 해당하는 BookReport가 있는지 확인
	// 	bookReportRepository.findByBookReport(bookReportId, user.getId())
	// 		.orElseThrow(() -> new NoSuchElementException("해당 독서록은 현재 유저 소유가 아닙니다."));
	//
	// 	// 예외 없으면 소유권 OK
	// 	return pjp.proceed();
	// }

	// 방법2: 매개변수 순서 상관 없음.
	@Around(value = "@annotation(checkOwner)")
	public Object checkOwnership(ProceedingJoinPoint pjp, CheckOwner checkOwner) throws Throwable {
		MethodSignature methodSignature = (MethodSignature)pjp.getSignature();
		Object[] args = pjp.getArgs(); // 메서드의 실제 전달된 인자값들
		String[] paramNames = methodSignature.getParameterNames(); // 파라미터 이름

		User user = null;
		Long bookReportId = null;

		for (int i = 0; i < paramNames.length; i++) {
			if ("user".equals(paramNames[i]) && args[i] instanceof User) {
				user = (User)args[i];
			}
			if ("bookReportId".equals(paramNames[i]) && args[i] instanceof Long) {
				bookReportId = (Long)args[i];
			}
		}

		if (user == null || bookReportId == null) {
			throw new IllegalArgumentException("리소스 소유권 검증 불가능: user 또는 bookReportId가 null");
		}

		bookReportRepository.findByBookReport(bookReportId, user.getId())
			.orElseThrow(() -> new NoSuchElementException("해당 독서록은 현재 유저 소유가 아닙니다."));

		return pjp.proceed();
	}
}
