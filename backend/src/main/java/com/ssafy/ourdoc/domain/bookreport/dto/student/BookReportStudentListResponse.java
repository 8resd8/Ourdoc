package com.ssafy.ourdoc.domain.bookreport.dto.student;

import org.springframework.data.domain.Page;

public record BookReportStudentListResponse(
	Page<BookReportListDtoConvert> bookReports
) {
}
