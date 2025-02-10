package com.ssafy.ourdoc.domain.bookreport.dto.teacher;

import org.springframework.data.domain.Page;

public record ReportTeacherListResponse(Page<ReportTeacherResponse> bookReports) {
}
