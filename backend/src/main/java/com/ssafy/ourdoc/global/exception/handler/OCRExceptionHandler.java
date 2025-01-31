package com.ssafy.ourdoc.global.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ssafy.ourdoc.global.exception.ErrorResponse;
import com.ssafy.ourdoc.global.integration.ocr.exception.OCRFailException;
import com.ssafy.ourdoc.global.integration.ocr.exception.OCRNoImageException;

@RestControllerAdvice
public class OCRExceptionHandler {

	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handOCRFailException(OCRFailException ex) {
		return ErrorResponse.toResponseEntity(HttpStatus.SERVICE_UNAVAILABLE, ex.getMessage());
	}

	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handOCRNoImageException(OCRNoImageException ex) {
		return ErrorResponse.toResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage());
	}
}
