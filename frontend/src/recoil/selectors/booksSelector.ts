// import { selector } from 'recoil';
// import {
//   booksState,
//   nationalLibraryBooksState,
//   bookDetailState,
//   favoriteBooksState,
//   teacherRecommendedBooksState,
//   studentRecommendedBooksState,
//   teacherHomeworkBooksState,
//   studentHomeworkBooksState,
//   teacherHomeworkBookDetailState,
//   studentHomeworkBookDetailState,
// } from '../atoms/booksAtoms';
// import {
//   getBooksApi,
//   getNationalLibraryBooksApi,
//   getBookDetailsApi,
//   getFavoriteBooksApi,
//   getTeacherRecommendedBooksApi,
//   getStudentRecommendedBooksApi,
//   getTeacherHomeworkBooksApi,
//   getStudentHomeworkBooksApi,
//   getTeacherHomeworkBookDetailApi,
//   getStudentHomeworkBookDetailApi,
// } from '../../services/booksService';

// // 도서 목록 가져오기 (비동기)
// export const booksSelector = selector({
//   key: 'booksSelector',
//   get: async ({ get }) => {
//     try {
//       const books = await getBooksApi({
//         title: '',
//         author: '',
//         publisher: '',
//         size: 0,
//         page: 0,
//       });
//       return books;
//     } catch (error) {
//       console.error('도서 목록 불러오기 실패:', error);
//       return get(booksState); // 기존 상태 유지
//     }
//   },
// });

// // 국립중앙도서관 도서 목록 가져오기 (비동기)
// export const nationalLibraryBooksSelector = selector({
//   key: 'nationalLibraryBooksSelector',
//   get: async ({ get }) => {
//     try {
//       const books = await getNationalLibraryBooksApi({
//         title: '',
//         author: '',
//         publisher: '',
//         size: 0,
//         page: 0,
//       });
//       return books;
//     } catch (error) {
//       console.error('국립중앙도서관 도서 목록 불러오기 실패:', error);
//       return get(nationalLibraryBooksState); // 기존 상태 유지
//     }
//   },
// });

// // 도서 상세 정보 가져오기 (비동기)
// export const bookDetailSelector = selector({
//   key: 'bookDetailSelector',
//   get: async ({ get }) => {
//     const bookDetail = get(bookDetailState);
//     if (!bookDetail) return null;

//     try {
//       return await getBookDetailsApi(bookDetail.id);
//     } catch (error) {
//       console.error('도서 상세 정보 불러오기 실패:', error);
//       return bookDetail; // 기존 상태 유지
//     }
//   },
// });

// // 사용자 관심 도서 목록 가져오기 (비동기)
// export const favoriteBooksSelector = selector({
//   key: 'favoriteBooksSelector',
//   get: async ({ get }) => {
//     try {
//       return await getFavoriteBooksApi();
//     } catch (error) {
//       console.error('관심 도서 목록 불러오기 실패:', error);
//       return get(favoriteBooksState); // 기존 상태 유지
//     }
//   },
// });

// // 교사 학년 추천 도서 목록 가져오기 (비동기)
// export const teacherRecommendedBooksSelector = selector({
//   key: 'teacherRecommendedBooksSelector',
//   get: async ({ get }) => {
//     try {
//       return await getTeacherRecommendedBooksApi();
//     } catch (error) {
//       console.error('교사 학년 추천 도서 목록 불러오기 실패:', error);
//       return get(teacherRecommendedBooksState); // 기존 상태 유지
//     }
//   },
// });

// // 학생 학년 추천 도서 목록 가져오기 (비동기)
// export const studentRecommendedBooksSelector = selector({
//   key: 'studentRecommendedBooksSelector',
//   get: async ({ get }) => {
//     try {
//       return await getStudentRecommendedBooksApi();
//     } catch (error) {
//       console.error('학생 학년 추천 도서 목록 불러오기 실패:', error);
//       return get(studentRecommendedBooksState); // 기존 상태 유지
//     }
//   },
// });

// // 교사 학급 숙제 도서 목록 가져오기 (비동기)
// export const teacherHomeworkBooksSelector = selector({
//   key: 'teacherHomeworkBooksSelector',
//   get: async ({ get }) => {
//     try {
//       const books = await getTeacherHomeworkBooksApi();
//       return books;
//     } catch (error) {
//       console.error('교사 학급 숙제 도서 목록 조회 실패:', error);
//       return get(teacherHomeworkBooksState); // 기존 상태 유지
//     }
//   },
// });

// // 학생 학급 숙제 도서 목록 가져오기 (비동기)
// export const studentHomeworkBooksSelector = selector({
//   key: 'studentHomeworkBooksSelector',
//   get: async ({ get }) => {
//     try {
//       const books = await getStudentHomeworkBooksApi();
//       return books;
//     } catch (error) {
//       console.error('학생 학급 숙제 도서 목록 조회 실패:', error);
//       return get(studentHomeworkBooksState); // 기존 상태 유지
//     }
//   },
// });

// // 교사 학급 숙제 도서 상세 조회 (비동기)
// export const teacherHomeworkBookDetailSelector = selector({
//   key: 'teacherHomeworkBookDetailSelector',
//   get: async ({ get }) => {
//     const bookDetail = get(teacherHomeworkBookDetailState);
//     if (!bookDetail) return null;

//     try {
//       return await getTeacherHomeworkBookDetailApi(bookDetail.id);
//     } catch (error) {
//       console.error('교사 학급 숙제 도서 상세 조회 실패:', error);
//       return bookDetail; // 기존 상태 유지
//     }
//   },
// });

// // 학생 학급 숙제 도서 상세 조회 (비동기)
// export const studentHomeworkBookDetailSelector = selector({
//   key: 'studentHomeworkBookDetailSelector',
//   get: async ({ get }) => {
//     const bookDetail = get(studentHomeworkBookDetailState);
//     if (!bookDetail) return null;

//     try {
//       return await getStudentHomeworkBookDetailApi(bookDetail.id);
//     } catch (error) {
//       console.error('학생 학급 숙제 도서 상세 조회 실패:', error);
//       return bookDetail; // 기존 상태 유지
//     }
//   },
// });
