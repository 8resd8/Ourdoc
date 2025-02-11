import axios, { AxiosInstance } from 'axios';
import { getRecoil } from 'recoil-nexus';
import { accessTokenState } from '../recoil/atoms';

const baseURL = import.meta.env.VITE_APP_API_URL;

axios.defaults.withCredentials = true;
axios.defaults.headers.common['Content-Type'] = 'application/json';

const getAccessToken = () => getRecoil(accessTokenState);

const setupInterceptors = (instance: AxiosInstance) => {
  instance.interceptors.request.use(
    (config) => {
      console.log('api: ', config.url, 'called.');

      const accessToken = getAccessToken();

      if (accessToken) {
        config.headers.Authorization = accessToken;
      }
      return config;
    },
    (error) => Promise.reject(error)
  );

  instance.interceptors.response.use(
    (response) => response,
    async (error) => {
      /*
      const originalRequest = error.config;

      if (error.response?.status === 401 && !originalRequest._retry) {
        originalRequest._retry = true; // 무한 루프 방지

        try {
          // ✅ Refresh Token을 사용하여 새로운 Access Token 발급
          const newAccessToken = await refreshAccessToken();

          if (newAccessToken) {
            setRecoil(accessTokenState, newAccessToken);
            originalRequest.headers.Authorization = `Bearer ${newAccessToken}`;
            return instance(originalRequest); // 원래 요청 재시도
          }
        } catch (refreshError) {
          console.error("Refresh Token expired. Logging out...", refreshError);
          logout(); // ✅ Refresh Token도 만료되면 로그아웃
        }
      }
          */

      return Promise.reject(error);
    }
  );
};

const createInstance = (headers = {}): AxiosInstance => {
  const instance = axios.create({
    baseURL,
    timeout: 10000,
    headers,
  });

  setupInterceptors(instance);

  return instance;
};

export const createAxiosInstance = (): AxiosInstance => createInstance();

export const createMultipartAxiosInstance = (): AxiosInstance =>
  createInstance({ 'Content-Type': 'multipart/form-data' });

export const api = createAxiosInstance();
export const multipartApi = createMultipartAxiosInstance();
