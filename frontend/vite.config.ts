import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react-swc';
import tailwindcss from '@tailwindcss/vite';
import svgr from 'vite-plugin-svgr';

export default defineConfig({
  plugins: [react(), tailwindcss(), svgr()],
  build: {
    rollupOptions: {
      external: ['dayjs'],
    },
  },
  optimizeDeps: {
    include: ['dayjs'],
  },
  server: {
    allowedHosts: ['i12a405.p.ssafy.io'],
  },
});
