package com.ssafy.ourdoc.domain.user.teacher.dto;

public record QrResponseDto(
	String qrImageBase64, // Base64로 인코딩된 QR 이미지
	String url
) {
}
