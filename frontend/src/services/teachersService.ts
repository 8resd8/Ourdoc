import { api } from '../services/api';

// 인터페이스 정의
export interface TemporaryPasswordResponse {
  resultCode: number;
  message: string;
  tempPassword: string;
}

export interface QrResponse {
  qrCode: String;
  qrUrl: String;
}

export interface UpdateStudentInfoRequest {
  grade: string;
  class: string;
}

export interface TeacherProfileResponse {
  resultCode: number;
  teacher: {
    id: string;
    name: string;
    subject: string;
  };
}

export interface TeacherProfileUpdateRequest {
  name?: string;
  grade?: number;
  class?: number;
}

export interface StudentProfile {
  id: string;
  name: string;
  grade: string;
  class: string;
}

export interface StudentAcademicInfo {
  id: string;
  name: string;
  grade: string;
  class: string;
  academicRecords: Record<string, any>;
}

export interface School {
  schoolName: string;
  address: string;
}

// 교사 인증 요청
export const requestTeacherVerificationApi = async (data: {
  name: string;
  teacherId: string;
}): Promise<void> => {
  await api.post('/teachers/verification', data);
};

// 학생 임시 비밀번호 발급
export const issueTemporaryPasswordApi = async (
  studentId: string
): Promise<TemporaryPasswordResponse> => {
  const response = await api.post<TemporaryPasswordResponse>(
    `/teachers/${studentId}/password/temp`
  );
  return response.data;
};

// 학생 초대 QR코드 발급(회원가입시)
export const generateSignupInviteCodeApi = async (
  teacherId: number
): Promise<QrResponse> => {
  const response = await api.get<QrResponse>(`/teachers/${teacherId}/code`);
  return response.data;
};

// 학생 초대 QR코드 발급(학년변경시)
export const generateChangeInviteCodeApi = async (
  teacherId: number
): Promise<QrResponse> => {
  const response = await api.get<QrResponse>(`/teachers/${teacherId}/change/code`);
  return response.data;
};

// 학생 소속 입력 승인/거절
export const updateStudentAffiliationApi = async (data: {
  studentLoginId: string;
  isApproved: boolean;
}): Promise<void> => {
  await api.patch(`/teachers/${data.studentLoginId}/verification`, {
    isApproved: data.isApproved,
  });
};

// 본인 반 학생 목록 조회
export const getClassStudentListApi = async (): Promise<StudentProfile[]> => {
  const response = await api.get('/teachers/students/profile');
  console.log(response);

  return response.data.studentProfiles;
};

// 본인 반 학생 학적 조회
export const getStudentAcademicInfoApi = async (
  studentId: string
): Promise<StudentAcademicInfo> => {
  const response = await api.get(`/teachers/students/${studentId}/profile`);
  return response.data.student;
};

// 학생 정보 삭제
export const deleteStudentApi = async (studentId: string): Promise<void> => {
  await api.delete(`/teachers/students/${studentId}/profile`);
};

// 교사가 학생 정보 수정
export const updateStudentInfoApi = async (
  studentId: string,
  data: UpdateStudentInfoRequest
): Promise<void> => {
  await api.patch(`/teachers/students/${studentId}/profile`, data);
};

// 교사 본인 정보 조회
export const getTeacherProfileApi = async (): Promise<TeacherProfileResponse> => {
  const response = await api.get<TeacherProfileResponse>(
    `/teachers/profile`
  );
  return response.data;
};

// 교사 본인 정보 수정
export const updateTeacherProfileApi = async (
  teacherId: string,
  data: TeacherProfileUpdateRequest
): Promise<void> => {
  await api.patch(`/teachers/${teacherId}/profile`, data);
};

// 학교 검색 API
export const searchSchoolsApi = async (
  schoolName: string
): Promise<School[]> => {
  const response = await api.get<School[]>('/teachers/class', {
    params: { schoolName },
  });
  return response.data;
};
