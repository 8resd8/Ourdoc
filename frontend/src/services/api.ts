import axios, { AxiosInstance } from 'axios';

const baseURL = import.meta.env.VITE_APP_API_URL;

axios.defaults.withCredentials = true;
axios.defaults.headers.common['Content-Type'] = 'application/json';

export const createAxiosInstance = (): AxiosInstance => {
  const instance = axios.create({
    baseURL,
    timeout: 10000,
  });

  instance.interceptors.request.use(
    (config) => {
      const token = localStorage.getItem('token');
      if (token) {
        config.headers.Authorization = `Bearer ${token}`;
      }
      return config;
    },
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

export const createMultipartAxiosInstance = (): AxiosInstance => {
  const instance = axios.create({
    baseURL,
    timeout: 10000,
    headers: {
      'Content-Type': 'multipart/form-data',
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
      }
      return Promise.reject(error);
    }
  );

  return instance;
};

export const api = createAxiosInstance();
export const multipartApi = createMultipartAxiosInstance();
