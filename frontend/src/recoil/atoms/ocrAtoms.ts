import { atom } from 'recoil';

// OCR 변환 결과 상태
export const ocrResultState = atom<string | null>({
  key: 'ocrResultState',
  default: null,
});
