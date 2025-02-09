import { api, multipartApi } from '../../hooks/CustomAxios';

interface signUpTeacherRequest {
  name: string;
  loginId: string;
  password: string;
  birth: string;
  gender: string;
  email: string;
  phone: string;
  certificateFile: File;
  [key: string]: string | File;
}
interface signUpStudentRequest {
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
}

interface signUpResponse {
  resultCode: number;
  message: string;
  teacherId?: number;
  studentId?: number;
}

interface signInRequest {
  userType: string;
  loginId: string;
  password: string;
}

interface signInResponse {
  id: number;
  name: string;
  role: string;
  schoolName: string;
  grade: number;
  classNumber: number;
  studentNumber: number;
  tempPassword: boolean;
}

interface checkIdRequest {
  loginId: string;
}

interface checkIdResponse {
  isDuplicate: boolean;
}

interface checkPasswordRequest {
  password: string;
}

interface checkPasswordResponse {
  resultCode: number;
  message: string;
}

interface emailVerificationRequest {
  email: string;
}

interface emailVerificationResponse {
  resultCode: number;
  message: string;
}

interface verifyEmailCodeRequest {
  email: string;
  authCode: string;
}

interface verifyEmailCodeResponse {
  resultCode: number;
  message: string;
}

interface tempPasswordResponse {
  resultCode: number;
  message: string;
  tempPassword: string;
}

interface changePasswordRequest {
  password: string;
}

interface changePasswordResponse {
  resultCode: number;
  message: string;
}

interface deleteUserResponse {
  resultCode: number;
  message: string;
}

interface teacherVerificationRequest {
  teacherId: number;
}

interface teacherVerificationResponse {
  resultCode: number;
  message: string;
}

// 로그인
export const signInApi = async (
  credentials: signInRequest
): Promise<signInResponse> => {
  try {
    const response = await api.post<signInResponse>(
      '/users/signin',
      credentials
    );
    return response.data;
  } catch (error) {
    console.error('Login error:', error);
    throw error;
  }
};

// 로그아웃
export const signOutApi = async (token: string): Promise<void> => {
  try {
    await api.post('/users/signout', { token });
  } catch (error) {
    console.error('Logout error:', error);
    throw error;
  }
};

// 회원가입 (선생님)
export const signUpTeacherApi = async (
  credentials: signUpTeacherRequest
): Promise<signUpResponse> => {
  try {
    const formData = new FormData();
    Object.keys(credentials).forEach((key) =>
      formData.append(key, credentials[key])
    );
    formData.append('certificateFile', credentials.certificateFile);

    const response = await multipartApi.post<signUpResponse>(
      '/teachers/signup',
      formData
    );
    return response.data;
  } catch (error) {
    console.error('Sign up error:', error);
    throw error;
  }
};

// 회원가입 (학생)
export const signUpStudentApi = async (
  credentials: signUpStudentRequest
): Promise<signUpResponse> => {
  try {
    const response = await api.post<signUpResponse>(
      '/students/signup',
      credentials
    );
    return response.data;
  } catch (error) {
    console.error('Sign up error:', error);
    throw error;
  }
};

// 아이디 중복 체크
export const checkIdApi = async (
  credentials: checkIdRequest
): Promise<checkIdResponse> => {
  try {
    const response = await api.post<checkIdResponse>(
      '/users/checkId',
      credentials
    );
    return response.data;
  } catch (error) {
    console.error('Check ID error:', error);
    throw error;
  }
};

// 비밀번호 일치 여부 확인
export const checkPasswordApi = async (
  credentials: checkPasswordRequest
): Promise<checkPasswordResponse> => {
  try {
    const response = await api.post<checkPasswordResponse>(
      '/users/password/verification',
      credentials
    );
    return response.data;
  } catch (error) {
    console.error('Check password error:', error);
    throw error;
  }
};

// 이메일 인증코드 발급
export const emailVerificationApi = async (
  credentials: emailVerificationRequest
): Promise<emailVerificationResponse> => {
  try {
    const response = await api.post<emailVerificationResponse>(
      '/users/email/auth',
      credentials
    );
    return response.data;
  } catch (error) {
    console.error('Email verification error:', error);
    throw error;
  }
};

// 이메일 인증코드 확인
export const verifyEmailCodeApi = async (
  credentials: verifyEmailCodeRequest
): Promise<verifyEmailCodeResponse> => {
  try {
    const response = await api.post<verifyEmailCodeResponse>(
      '/users/email/auth/verification',
      credentials
    );
    return response.data;
  } catch (error) {
    console.error('Verify email code error:', error);
    throw error;
  }
};

// 학생 임시 비밀번호 발급
export const tempPasswordApi = async (
  studentId: number
): Promise<tempPasswordResponse> => {
  try {
    const response = await api.post<tempPasswordResponse>(
      `/teachers/${studentId}/password/temp`
    );
    return response.data;
  } catch (error) {
    console.error('Temp password error:', error);
    throw error;
  }
};

// 비밀번호 변경
export const changePasswordApi = async (
  userId: number,
  credentials: changePasswordRequest
): Promise<changePasswordResponse> => {
  try {
    const response = await api.put<changePasswordResponse>(
      `/users/${userId}/password`,
      credentials
    );
    return response.data;
  } catch (error) {
    console.error('Change password error:', error);
    throw error;
  }
};

// 회원 탈퇴
export const deleteUserApi = async (
  userId: number
): Promise<deleteUserResponse> => {
  try {
    const response = await api.delete<deleteUserResponse>(`/users/${userId}`);
    return response.data;
  } catch (error) {
    console.error('Delete user error:', error);
    throw error;
  }
};

// 교사 인증 요청
export const teacherVerificationApi = async (
  credentials: teacherVerificationRequest
): Promise<teacherVerificationResponse> => {
  try {
    const response = await api.post<teacherVerificationResponse>(
      '/teachers/verification',
      credentials
    );
    return response.data;
  } catch (error) {
    console.error('Teacher verification error:', error);
    throw error;
  }
};
