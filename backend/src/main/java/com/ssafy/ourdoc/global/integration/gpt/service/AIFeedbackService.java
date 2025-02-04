package com.ssafy.ourdoc.global.integration.gpt.service;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.stereotype.Service;

import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.domain.user.student.entity.StudentClass;
import com.ssafy.ourdoc.domain.user.student.repository.StudentClassRepository;
import com.ssafy.ourdoc.domain.user.student.repository.StudentRepository;
import com.ssafy.ourdoc.global.integration.gpt.dto.FeedbackRequest;
import com.ssafy.ourdoc.global.integration.gpt.dto.FeedbackResponse;
import com.ssafy.ourdoc.global.util.Prompt;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AIFeedbackService {

	private final ChatModel chatModel;
	private final StudentClassRepository studentClassRepository;

	public FeedbackResponse spelling(User user, FeedbackRequest request) {
		int studentGrade = getStudentGrade(user);
		String aiSpellingFeedback = chatModel.call(Prompt.feedback(studentGrade, request.content()));

		return new FeedbackResponse(aiSpellingFeedback);
	}

	public FeedbackResponse feedback(User user, FeedbackRequest request) {
		int studentGrade = getStudentGrade(user);
		String aiSpellingFeedback = chatModel.call(Prompt.feedback(studentGrade, request.content()));

		return new FeedbackResponse(aiSpellingFeedback);
	}

	private int getStudentGrade(User user) {
		StudentClass studentClass = getStudentClass(user);
		return studentClass.getClassRoom().getGrade();
	}

	private StudentClass getStudentClass(User user) {
		return studentClassRepository.findStudentClassByUserId(user.getId()).orElseThrow();
	}
}
