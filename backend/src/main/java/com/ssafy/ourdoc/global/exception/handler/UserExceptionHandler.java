package com.ssafy.ourdoc.global.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ssafy.ourdoc.global.common.response.ErrorResponse;
import com.ssafy.ourdoc.global.exception.UserFailedException;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class UserExceptionHandler {

	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleUserFailedException(UserFailedException e, HttpServletRequest request) {
		return ErrorResponse.toResponseEntity(HttpStatus.UNAUTHORIZED, e.getMessage(), request.getRequestURI());
	}
}
