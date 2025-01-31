package com.ssafy.ourdoc.global.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ssafy.ourdoc.global.exception.ErrorResponse;
import com.ssafy.ourdoc.global.integration.ocr.exception.OCRFailException;
import com.ssafy.ourdoc.global.integration.ocr.exception.OCRNoImageException;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class OCRExceptionHandler {

	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handOCRFailException(OCRFailException ex, HttpServletRequest request) {
		return ErrorResponse.toResponseEntity(HttpStatus.SERVICE_UNAVAILABLE, ex.getMessage(), request.getRequestURI());
	}

	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handOCRNoImageException(OCRNoImageException ex, HttpServletRequest request) {
		return ErrorResponse.toResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage(), request.getRequestURI());
	}
}
