import { create } from 'zustand';

export interface IHolidayStore {
  isModalOpen: boolean;
  isDeleteModalOpen: boolean;
}

export interface IHolidayStoreActions extends IHolidayStore {
  toggleModal: () => void;
  toggleDeleteModal: () => void;
}

export const useHolidayModalStore = create<IHolidayStoreActions>((set) => ({
  isModalOpen: false,
  isDeleteModalOpen: false,
  toggleModal: () => set((state) => ({ isModalOpen: !state.isModalOpen })),
  toggleDeleteModal: () =>
    set((state) => ({ isDeleteModalOpen: !state.isDeleteModalOpen })),
}));
