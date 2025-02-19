import axios, { AxiosInstance } from 'axios';
import { getRecoil } from 'recoil-nexus';
import { accessTokenState } from '../recoil/atoms/usersAtoms';
import secureLocalStorage from 'react-secure-storage';
import { signoutApi } from './usersService';
import { notify } from '../components/commons/Toast';
import { toast } from 'react-toastify';

const baseURL = import.meta.env.VITE_APP_API_URL;

axios.defaults.withCredentials = true;
axios.defaults.headers.common['Content-Type'] = 'application/json';

const getAccessToken = () =>
  getRecoil(accessTokenState) ||
  (secureLocalStorage.getItem('accessTokenState') as string | null);

const setupInterceptors = (instance: AxiosInstance) => {
  instance.interceptors.request.use(
    (request) => {
      console.log('api: ', request.url, '호출됨.');

      const accessToken = getAccessToken();

      if (accessToken) {
        request.headers.Authorization = accessToken;
      }
      return request;
    },
    (error) => {
      return Promise.reject(error);
    }
  );

  instance.interceptors.response.use(
    (response) => {
      return response;
    },
    async (error) => {
      const errorState = error.status;

      switch (errorState) {
        case 401:
          notify({
            type: 'error',
            text: '선생 및 학생이 다릅니다. 재로그인을 요청합니다.',
          });

          await signoutApi();

          toast.onChange((payload) => {
            if (payload.status === 'removed') {
              window.location.href = '/';
            }
          });

          break;
        case 403:
          notify({
            type: 'error',
            text: '권한이 존재하지 않습니다.',
          });

          toast.onChange((payload) => {
            if (payload.status === 'removed') {
              window.history.back();
            }
          });

          break;

        case 404:
          notify({ type: 'error', text: '컨텐츠가 존재하지 않습니다.' });

          toast.onChange((payload) => {
            if (payload.status === 'removed') {
              window.history.back();
            }
          });

          break;

        case 500:
          notify({
            type: 'error',
            text: '서버에서 오류가 발생했습니다. 재로그인 요청합니다.',
          });

          toast.onChange((payload) => {
            if (payload.status === 'removed') {
              window.location.href = '/';
            }
          });
      }

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
