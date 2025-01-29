package com.ssafy.ourdoc.ocr.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ai.chat.model.ChatModel;

import com.ssafy.ourdoc.ocr.dto.FeedbackRequest;
import com.ssafy.ourdoc.ocr.dto.FeedbackResponse;

@ExtendWith(MockitoExtension.class)
class AIFeedbackServiceTest {

	@Mock
	private ChatModel chatModel;

	@InjectMocks
	private AIFeedbackService aiFeedbackService;


	@Test
	@DisplayName("AI-spelling")
	void spelling_shouldReturnFeedbackResponse() {
		Long studentId = 100L;
		FeedbackRequest request = new FeedbackRequest("이 문장은 테스트입니다.");
		String mockAiResponse = "교정된 맞춤법 예시 문장";
		when(chatModel.call(anyString())).thenReturn(mockAiResponse);

		FeedbackResponse result = aiFeedbackService.spelling(studentId, request);

		verify(chatModel, times(1)).call(anyString());
		assertNotNull(result);
		assertEquals(mockAiResponse, result.feedbackContent(), "교정 결과가 올바르게 반환되어야 함");
	}

	@Test
	@DisplayName("AI-feedback")
	void feedback_shouldReturnFeedbackResponse() {
		Long studentId = 200L;
		FeedbackRequest request = new FeedbackRequest("이 글은 독서록 피드백 테스트입니다.");
		String mockAiResponse = "피드백 예시 문장";
		when(chatModel.call(anyString())).thenReturn(mockAiResponse);

		FeedbackResponse result = aiFeedbackService.feedback(studentId, request);

		verify(chatModel, times(1)).call(anyString());
		assertNotNull(result);
		assertEquals(mockAiResponse, result.feedbackContent(), "피드백 결과가 올바르게 반환되어야 함");
	}
}
