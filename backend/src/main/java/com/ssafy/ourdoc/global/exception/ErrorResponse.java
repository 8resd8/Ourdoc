package com.ssafy.ourdoc.global.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorResponse {
	private LocalDateTime timestamp;
	private int status;
	private String error;
	private String message;

	public static ResponseEntity<ErrorResponse> toResponseEntity(HttpStatus httpStatus, String message) {
		return ResponseEntity
			.status(httpStatus)
			.body(ErrorResponse.builder()
				.timestamp(LocalDateTime.now())
				.status(httpStatus.value())
				.error(httpStatus.name())
				.message(message)
				.build());
	}
}
