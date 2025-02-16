import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import './index.css';

import { BrowserRouter } from 'react-router-dom';
import { RecoilRoot } from 'recoil';
import RecoilNexus from 'recoil-nexus';
import Router from './routes/Router';
import Toast from './components/commons/Toast';

createRoot(document.getElementById('root')!).render(
  <RecoilRoot>
    <RecoilNexus />
    <BrowserRouter>
      <Toast />
      {/* <ResponseInterceptor /> */}
      <Router />
    </BrowserRouter>
  </RecoilRoot>
);
