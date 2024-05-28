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
