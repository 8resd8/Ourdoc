package com.ssafy.ourdoc.api.gpt;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.global.annotation.Login;
import com.ssafy.ourdoc.global.integration.gpt.dto.FeedbackRequest;
import com.ssafy.ourdoc.global.integration.gpt.dto.FeedbackResponse;
import com.ssafy.ourdoc.global.integration.gpt.service.AIFeedbackService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ai")
public class GPTController {

	private final AIFeedbackService aiFeedbackService;

	@PostMapping("/spelling")
	@ResponseStatus(HttpStatus.CREATED)
	public FeedbackResponse checkSpelling(@Login User user, @Validated @RequestBody FeedbackRequest request) {
		return aiFeedbackService.spelling(user, request);
	}

	@PostMapping("/feedback")
	@ResponseStatus(HttpStatus.CREATED)
	public FeedbackResponse provideFeedback(@Login User user, @Validated @RequestBody FeedbackRequest request) {
		return aiFeedbackService.feedback(user, request);
	}
}
