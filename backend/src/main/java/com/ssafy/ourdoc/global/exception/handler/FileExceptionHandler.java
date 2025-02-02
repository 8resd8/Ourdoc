package com.ssafy.ourdoc.global.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ssafy.ourdoc.global.common.response.ErrorResponse;
import com.ssafy.ourdoc.global.integration.s3.exception.FileUploadException;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class FileExceptionHandler {

	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleFileUploadException(FileUploadException ex, HttpServletRequest request) {
		return ErrorResponse.toResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage(), request.getRequestURI());
	}
}
