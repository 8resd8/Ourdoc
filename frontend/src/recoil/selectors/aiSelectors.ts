import { selector } from 'recoil';
import { aiFeedbackState, aiSpellingState } from '../atoms/aiAtoms';

// 독서록 AI 피드백 선택자
export const aiFeedbackSelector = selector<string | null>({
  key: 'aiFeedbackSelector',
  get: ({ get }) => {
    return get(aiFeedbackState);
  },
});

// 텍스트 AI 맞춤법 선택자
export const aiSpellingSelector = selector<string | null>({
  key: 'aiSpellingSelector',
  get: ({ get }) => {
    return get(aiSpellingState);
  },
});
