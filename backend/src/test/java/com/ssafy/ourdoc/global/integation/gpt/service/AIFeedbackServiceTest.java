package com.ssafy.ourdoc.global.integation.gpt.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ai.chat.model.ChatModel;

import com.ssafy.ourdoc.data.entity.UserSample;
import com.ssafy.ourdoc.domain.classroom.entity.ClassRoom;
import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.domain.user.student.entity.StudentClass;
import com.ssafy.ourdoc.domain.user.student.repository.StudentClassRepository;
import com.ssafy.ourdoc.global.common.enums.Active;
import com.ssafy.ourdoc.global.common.enums.UserType;
import com.ssafy.ourdoc.global.integration.gpt.dto.FeedbackRequest;
import com.ssafy.ourdoc.global.integration.gpt.dto.FeedbackResponse;
import com.ssafy.ourdoc.global.integration.gpt.service.AIFeedbackService;

@ExtendWith(MockitoExtension.class)
class AIFeedbackServiceTest {

	@Mock
	private ChatModel chatModel;

	@Mock
	private StudentClassRepository studentClassRepository;

	@InjectMocks
	private AIFeedbackService aiFeedbackService;

	@Test
	@DisplayName("AI-spelling: 올바른 교정 결과 반환")
	void spelling_shouldReturnFeedbackResponse() {
		User realUser = UserSample.user(UserType.학생);
		User userSpy = spy(realUser);
		when(userSpy.getId()).thenReturn(1L);

		StudentClass mockStudentClass = mock(StudentClass.class);
		ClassRoom mockClassRoom = mock(ClassRoom.class);

		when(studentClassRepository.findByUserIdAndActive(1L, Active.활성))
			.thenReturn(Optional.of(mockStudentClass));

		when(mockStudentClass.getClassRoom()).thenReturn(mockClassRoom);
		when(mockClassRoom.getGrade()).thenReturn(3);

		FeedbackRequest request = new FeedbackRequest("이 문장은 테스트입니다.");
		String mockAiResponse = "교정된 맞춤법 예시 문장";
		when(chatModel.call(anyString())).thenReturn(mockAiResponse);

		FeedbackResponse result = aiFeedbackService.spelling(userSpy, request);

		verify(chatModel, times(1)).call(anyString());
		assertNotNull(result, "피드백 응답이 null이 아니어야 함");
		assertEquals(mockAiResponse, result.feedbackContent(), "교정 결과가 올바르게 반환되어야 함");
	}

	@Test
	@DisplayName("AI-feedback: 올바른 피드백 결과 반환")
	void feedback_shouldReturnFeedbackResponse() {
		User realUser = UserSample.user(UserType.학생);
		User userSpy = spy(realUser);
		when(userSpy.getId()).thenReturn(2L);

		StudentClass mockStudentClass = mock(StudentClass.class);
		ClassRoom mockClassRoom = mock(ClassRoom.class);

		when(studentClassRepository.findByUserIdAndActive(2L, Active.활성))
			.thenReturn(Optional.of(mockStudentClass));

		when(mockStudentClass.getClassRoom()).thenReturn(mockClassRoom);
		when(mockClassRoom.getGrade()).thenReturn(1);

		FeedbackRequest request = new FeedbackRequest("이 글은 독서록 피드백 테스트입니다.");
		String mockAiResponse = "피드백 예시 문장";
		when(chatModel.call(anyString())).thenReturn(mockAiResponse);

		FeedbackResponse result = aiFeedbackService.feedback(userSpy, request);

		verify(chatModel, times(1)).call(anyString());
		assertNotNull(result, "피드백 응답이 null이 아니어야 함");
		assertEquals(mockAiResponse, result.feedbackContent(), "피드백 결과가 올바르게 반환되어야 함");
	}
}
