package com.ssafy.ourdoc.global.exception;

public class LoginFailedException extends RuntimeException {
	public LoginFailedException(String message) {
		super(message);
	}
}
