package com.ssafy.ourdoc.domain.bookreport.dto;

import org.springframework.data.domain.Page;

public record BookReportListResponse(Page<BookReportDto> bookReports) {
}
