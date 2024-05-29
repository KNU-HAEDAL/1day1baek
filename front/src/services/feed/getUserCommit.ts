import dayjs from 'dayjs';

import { BASE_URI } from '@constants/URI';

import { instance } from '@services/API_JWT';

export const getUserCommit = async () => {
  const currentDate = dayjs().format('YYYY-MM-DD'); // 현재 날짜를 yyyy-mm-dd 형식으로 추출

  try {
    const response = await instance.get(
      `${BASE_URI}/problems/count?date=${currentDate}`
    );
    return response.data;
  } catch (error) {
    console.error('Error fetching UserData:', error);
    throw error;
  }
};
