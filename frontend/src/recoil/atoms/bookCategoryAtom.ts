import { atom } from 'recoil';
import { BookCategoryType } from '../../styles/bookCategoryType';

export const bookCategoryState = atom<BookCategoryType>({
  key: 'bookCategoryState',
  default: BookCategoryType.HomeWork,
});
