import { accessTokenState } from '../recoil/atoms/usersAtoms';
import { api, multipartApi } from './api';
import { getRecoil, setRecoil } from 'recoil-nexus';
import secureLocalStorage from 'react-secure-storage';
import { useSetRecoilState } from 'recoil';
import { notify } from '../components/commons/Toast';

export interface SignupTeacherRequest {
  name: string;
  loginId: string;
  password: string;
  birth: string;
  gender: string;
  email: string;
  phone: string;
  // certificateFile: File;
}

export interface SignupStudentRequest {
  name: string;
  loginId: string;
  password: string;
  schoolName: string;
  schoolId: number;
  grade: number;
  classNumber: number;
  studentNumber: number;
  birth: string;
  gender: string;
  classId: number;
}

export interface LoginRequest {
  userType: string;
  loginId: string;
  password: string;
}

export interface LoginResponse {
  id: string;
  name: string;
  role: string;
  schoolName?: string;
  grade?: number;
  classNumber?: number;
  studentNumber?: number;
  tempPassword?: string;
  profileImagePath?: string;
}

export interface CheckIdRequest {
  loginId: string;
}

export interface VerifyPasswordRequest {
  password: string;
}

export interface ChangePasswordRequest {
  newPassword: string;
}

export const signupTeacherApi = async (
  requestData: SignupTeacherRequest,
  certificateFile: File
): Promise<void> => {
  const formData = new FormData();

  formData.append(
    'request',
    new Blob([JSON.stringify(requestData)], { type: 'application/json' })
  );

  const fileType = certificateFile.type;
  if (fileType === 'application/pdf') {
    const pdfBlob = new Blob([certificateFile], { type: 'application/pdf' });
    formData.append('certificateFile', pdfBlob, certificateFile.name);
  } else {
    formData.append('certificateFile', certificateFile);
  }

  await multipartApi.post('/teachers/signup', formData);
};

// 학생 회원가입
export const signupStudentApi = async (
  data: SignupStudentRequest
): Promise<void> => {
  await api.post('/students/signup', data);
};

export const signinApi = async (data: LoginRequest): Promise<LoginResponse> => {
  try {
    const response = await api.post<LoginResponse>('/users/signin', data);
    const accessToken = response.headers['authorization'];

    notify({
      type: 'info',
      text: '로그인 성공!',
    });

    if (accessToken) {
      setRecoil(accessTokenState, accessToken);
    }

    return response.data;
  } catch (error: any) {
    console.error('로그인 실패:', error);

    // 서버에서 온 에러 메시지가 있다면 그대로 throw
    throw error.response?.data?.message
      ? new Error(error.response.data.message)
      : new Error('로그인에 실패했습니다. 다시 시도해주세요.');
  }
};

// 로그아웃
export const signoutApi = async (): Promise<void> => {
  const accessToken = getRecoil(accessTokenState);

  if (accessToken) {
    try {
      await api.post('/users/signout');
    } catch (error) {
      console.warn('서버 로그아웃 실패, 클라이언트 상태만 초기화');
    } finally {
      secureLocalStorage.clear();
      setRecoil(accessTokenState, null);
    }
  }
};

// 아이디 중복 체크
export const checkIdApi = async (data: CheckIdRequest): Promise<boolean> => {
  const response = await api.post<boolean>('/users/checkId', data);
  return response.data;
};

// 비밀번호 일치 여부 확인
export const verifyPasswordApi = async (
  data: VerifyPasswordRequest
): Promise<boolean> => {
  const response = await api.post<boolean>(
    '/users/password/verification',
    data
  );
  return response.data;
};

// 이메일 인증코드 발급
export const requestEmailVerificationApi = async (
  email: string
): Promise<void> => {
  await api.post('/users/email/auth', { email });
};

// 이메일 인증코드 확인
export const verifyEmailCodeApi = async (
  email: string,
  authCode: string
): Promise<void> => {
  await api.post('/users/email/auth/verification', { email, authCode });
};

// 비밀번호 변경
export const changePasswordApi = async (
  data: ChangePasswordRequest
): Promise<void> => {
  await api.patch(`/users/password`, data);
};

// 회원 탈퇴
export const deleteUserApi = async (userId: string): Promise<void> => {
  await api.delete(`/users/${userId}`);
};
