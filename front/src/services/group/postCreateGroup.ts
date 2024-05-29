import { BASE_URI } from '@constants/URI';

import { instance } from '@services/API_JWT';

import { ICreateGroupModalProps } from '@/interfaces/GroupInterface';

export const postCreateGroup = async (data: ICreateGroupModalProps) => {
  try {
    const response = await instance.post(`${BASE_URI}/groups`, data);
    return response.data;
  } catch (error) {
    console.error('Error fetching UserData:', error);
    throw error;
  }
};
