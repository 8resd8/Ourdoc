package com.ssafy.ourdoc.global.exception.handler;

import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ssafy.ourdoc.domain.notification.exception.SubscribeException;
import com.ssafy.ourdoc.global.common.response.ErrorResponse;
import com.ssafy.ourdoc.global.exception.ForbiddenException;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleException(Exception ex, HttpServletRequest request) {
		return ErrorResponse.toResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(),
			request.getRequestURI());
	}

	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex,
		HttpServletRequest request) {
		return ErrorResponse.toResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage(), request.getRequestURI());
	}

	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleForbiddenException(ForbiddenException ex, HttpServletRequest request) {
		return ErrorResponse.toResponseEntity(HttpStatus.FORBIDDEN, ex.getMessage(), request.getRequestURI());
	}

	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleNoSuchElementException(NoSuchElementException ex,
		HttpServletRequest request) {
		return ErrorResponse.toResponseEntity(HttpStatus.NOT_FOUND, ex.getMessage(), request.getRequestURI());
	}

	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleSubscribeExceptionException(SubscribeException ex,
		HttpServletRequest request) {
		return ErrorResponse.toResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage(), request.getRequestURI());
	}

	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex,
		HttpServletRequest request) {
		return ErrorResponse.toResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage(), request.getRequestURI());
	}

	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleMissingRequestException(HttpMessageNotReadableException ex,
		HttpServletRequest request) {
		return ErrorResponse.toResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage(), request.getRequestURI());
	}

	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleExpiredJwtException(ExpiredJwtException ex,
		HttpServletRequest request) {
		return ErrorResponse.toResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage(), request.getRequestURI());
	}
}
