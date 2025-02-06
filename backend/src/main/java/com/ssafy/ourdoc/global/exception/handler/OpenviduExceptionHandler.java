package com.ssafy.ourdoc.global.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ssafy.ourdoc.global.common.response.ErrorResponse;
import com.ssafy.ourdoc.global.integration.openvidu.exception.OpenviduSessionFailException;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class OpenviduExceptionHandler {
	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleOpenviduSessionFailException(OpenviduSessionFailException ex, HttpServletRequest request) {
		return ErrorResponse.toResponseEntity(HttpStatus.SERVICE_UNAVAILABLE, ex.getMessage(), request.getRequestURI());
	}
}
