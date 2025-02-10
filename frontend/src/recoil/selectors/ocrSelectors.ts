import { selector } from 'recoil';
import { ocrResultState } from '../atoms/ocrAtoms';

// OCR 변환 결과 선택자
export const ocrResultSelector = selector<string | null>({
  key: 'ocrResultSelector',
  get: ({ get }) => {
    return get(ocrResultState);
  },
});
