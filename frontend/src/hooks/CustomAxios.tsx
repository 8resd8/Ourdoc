import axios, { AxiosInstance } from 'axios';

const baseURL = import.meta.env.VITE_APP_API_URL;

export const createAxiosInstance = (): AxiosInstance => {
  const instance = axios.create({
    baseURL,
    timeout: 10000,
    withCredentials: true, // 모든 요청에 쿠키 포함
    headers: {
      'Content-Type': 'application/json',
    },
  });

  instance.interceptors.request.use(
    (config) => config,
    (error) => Promise.reject(error)
  );

  instance.interceptors.response.use(
    (response) => response,
    (error) => {
      if (error.response?.status === 401) {
        // 에러 핸들링 어떻게 할것인가
      }
      return Promise.reject(error);
    }
  );

  return instance;
};

export const api = createAxiosInstance();
