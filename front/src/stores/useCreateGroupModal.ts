import { create } from 'zustand';

export interface ICreateGroupStore {
  isModalOpen: boolean;
}

export interface ICreateGroupStoreActions extends ICreateGroupStore {
  toggleModal: () => void;
}

export const useCreateGroupModalStore = create<ICreateGroupStoreActions>(
  (set) => ({
    isModalOpen: false,
    toggleModal: () => set((state) => ({ isModalOpen: !state.isModalOpen })),
  })
);
