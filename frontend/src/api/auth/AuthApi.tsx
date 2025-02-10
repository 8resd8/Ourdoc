import { api } from '../../hooks/CustomAxios';

interface signInRequest {
  userType: string;
  loginId: string;
  password: string;
}
interface signUpRequest {
  userType: string;
  loginId: string;
  password: string;
}

interface sighInResponse {
  user: {
    id: number;
    name: string;
    role: string;
    tempPassword: boolean;
  };
}
interface signUpResponse {
  user: {
    id: number;
    name: string;
    role: string;
    tempPassword: boolean;
  };
}

// 로그인
export const signInApi = async (
  credentials: signInRequest
): Promise<sighInResponse> => {
  try {
    const response = await api.post<sighInResponse>(
      '/users/signin',
      credentials,
      {
        withCredentials: true,
      }
    );
    return response.data;
  } catch (error) {
    console.error('Login error:', error);
    throw error;
  }
};

// 로그아웃
export const signOutApi = async (): Promise<void> => {
  try {
    await api.post(
      '/users/signout',
      {},
      {
        withCredentials: true,
      }
    );
  } catch (error) {
    console.error('Logout error:', error);
    throw error;
  }
};

// 회원가입
export const signUpApi = async (
  credentials: signUpRequest
): Promise<signUpResponse> => {
  try {
    const response = await api.post<signUpResponse>(
      '/users/signup',
      credentials,
      {
        withCredentials: true,
      }
    );
    return response.data;
  } catch (error) {
    console.error('Login error:', error);
    throw error;
  }
};
