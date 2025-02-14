package com.ssafy.ourdoc.domain.bookreport.dto.teacher;

import jakarta.validation.constraints.Size;

public record ReportCommentRequest(
	@Size(max = 250, message = "{size.max}")
	String comment
) {
}
