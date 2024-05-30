import { BASE_URI } from '@constants/URI';

import { instance } from '@services/API_JWT';

export const deleteKickMem = async (
  groupId: string | undefined,
  targetUserId: string | undefined
) => {
  try {
    const response = await instance.delete(
      `${BASE_URI}/groups/${groupId}/kick/${targetUserId}`
    );
    return response.data;
  } catch (error) {
    console.error('Error fetching UserData:', error);
    throw error;
  }
};
