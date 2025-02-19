package com.ssafy.ourdoc.global.integration.gpt.service;

import java.util.Optional;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.stereotype.Service;

import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.domain.user.student.entity.StudentClass;
import com.ssafy.ourdoc.domain.user.student.repository.StudentClassRepository;
import com.ssafy.ourdoc.domain.user.student.repository.StudentRepository;
import com.ssafy.ourdoc.global.integration.gpt.dto.FeedbackRequest;
import com.ssafy.ourdoc.global.integration.gpt.dto.FeedbackResponse;
import com.ssafy.ourdoc.global.integration.gpt.dto.SpellingRequest;
import com.ssafy.ourdoc.global.util.Prompt;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AIFeedbackService {

	private final ChatModel chatModel;
	private final StudentClassRepository studentClassRepository;

	public FeedbackResponse spelling(User user, SpellingRequest request) {
		int studentGrade = getStudentGrade(user);
		String aiSpellingFeedback = chatModel.call(Prompt.spelling(studentGrade, request.content()));

		return new FeedbackResponse(aiSpellingFeedback);
	}

	public FeedbackResponse feedback(User user, FeedbackRequest request) {
		int studentGrade = getStudentGrade(user);
		String aiSpellingFeedback = chatModel.call(Prompt.feedback(studentGrade, request));

		return new FeedbackResponse(aiSpellingFeedback);
	}

	private int getStudentGrade(User user) {
		Optional<StudentClass> studentClass = getStudentClass(user);
		if (studentClass.isEmpty()) {
			return 1;
		}
		return studentClass.get().getClassRoom().getGrade();
	}

	private Optional<StudentClass> getStudentClass(User user) {
		return studentClassRepository.findStudentClassByUserId(user.getId());
	}
}
