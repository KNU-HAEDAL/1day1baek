import { create } from 'zustand';

export interface IFeedStore {
  selectedDate: Date | null;
}

export interface IFeedStoreActions extends IFeedStore {
  setSelectedDate: (date: Date | null) => void;
}

export const useFeedStore = create<IFeedStoreActions>((set) => ({
  selectedDate: null,
  setSelectedDate: (date: Date | null) => set({ selectedDate: date }),
}));
