import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

const ResponseInterceptor = () => {
  const navigate = useNavigate();

  useEffect(() => {
    const interceptor = axios.interceptors.response.use(
      (response) => response,
      (error) => {
        if (error.response?.status === 401) {
          navigate('/login', { state: { from: window.location.pathname } });
        }
        return Promise.reject(error);
      }
    );

    return () => {
      // 컴포넌트 언마운트 시 인터셉터 제거
      axios.interceptors.response.eject(interceptor);
    };
  }, [navigate]);

  return null;
};

export default ResponseInterceptor;
