import { api, multipartApi } from '../services/api';

// OCR 변환 API 요청 인터페이스
export interface HandOCRResponse {
  handOCRContent: string;
}

// OCR 변환 API 호출 함수
export const convertHandToTextApi = async (
  handImage: File
): Promise<HandOCRResponse> => {
  const formData = new FormData();
  formData.append('hand_image', handImage);

  const response = await multipartApi.post<HandOCRResponse>(
    '/ocr/hand',
    formData
  );
  return response.data;
};
