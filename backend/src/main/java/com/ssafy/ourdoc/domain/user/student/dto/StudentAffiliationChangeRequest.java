package com.ssafy.ourdoc.domain.user.student.dto;

public record StudentAffiliationChangeRequest(
	String schoolName,		// school 테이블
	Long schoolId,			// school 테이블
	int grade,				// class 테이블
	int classNumber,		// class 테이블
	int studentNumber		// student_class 테이블
) {
}
