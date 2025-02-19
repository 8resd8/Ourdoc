package com.ssafy.ourdoc.global.integration.gpt.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record FeedbackResponse(
	@JsonProperty("summary")
	String summary,

	@JsonProperty("strength")
	String strength,

	@JsonProperty("improvement")
	String improvement
) {
}
