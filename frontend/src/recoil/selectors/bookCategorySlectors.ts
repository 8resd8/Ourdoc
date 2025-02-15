import { selector } from 'recoil';
import { BookCategoryType } from '../../styles/bookCategoryType';
import { bookCategoryState } from '../atoms/bookCategoryAtom';

export const bookCategorySelector = selector<BookCategoryType>({
  key: 'bookCategorySelector',
  get: ({ get }) => {
    return get(bookCategoryState);
  },
});
