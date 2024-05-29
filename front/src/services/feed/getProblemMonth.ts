import { BASE_URI } from '@constants/URI';

import { instance } from '@services/API_JWT';

export const getUserProblemMonth = async (yearMonth: string) => {
  try {
    const response = await instance.get(`${BASE_URI}/problems`, {
      params: {
        type: 'MONTH',
        yearMonth: yearMonth,
      },
    });
    return response.data;
  } catch (error) {
    console.error('Error fetching UserData:', error);
    throw error;
  }
};
