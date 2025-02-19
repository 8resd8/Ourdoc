package com.ssafy.ourdoc.global.integration.gpt.service;

import java.util.Optional;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.domain.user.student.entity.StudentClass;
import com.ssafy.ourdoc.domain.user.student.repository.StudentClassRepository;
import com.ssafy.ourdoc.global.common.enums.Active;
import com.ssafy.ourdoc.global.integration.gpt.dto.FeedbackRequest;
import com.ssafy.ourdoc.global.integration.gpt.dto.FeedbackResponse;
import com.ssafy.ourdoc.global.integration.gpt.dto.SpellingRequest;
import com.ssafy.ourdoc.global.integration.gpt.dto.SpellingResponse;
import com.ssafy.ourdoc.global.util.Prompt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AIFeedbackService {

	private final ChatModel chatModel;
	private final StudentClassRepository studentClassRepository;

	public SpellingResponse spelling(User user, SpellingRequest request) {
		int studentGrade = getStudentGrade(user);
		String spelling = chatModel.call(Prompt.spelling(studentGrade, request.content()));

		return new SpellingResponse(spelling);
	}

	public FeedbackResponse feedback(User user, FeedbackRequest request) {
		int studentGrade = getStudentGrade(user);
		String feedback = chatModel.call(Prompt.feedback(studentGrade, request));
		return getFeedbackResponse(feedback);
	}

	private static FeedbackResponse getFeedbackResponse(String feedback) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.readValue(feedback, FeedbackResponse.class);
		} catch (JsonProcessingException e) {
			throw new IllegalStateException("JSON 파싱 실패", e);
		}
	}

	private int getStudentGrade(User user) {
		Optional<StudentClass> studentClass = getStudentClass(user);
		if (studentClass.isEmpty()) {
			return 1;
		}
		return studentClass.get().getClassRoom().getGrade();
	}

	private Optional<StudentClass> getStudentClass(User user) {
		return studentClassRepository.findByUserIdAndActive(user.getId(), Active.활성);
	}
}
