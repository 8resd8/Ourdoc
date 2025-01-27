package com.ssafy.ourdoc.global.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ssafy.ourdoc.global.exception.FileUploadException;

@RestControllerAdvice
public class FileExceptionHandler {

	@ExceptionHandler
	public ResponseEntity<String> handleFileUploadException(FileUploadException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
	}
}
