package com.ssafy.ourdoc.api.gpt;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.ourdoc.global.integration.gpt.dto.FeedbackRequest;
import com.ssafy.ourdoc.global.integration.gpt.dto.FeedbackResponse;
import com.ssafy.ourdoc.global.integration.gpt.service.AIFeedbackService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ai")
public class GPTController {

	private final AIFeedbackService aiFeedbackService;

	@PostMapping("/{studentId}/spelling")
	public FeedbackResponse checkSpelling(@PathVariable("studentId") Long studentId,
		@RequestBody FeedbackRequest request) {
		return aiFeedbackService.spelling(studentId, request);
	}

	@PostMapping("/{studentId}/feedback")
	public FeedbackResponse provideFeedback(@PathVariable("studentId") Long studentId,
		@RequestBody FeedbackRequest request) {
		return aiFeedbackService.feedback(studentId, request);
	}
}
