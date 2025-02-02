package com.ssafy.ourdoc.global.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ssafy.ourdoc.global.exception.LoginFailedException;

/**
 * 로그인 관련 예외를 처리하는 핸들러
 */
@RestControllerAdvice
public class LoginExceptionHandler {

	@ExceptionHandler
	public ResponseEntity<String> handleLoginFailedException(LoginFailedException e) {
		return ResponseEntity
			.status(HttpStatus.UNAUTHORIZED) // HTTP 401 Unauthorized
			.body(e.getMessage()); // 예외 메시지를 응답에 포함
	}
}
