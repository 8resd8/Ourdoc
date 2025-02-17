package com.ssafy.ourdoc.domain.bookreport.dto.teacher;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;

public record ReportTeacherRequest(
	@Max(value = 250, message = "{length.max}")
	Integer year,

	@Max(value = 250, message = "{length.max}")
	Integer studentNumber,

	@Size(max = 250, message = "{size.max}")
	String studentName,

	@Size(max = 250, message = "{size.max}")
	String schoolName
) {
}
