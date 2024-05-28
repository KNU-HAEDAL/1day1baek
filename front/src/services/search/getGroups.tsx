import { BASE_URI } from '@constants/URI';

import { instance } from '@services/API_JWT';

export const getGroups = async () => {
  try {
    const response = await instance.get(`${BASE_URI}/groups`);
    return response.data;
  } catch (error) {
    console.error('Error fetching groups:', error);
    throw error;
  }
};
