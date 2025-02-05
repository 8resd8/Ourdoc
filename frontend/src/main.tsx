import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import './index.css';

import App from './App.tsx';
import Layout from './layouts/Layout.tsx';
import { BrowserRouter, RouterProvider } from 'react-router-dom';
import Router from './routes/Router.tsx';

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <BrowserRouter>
      <Router />
    </BrowserRouter>
  </StrictMode>
);
