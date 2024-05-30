import { BASE_URI } from '@constants/URI';

import { instance } from '@services/API_JWT';

import { IGroupHolidayProps } from '@interfaces/GroupInterface';

export const postHoliday = async (
  groupId: string | undefined,
  data: IGroupHolidayProps
) => {
  try {
    const response = await instance.post(
      `${BASE_URI}/groups/${groupId}/holidays`,
      data
    );
    return response.data;
  } catch (error) {
    console.error('Error fetching UserData:', error);
    throw error;
  }
};
