import { api } from '../services/api';

// 인터페이스 정의
export interface ChangeClassRequest {
  schoolName: string;
  schoolId: number;
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
  resultCode: number;
  student: StudentProfile;
}

export interface UpdateStudentProfileRequest {
  name?: string;
  class?: string;
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
export const getStudentProfileApi = async (): Promise<StudentProfile> => {
  const response = await api.get<StudentProfileResponse>('/students/profile');
  return response.data.student;
};

// 학생 본인 정보 수정
export const updateStudentProfileApi = async (
  data: UpdateStudentProfileRequest
): Promise<void> => {
  const response = await api.patch('/students/profile', data);
  if (response.data.resultCode !== 200) {
    throw new Error(response.data.message);
  }
};
