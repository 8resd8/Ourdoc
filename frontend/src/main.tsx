import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import './index.css';

import { BrowserRouter, RouterProvider } from 'react-router-dom';
import Router from './routes/Router.tsx';
import { RecoilRoot } from 'recoil';

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <RecoilRoot>
      <BrowserRouter>
        {/* <ResponseInterceptor /> */}
        <Router />
      </BrowserRouter>
    </RecoilRoot>
  </StrictMode>
);
