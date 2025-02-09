package com.ssafy.ourdoc.domain.user.teacher.dto;

public record VerificateAffiliationChangeRequest(
	String studentLoginId,
	boolean isApproved
) {
}
