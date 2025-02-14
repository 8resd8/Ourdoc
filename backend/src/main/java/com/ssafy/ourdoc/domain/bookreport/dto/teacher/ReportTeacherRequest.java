package com.ssafy.ourdoc.domain.bookreport.dto.teacher;

import jakarta.validation.constraints.Size;

public record ReportTeacherRequest(
	@Size(max = 250, message = "{size.max}")
	Integer year,

	@Size(max = 250, message = "{size.max}")
	Integer studentNumber,

	@Size(max = 250, message = "{size.max}")
	String studentName,

	@Size(max = 250, message = "{size.max}")
	String schoolName
) {
}
