package com.ssafy.ourdoc.global.aop;

import lombok.extern.slf4j.Slf4j;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class LogAspect {

	@Before("execution(* com.ssafy.ourdoc..controller..*(..))")
	public void logBefore(JoinPoint joinPoint) {
		String methodName = joinPoint.getSignature().getName();
		Object[] args = joinPoint.getArgs();

		StringBuilder logMessage = new StringBuilder();
		logMessage.append("\n[컨트롤러 REQUEST] ==================================================\n")
			.append("▶ Controller Method: ").append(methodName).append("\n")
			.append("▶ Request Params   : ")
			.append(args.length > 0 ? Arrays.toString(args) : "요청값 없음");

		log.info(logMessage.toString());
	}

	@AfterReturning(pointcut = "execution(* com.ssafy.ourdoc..controller..*(..))", returning = "result")
	public void logAfterMethod(JoinPoint joinPoint, Object result) {
		String methodName = joinPoint.getSignature().getName();

		StringBuilder logMessage = new StringBuilder();
		logMessage.append("\n[컨트롤러 RESPONSE]\n")
			.append("▶ Controller Method: ").append(methodName).append("\n")
			.append("▶ Response Value   : ")
			.append(result != null ? result : "응답 없음")
			.append("\n===================================================================\n\n");

		log.info(logMessage.toString());
	}
}
