package com.ssafy.ourdoc.global.gpt.service;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.stereotype.Service;

import com.ssafy.ourdoc.global.gpt.dto.FeedbackRequest;
import com.ssafy.ourdoc.global.gpt.dto.FeedbackResponse;
import com.ssafy.ourdoc.global.util.Prompt;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AIFeedbackService {

	private final ChatModel chatModel;

	public FeedbackResponse spelling(Long studentId, FeedbackRequest request) {
		// studentId로 class와 조인해 학년 찾기
		int studentGrade = 1;
		String aiSpellingFeedback = chatModel.call(Prompt.feedback(studentGrade, request.content()));

		return new FeedbackResponse(aiSpellingFeedback);
	}

	public FeedbackResponse feedback(Long studentId, FeedbackRequest request) {
		// studentId로 class와 조인해 학년 찾기
		int studentGrade = 1;
		String aiSpellingFeedback = chatModel.call(Prompt.feedback(studentGrade, request.content()));

		return new FeedbackResponse(aiSpellingFeedback);
	}
}
