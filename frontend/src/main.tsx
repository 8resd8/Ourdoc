import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import './index.css';

import { BrowserRouter, RouterProvider } from 'react-router-dom';
import Router from './routes/Router.tsx';
import { RecoilRoot } from 'recoil';
import RecoilNexus from 'recoil-nexus';
import { ToastContainer } from 'react-toastify';
import Toast from './components/commons/Toast.tsx';

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <RecoilRoot>
      <RecoilNexus />
      <BrowserRouter>
        <Toast />
        {/* <ResponseInterceptor /> */}
        <Router />
      </BrowserRouter>
    </RecoilRoot>
  </StrictMode>
);
