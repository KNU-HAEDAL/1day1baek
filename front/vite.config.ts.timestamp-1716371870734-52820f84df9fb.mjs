// vite.config.ts
import { defineConfig } from "file:///Users/kimkyuhoi/Desktop/AvWebP/1day1baek/front/node_modules/.pnpm/vite@5.2.11/node_modules/vite/dist/node/index.js";
import react from "file:///Users/kimkyuhoi/Desktop/AvWebP/1day1baek/front/node_modules/.pnpm/@vitejs+plugin-react-swc@3.6.0_vite@5.2.11/node_modules/@vitejs/plugin-react-swc/index.mjs";
var vite_config_default = defineConfig({
  define: {
    global: {}
  },
  plugins: [
    react({
      jsxImportSource: "@emotion/react"
    })
  ],
  test: {
    globals: true,
    environment: "jsdom",
    setupFiles: ["/src/tests/setup.ts"]
  },
  resolve: {
    alias: [
      { find: "@", replacement: "/src" },
      { find: "@assets", replacement: "/src/assets" },
      { find: "@styles", replacement: "/src/styles" },
      { find: "@stores", replacement: "/src/stores" },
      { find: "@components", replacement: "/src/components" },
      { find: "@pages", replacement: "/src/pages" },
      { find: "@hooks", replacement: "/src/hooks" },
      { find: "@utils", replacement: "/src/utils" },
      { find: "@services", replacement: "/src/services" },
      { find: "@constants", replacement: "/src/constants" },
      { find: "@interfaces", replacement: "/src/interfaces" },
      { find: "@tests", replacement: "/src/tests" },
      { find: "@mocks", replacement: "/src/mocks" }
    ]
  }
});
export {
  vite_config_default as default
};
//# sourceMappingURL=data:application/json;base64,ewogICJ2ZXJzaW9uIjogMywKICAic291cmNlcyI6IFsidml0ZS5jb25maWcudHMiXSwKICAic291cmNlc0NvbnRlbnQiOiBbImNvbnN0IF9fdml0ZV9pbmplY3RlZF9vcmlnaW5hbF9kaXJuYW1lID0gXCIvVXNlcnMva2lta3l1aG9pL0Rlc2t0b3AvQXZXZWJQLzFkYXkxYmFlay9mcm9udFwiO2NvbnN0IF9fdml0ZV9pbmplY3RlZF9vcmlnaW5hbF9maWxlbmFtZSA9IFwiL1VzZXJzL2tpbWt5dWhvaS9EZXNrdG9wL0F2V2ViUC8xZGF5MWJhZWsvZnJvbnQvdml0ZS5jb25maWcudHNcIjtjb25zdCBfX3ZpdGVfaW5qZWN0ZWRfb3JpZ2luYWxfaW1wb3J0X21ldGFfdXJsID0gXCJmaWxlOi8vL1VzZXJzL2tpbWt5dWhvaS9EZXNrdG9wL0F2V2ViUC8xZGF5MWJhZWsvZnJvbnQvdml0ZS5jb25maWcudHNcIjsvLy8gPHJlZmVyZW5jZSB0eXBlcz1cInZpdGVzdFwiIC8+XG5pbXBvcnQgeyBkZWZpbmVDb25maWcgfSBmcm9tICd2aXRlJztcblxuaW1wb3J0IHJlYWN0IGZyb20gJ0B2aXRlanMvcGx1Z2luLXJlYWN0LXN3Yyc7XG5cbi8vIGh0dHBzOi8vdml0ZWpzLmRldi9jb25maWcvXG5leHBvcnQgZGVmYXVsdCBkZWZpbmVDb25maWcoe1xuICBkZWZpbmU6IHtcbiAgICBnbG9iYWw6IHt9LFxuICB9LFxuICBwbHVnaW5zOiBbXG4gICAgcmVhY3Qoe1xuICAgICAganN4SW1wb3J0U291cmNlOiAnQGVtb3Rpb24vcmVhY3QnLFxuICAgIH0pLFxuICBdLFxuICB0ZXN0OiB7XG4gICAgZ2xvYmFsczogdHJ1ZSxcbiAgICBlbnZpcm9ubWVudDogJ2pzZG9tJyxcbiAgICBzZXR1cEZpbGVzOiBbJy9zcmMvdGVzdHMvc2V0dXAudHMnXSxcbiAgfSxcbiAgcmVzb2x2ZToge1xuICAgIGFsaWFzOiBbXG4gICAgICB7IGZpbmQ6ICdAJywgcmVwbGFjZW1lbnQ6ICcvc3JjJyB9LFxuICAgICAgeyBmaW5kOiAnQGFzc2V0cycsIHJlcGxhY2VtZW50OiAnL3NyYy9hc3NldHMnIH0sXG4gICAgICB7IGZpbmQ6ICdAc3R5bGVzJywgcmVwbGFjZW1lbnQ6ICcvc3JjL3N0eWxlcycgfSxcbiAgICAgIHsgZmluZDogJ0BzdG9yZXMnLCByZXBsYWNlbWVudDogJy9zcmMvc3RvcmVzJyB9LFxuICAgICAgeyBmaW5kOiAnQGNvbXBvbmVudHMnLCByZXBsYWNlbWVudDogJy9zcmMvY29tcG9uZW50cycgfSxcbiAgICAgIHsgZmluZDogJ0BwYWdlcycsIHJlcGxhY2VtZW50OiAnL3NyYy9wYWdlcycgfSxcbiAgICAgIHsgZmluZDogJ0Bob29rcycsIHJlcGxhY2VtZW50OiAnL3NyYy9ob29rcycgfSxcbiAgICAgIHsgZmluZDogJ0B1dGlscycsIHJlcGxhY2VtZW50OiAnL3NyYy91dGlscycgfSxcbiAgICAgIHsgZmluZDogJ0BzZXJ2aWNlcycsIHJlcGxhY2VtZW50OiAnL3NyYy9zZXJ2aWNlcycgfSxcbiAgICAgIHsgZmluZDogJ0Bjb25zdGFudHMnLCByZXBsYWNlbWVudDogJy9zcmMvY29uc3RhbnRzJyB9LFxuICAgICAgeyBmaW5kOiAnQGludGVyZmFjZXMnLCByZXBsYWNlbWVudDogJy9zcmMvaW50ZXJmYWNlcycgfSxcbiAgICAgIHsgZmluZDogJ0B0ZXN0cycsIHJlcGxhY2VtZW50OiAnL3NyYy90ZXN0cycgfSxcbiAgICAgIHsgZmluZDogJ0Btb2NrcycsIHJlcGxhY2VtZW50OiAnL3NyYy9tb2NrcycgfSxcbiAgICBdLFxuICB9LFxufSk7XG4iXSwKICAibWFwcGluZ3MiOiAiO0FBQ0EsU0FBUyxvQkFBb0I7QUFFN0IsT0FBTyxXQUFXO0FBR2xCLElBQU8sc0JBQVEsYUFBYTtBQUFBLEVBQzFCLFFBQVE7QUFBQSxJQUNOLFFBQVEsQ0FBQztBQUFBLEVBQ1g7QUFBQSxFQUNBLFNBQVM7QUFBQSxJQUNQLE1BQU07QUFBQSxNQUNKLGlCQUFpQjtBQUFBLElBQ25CLENBQUM7QUFBQSxFQUNIO0FBQUEsRUFDQSxNQUFNO0FBQUEsSUFDSixTQUFTO0FBQUEsSUFDVCxhQUFhO0FBQUEsSUFDYixZQUFZLENBQUMscUJBQXFCO0FBQUEsRUFDcEM7QUFBQSxFQUNBLFNBQVM7QUFBQSxJQUNQLE9BQU87QUFBQSxNQUNMLEVBQUUsTUFBTSxLQUFLLGFBQWEsT0FBTztBQUFBLE1BQ2pDLEVBQUUsTUFBTSxXQUFXLGFBQWEsY0FBYztBQUFBLE1BQzlDLEVBQUUsTUFBTSxXQUFXLGFBQWEsY0FBYztBQUFBLE1BQzlDLEVBQUUsTUFBTSxXQUFXLGFBQWEsY0FBYztBQUFBLE1BQzlDLEVBQUUsTUFBTSxlQUFlLGFBQWEsa0JBQWtCO0FBQUEsTUFDdEQsRUFBRSxNQUFNLFVBQVUsYUFBYSxhQUFhO0FBQUEsTUFDNUMsRUFBRSxNQUFNLFVBQVUsYUFBYSxhQUFhO0FBQUEsTUFDNUMsRUFBRSxNQUFNLFVBQVUsYUFBYSxhQUFhO0FBQUEsTUFDNUMsRUFBRSxNQUFNLGFBQWEsYUFBYSxnQkFBZ0I7QUFBQSxNQUNsRCxFQUFFLE1BQU0sY0FBYyxhQUFhLGlCQUFpQjtBQUFBLE1BQ3BELEVBQUUsTUFBTSxlQUFlLGFBQWEsa0JBQWtCO0FBQUEsTUFDdEQsRUFBRSxNQUFNLFVBQVUsYUFBYSxhQUFhO0FBQUEsTUFDNUMsRUFBRSxNQUFNLFVBQVUsYUFBYSxhQUFhO0FBQUEsSUFDOUM7QUFBQSxFQUNGO0FBQ0YsQ0FBQzsiLAogICJuYW1lcyI6IFtdCn0K
