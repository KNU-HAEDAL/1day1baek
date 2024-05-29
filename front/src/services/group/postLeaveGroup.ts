import { BASE_URI } from '@constants/URI';

import { instance } from '@services/API_JWT';

export const postLeaveMyGroup = async (groupId: number) => {
  try {
    const response = await instance.post(`${BASE_URI}/groups/${groupId}/leave`);
    return response.data;
  } catch (error) {
    console.error('Error fetching UserData:', error);
    throw error;
  }
};
