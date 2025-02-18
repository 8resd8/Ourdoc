import { api, multipartApi } from '../services/api';

// 인터페이스 정의
export interface ChangeClassRequest {
  schoolName: string;
  schoolId: number;
  classId: number;
  grade: number;
  classNumber: number;
  studentNumber: number;
}

export interface StudentProfile {
  id: string;
  name: string;
  grade: string;
  class: string;
}

export interface StudentProfileResponse {
  profileImage: string;
  name: string;
  loginId: string;
  schoolName: string;
  grade: number;
  classNumber: number;
  studentNumber: number;
  birth: string;
  active: string;
}

export interface UpdateStudentProfileRequest {
  profileImagePath: File;
}

// 학생 소속 변경 (학급 가입 요청)
export const changeStudentClassApi = async (
  data: ChangeClassRequest
): Promise<void> => {
  const response = await api.post('/students/request', data);
  if (response.data.resultCode !== 200) {
    throw new Error(response.data.message);
  }
};

// 학생 본인 정보 조회
export const getStudentProfileApi = async () => {
  const response = await api.get<StudentProfileResponse>('/students/profile');
  return response.data;
};

// 학생 본인 정보 수정
export const updateStudentProfileApi = async (
  data: FormData
): Promise<void> => {
  const response = await multipartApi.patch('/students/profile', data);
  return response.data;
};
