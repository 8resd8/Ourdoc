package com.ssafy.ourdoc.domain.bookreport.dto;

public record FeedbackRequest(
	Long bookReportId,
	String afterContent
) {
}
