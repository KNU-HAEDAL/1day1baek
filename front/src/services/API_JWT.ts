import axios from 'axios';

import { BASE_URI } from '@constants/URI';

export const token = localStorage.getItem('aId');

export const instance = axios.create({
  baseURL: BASE_URI,
  headers: {
    'Content-Type': 'application/json',
    Authorization: `Bearer ${token}`,
  },
});

instance.interceptors.response.use(
  (response) => response,
  async (error) => {
    const originalRequest = error.config;

    if (error.response?.status === 401 && !originalRequest._retry) {
      originalRequest._retry = true;
      try {
        const refreshToken = localStorage.getItem('rId');
        const { data } = await axios.get(`${BASE_URI}/token/refresh`, {
          headers: {
            Refresh: `Bearer ${refreshToken}`,
          },
        });

        localStorage.setItem('aId', data.accessToken);
        localStorage.setItem('rId', data.refreshToken);

        instance.defaults.headers.Authorization = `Bearer ${data.accessToken}`;
        originalRequest.headers.Authorization = `Bearer ${data.accessToken}`;

        return instance(originalRequest);
      } catch (refreshError) {
        console.error('Token refresh failed:', refreshError);
      }
    }

    return Promise.reject(error);
  }
);
