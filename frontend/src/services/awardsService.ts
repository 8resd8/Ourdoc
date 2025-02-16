import { api, multipartApi } from '../services/api';

// 인터페이스 정의
export interface AwardDetail {
  id: number;
  imagePath: string;
  title: string;
  createdAt: string;
}

export interface AwardsListResponse {
  awards: AwardDetail[];
}

export interface CreateAwardRequest {
  title: string;
  file: File;
}

// 상장 목록 조회
export const getAwardsListApi = async (): Promise<AwardDetail[]> => {
  const response = await api.get<AwardsListResponse>('/awards');
  return response.data.awards;
};

// 상장 상세 조회
export const getAwardDetailApi = async (
  awardId: number
): Promise<AwardDetail> => {
  const response = await api.get<AwardDetail>(`/awards/${awardId}`);
  return response.data;
};

// 상장 생성
export const createAwardApi = async (
  data: CreateAwardRequest
): Promise<void> => {
  const formData = new FormData();
  formData.append('title', data.title);
  formData.append('file', data.file);

  await multipartApi.post('/awards', formData);
};

// 학생 상장 목록 조회
export const getStudentAwardsListApi = async (): Promise<AwardDetail[]> => {
  const response = await api.get<AwardsListResponse>('/students/awards');
  return response.data.awards;
};
