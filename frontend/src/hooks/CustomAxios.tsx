import axios, { AxiosInstance, AxiosError, AxiosRequestConfig } from 'axios';

const baseURL = import.meta.env.VITE_APP_API_URL || 'https://example.com';

export const createAxiosInstance = (): AxiosInstance => {
  const instance = axios.create({
    baseURL,
    timeout: 10000,
    headers: {
      'Content-Type': 'application/json',
    },
  });

  // 요청 인터셉터
  instance.interceptors.request.use(
    (config) => {
      const token = localStorage.getItem('token');
      if (token) {
        config.headers.Authorization = `Bearer ${token}`;
      }
      return config;
    },
    (error: AxiosError) => {
      return Promise.reject(error);
    }
  );

  // 응답 인터셉터
  instance.interceptors.response.use(
    (response) => response,
    (error: AxiosError) => {
      if (error.response?.status === 401) {
        // 토큰 만료 등 인증 에러 처리
        localStorage.removeItem('token');
      }
      return Promise.reject(error);
    }
  );

  return instance;
};

export const api = createAxiosInstance();
