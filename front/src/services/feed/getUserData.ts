import { BASE_URI } from '@constants/URI';

import { instance } from '@services/API_JWT';

export const getUserData = async () => {
  try {
    const response = await instance.get(`${BASE_URI}/users`);
    return response.data;
  } catch (error) {
    console.error('Error fetching UserData:', error);
    throw error;
  }
};
