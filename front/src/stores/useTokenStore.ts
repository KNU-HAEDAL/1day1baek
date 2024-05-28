import { create } from 'zustand';

interface ITokenStore {
  isAccessToken: boolean;
}

interface ITokenActionStore extends ITokenStore {
  setIsAccessToken: (value: boolean) => void;
}

export const useTokenStore = create<ITokenActionStore>((set) => ({
  isAccessToken: localStorage.getItem('aId') !== null,
  setIsAccessToken: (value) => set(() => ({ isAccessToken: value })),
}));
