import { BASE_URI } from '@constants/URI';

import { instance } from '@services/API_JWT';

export const getSelectGroup = async (groupId: string | undefined) => {
  try {
    const response = await instance.get(`${BASE_URI}/groups/${groupId}`);
    return response.data;
  } catch (error) {
    console.error('Error fetching UserData:', error);
    throw error;
  }
};
