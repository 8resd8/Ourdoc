package com.ssafy.ourdoc.global.aop;

import lombok.extern.slf4j.Slf4j;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Slf4j
@Component
public class ServiceTime {

	@Around("execution(* com.ssafy.ourdoc..service..*(..))")
	public Object serviceTime(ProceedingJoinPoint joinPoint) throws Throwable {
		String fullPathClassName = joinPoint.getSignature().getDeclaringTypeName();
		String className = fullPathClassName.substring(fullPathClassName.lastIndexOf(".") + 1);
		String methodName = className + "." + joinPoint.getSignature().getName();

		Object[] args = joinPoint.getArgs();
		long startTime = System.currentTimeMillis();

		StringBuilder startLog = new StringBuilder();
		startLog.append("\n[SERVICE START]\n")
			.append("▶ 메서드 이름: ").append(methodName).append("\n")
			.append("▶ 요청 값    : ")
			.append(args.length > 0 ? Arrays.toString(args) : "No parameters");
		log.debug(startLog.toString());

		Object result;
		try {
			result = joinPoint.proceed();
		} catch (Exception e) {
			StringBuilder exceptionLog = new StringBuilder();
			exceptionLog.append("\n[SERVICE EXCEPTION] ========================================\n")
				.append("▶ Method Name: ").append(methodName).append("\n")
				.append("▶ Exception  : ").append(e.getClass().getSimpleName()).append("\n")
				.append("▶ Message    : ").append(e.getMessage()).append("\n")
				.append("============================================================");
			log.error(exceptionLog.toString(), e);
			throw e;
		}

		long endTime = System.currentTimeMillis();
		long executionTime = endTime - startTime;

		StringBuilder endLog = new StringBuilder();
		endLog.append("\n[SERVICE END]\n")
			.append("▶ 메서드 이름 : ").append(methodName).append("\n")
			.append("▶ 실행시간   : ").append(executionTime).append(" ms\n")
			.append("▶ 반환 값    : ").append(result != null ? result : "No return value");
		log.debug(endLog.toString());

		return result;
	}
}
