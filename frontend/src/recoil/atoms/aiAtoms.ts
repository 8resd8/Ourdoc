import { atom } from 'recoil';

// 독서록 AI 피드백 결과 상태
export const aiFeedbackState = atom<string | null>({
  key: 'aiFeedbackState',
  default: null,
});

// 텍스트 AI 맞춤법 결과 상태
export const aiSpellingState = atom<string | null>({
  key: 'aiSpellingState',
  default: null,
});
