package com.ssafy.ourdoc.domain.bookreport.dto.teacher;

public record ReportTeacherRequest(
	Integer year,
	Integer studentNumber,
	String studentName,
	String schoolName
) {
}
