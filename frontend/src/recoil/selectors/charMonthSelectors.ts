import { selector } from 'recoil';
import { selectedMonthState } from '../atoms/chartMonthAtom';

export const chartMonthSelector = selector<number>({
  key: 'chartMonthSelector',
  get: ({ get }) => {
    return get(selectedMonthState);
  },
});
