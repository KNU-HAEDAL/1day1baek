import { create } from 'zustand';

export interface IUserStore {
  username: string;
  profileImg: string;
  commit: number;
}

export interface IUserStoreActions extends IUserStore {
  setUsername: (username: string) => void;
  getUsername: () => string;
  setProfileImg: (profileImg: string) => void;
  getProfileImg: () => string;
  setCommit: (commit: number) => void;
  getCommit: () => number;
}

export const useUserDataStore = create<IUserStoreActions>((set, get) => ({
  username: '',
  commit: 0,
  profileImg: '',
  setUsername: (username: string) => set({ username }),
  getUsername: () => get().username,
  setProfileImg: (profileImg: string) => set({ profileImg }),
  getProfileImg: () => get().profileImg,
  setCommit: (commit: number) => set({ commit }),
  getCommit: () => get().commit,
}));
