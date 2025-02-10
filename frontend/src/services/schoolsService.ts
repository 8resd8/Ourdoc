import { api } from '../services/api';

// 인터페이스 정의
export interface School {
  schoolName: string;
  address: string;
}

// 학교 목록 조회
export const getSchoolsApi = async (schoolName: string): Promise<School[]> => {
  const response = await api.get<School[]>('/schools', {
    params: { schoolName },
  });
  return response.data;
};
