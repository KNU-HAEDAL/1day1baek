/// <reference types="vitest" />
import { defineConfig } from 'vite';

import react from '@vitejs/plugin-react-swc';

// https://vitejs.dev/config/
export default defineConfig({
  define: {
    global: {},
  },
  plugins: [
    react({
      jsxImportSource: '@emotion/react',
    }),
  ],
  test: {
    globals: true,
    environment: 'jsdom',
    setupFiles: ['/src/tests/setup.ts'],
  },
  resolve: {
    alias: [
      { find: '@', replacement: '/src' },
      { find: '@assets', replacement: '/src/assets' },
      { find: '@styles', replacement: '/src/styles' },
      { find: '@stores', replacement: '/src/stores' },
      { find: '@components', replacement: '/src/components' },
      { find: '@pages', replacement: '/src/pages' },
      { find: '@hooks', replacement: '/src/hooks' },
      { find: '@utils', replacement: '/src/utils' },
      { find: '@services', replacement: '/src/services' },
      { find: '@constants', replacement: '/src/constants' },
      { find: '@interfaces', replacement: '/src/interfaces' },
      { find: '@tests', replacement: '/src/tests' },
      { find: '@mocks', replacement: '/src/mocks' },
    ],
  },
});
