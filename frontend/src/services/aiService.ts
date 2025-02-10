import { api } from '../services/api';

// 독서록 AI 피드백 API 호출
export interface AIFeedbackRequest {
  content: string;
}

export interface AIFeedbackResponse {
  feedbackContent: string;
}

export const getAIFeedbackApi = async (
  request: AIFeedbackRequest
): Promise<AIFeedbackResponse> => {
  const response = await api.post<AIFeedbackResponse>('/ai/feedback', request);
  return response.data;
};

// 텍스트 AI 맞춤법 API 호출
export interface AISpellingRequest {
  content: string;
}

export interface AISpellingResponse {
  feedbackContent: string;
}

export const getAISpellingApi = async (
  request: AISpellingRequest
): Promise<AISpellingResponse> => {
  const response = await api.post<AISpellingResponse>('/ai/spelling', request);
  return response.data;
};
