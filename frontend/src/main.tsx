import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import './index.css';

import App from './App.tsx';
import Layout from './layouts/Layout.tsx';

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <Layout />
  </StrictMode>
);
