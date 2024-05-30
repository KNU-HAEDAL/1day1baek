import { BASE_URI } from '@constants/URI';

import { instance } from '@services/API_JWT';

export const postInviteGroup = async (inviteCode: string) => {
  try {
    const response = await instance.post(
      `${BASE_URI}/groups/invited`,
      inviteCode
    );
    return response.data;
  } catch (error) {
    console.error('Error fetching UserData:', error);
    throw error;
  }
};
