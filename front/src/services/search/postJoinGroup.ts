import { BASE_URI } from '@constants/URI';

import { instance } from '@services/API_JWT';

export const postJoinGroup = async (groupId: string, password: string) => {
  try {
    const response = await instance.post(`${BASE_URI}/groups/${groupId}/join`, {
      password: password,
    });
    return response.data;
  } catch (error) {
    console.error('Error fetching UserData:', error);
    throw error;
  }
};
