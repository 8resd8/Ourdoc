import { atom } from 'recoil';
import {
  Book,
  FavoriteBook,
  RecommendedBook,
} from '../../services/booksService';

// 도서 목록 상태
export const booksState = atom<Book[]>({
  key: 'booksState',
  default: [],
});

// 국립중앙도서관 도서 목록 상태
export const nationalLibraryBooksState = atom<Book[]>({
  key: 'nationalLibraryBooksState',
  default: [],
});

// 도서 상세 정보 상태
export const bookDetailState = atom<Book | null>({
  key: 'bookDetailState',
  default: null,
});

// 사용자 관심 도서 목록 상태
export const favoriteBooksState = atom<FavoriteBook[]>({
  key: 'favoriteBooksState',
  default: [],
});

// 교사 학년 추천 도서 목록 상태
export const teacherRecommendedBooksState = atom<RecommendedBook[]>({
  key: 'teacherRecommendedBooksState',
  default: [],
});

// 학생 학년 추천 도서 목록 상태
export const studentRecommendedBooksState = atom<RecommendedBook[]>({
  key: 'studentRecommendedBooksState',
  default: [],
});

// 교사 학급 숙제 도서 목록 상태
export const teacherHomeworkBooksState = atom<Book[]>({
  key: 'teacherHomeworkBooksState',
  default: [],
});

// 학생 학급 숙제 도서 목록 상태
export const studentHomeworkBooksState = atom<Book[]>({
  key: 'studentHomeworkBooksState',
  default: [],
});

// 교사 학급 숙제 도서 상세 상태
export const teacherHomeworkBookDetailState = atom<Book | null>({
  key: 'teacherHomeworkBookDetailState',
  default: null,
});

// 학생 학급 숙제 도서 상세 상태
export const studentHomeworkBookDetailState = atom<Book | null>({
  key: 'studentHomeworkBookDetailState',
  default: null,
});
