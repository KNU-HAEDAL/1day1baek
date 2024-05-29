import { BASE_URI } from '@constants/URI';

import { instance } from '@services/API_JWT';

export const getUserProblems = async (date: string) => {
  try {
    const response = await instance.get(`${BASE_URI}/problems`, {
      params: {
        type: 'DAY',
        date: date,
      },
    });
    return response.data;
  } catch (error) {
    console.error('Error fetching UserData:', error);
    throw error;
  }
};
