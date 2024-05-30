import { create } from 'zustand';

export interface IInviteStore {
  isModalOpen: boolean;
}

export interface IInviteStoreActions extends IInviteStore {
  toggleModal: () => void;
}

export const useInviteModalStore = create<IInviteStoreActions>((set) => ({
  isModalOpen: false,
  toggleModal: () => set((state) => ({ isModalOpen: !state.isModalOpen })),
}));
