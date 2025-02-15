import { atom } from 'recoil';

const today = new Date();
const currentMonth = today.getMonth() + 1;

export const selectedMonthState = atom<number>({
  key: 'selectedMonthState',
  default: currentMonth,
});
