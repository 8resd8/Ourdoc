import { api } from '../services/api';

interface BookQueryParams {
  size: number;
  page: number;
  title: string;
  author: string;
  publisher: string;
}

interface HomeworkDetailParams {
  size: number;
  page: number;
  homeworkId: number;
}

export interface Book {
  bookId: number;
  title: string;
  author: string;
  genre: string;
  publisher: string;
  publishYear: string;
  imageUrl: string;
  bookStatus?: BookStatus;
}

export interface HomeworkBook {
  bookId: number;
  title: string;
  author: string;
  genre: string;
  description: string;
  publisher: string;
  publishYear: string;
  imageUrl: string;
  bookStatus: any;
  homeworks: any;
}

export interface BookReport {
  id: number;
  beforeContent: string;
  createdAt: string;
  submitStatus: '제출' | '미제출';
  homeworkSubmitStatus: '제출' | '미제출';
  bookReportApproveStatus: '있음' | '없음';
}
export interface PaginatedBookReports {
  content: BookReport[];
  pageable: {
    pageNumber: number;
    pageSize: number;
    sort: {
      sorted: boolean;
      unsorted: boolean;
      empty: boolean;
    };
    offset: number;
    paged: boolean;
    unpaged: boolean;
  };
  last: boolean;
  totalElements: number;
  totalPages: number;
  first: boolean;
  sort: {
    sorted: boolean;
    unsorted: boolean;
    empty: boolean;
  };
  number: number;
  numberOfElements: number;
  size: number;
  empty: boolean;
}
export interface PaginatedBook {
  book: any;
  pageable: {
    pageNumber: number;
    pageSize: number;
    sort: {
      sorted: boolean;
      unsorted: boolean;
      empty: boolean;
    };
    offset: number;
    paged: boolean;
    unpaged: boolean;
  };
  last: boolean;
  totalElements: number;
  totalPages: number;
  first: boolean;
  sort: {
    sorted: boolean;
    unsorted: boolean;
    empty: boolean;
  };
  number: number;
  numberOfElements: number;
  size: number;
  empty: boolean;
}
export interface StudentHomeworkBookDetail {
  book: HomeworkBook;
  bookReports: PaginatedBookReports;
  homeworkSubmitStatus: any;
}

export interface HomeworkItem {
  homeworkId: number;
  book: HomeworkBook;
  createdAt: string;
  submitStatus: boolean;
  bookReports: any[];
}

export interface PaginatedHomeworks {
  content: HomeworkItem[];
  pageable: {
    pageNumber: number;
    pageSize: number;
    sort: {
      sorted: boolean;
      empty: boolean;
      unsorted: boolean;
    };
    offset: number;
    paged: boolean;
    unpaged: boolean;
  };
  last: boolean;
  totalElements: number;
  totalPages: number;
  first: boolean;
  size: number;
  number: number;
  sort: {
    sorted: boolean;
    empty: boolean;
    unsorted: boolean;
  };
  numberOfElements: number;
  empty: boolean;
}

export interface BookListResponse {
  homeworks: PaginatedHomeworks;
}

interface BookDetail extends Book {
  description: string;
}

export interface FavoriteBook {
  content: Book[];
  pageable: Pageable;
  last: boolean;
  totalElements: number;
  totalPages: number;
  first: boolean;
  number: number;
  sort: Sort;
  numberOfElements: number;
  size: number;
  empty: boolean;
}

export interface BookStatus {
  favorite: boolean;
  recommend: boolean;
  homework: boolean;
}

export interface Pageable {
  pageNumber: number;
  pageSize: number;
  sort: Sort;
  offset: number;
  paged: boolean;
  unpaged: boolean;
}

export interface Sort {
  sorted: boolean;
  unsorted: boolean;
  empty: boolean;
}

export interface RecommendedBook {
  id: number;
  bookId: string;
  title: string;
  author: string;
  publisher: string;
  publishYear: string;
}

export interface BookCategoryParams {
  page: number;
  size: number;
}
export interface BookCategoryBookProps {
  content: BookCategoryContents[];
  pageable: Pageable;
  last: boolean;
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
  sort: Sort;
  first: boolean;
  numberOfElements: number;
  empty: boolean;
}

export interface BookCategoryContents {
  homeworkId: number;
  book: Book;
  createdAt: string;
  homeworkSubmitStatus: boolean;
  bookReports: BookReport[];
}

// 도서 목록 조회
export const getBooksApi = async (params: BookQueryParams) => {
  try {
    const response = await api.get<PaginatedBook>('/books', { params });
    return response.data;
  } catch (error) {
    console.error('Error fetching books:', error);
    throw error;
  }
};

// 국립중앙도서관 도서 목록 조회
export const getNationalLibraryBooksApi = async (
  params: BookQueryParams
): Promise<Book[]> => {
  try {
    const response = await api.get<Book[]>('/nl/books', { params });
    return response.data;
  } catch (error) {
    console.error('Error fetching national library books:', error);
    throw error;
  }
};

// 도서 상세 조회
export const getBookDetailsApi = async (
  bookId: number
): Promise<BookDetail> => {
  try {
    const response = await api.get<BookDetail>(`/books/${bookId}`);
    return response.data;
  } catch (error) {
    console.error('Error fetching book details:', error);
    throw error;
  }
};

// 사용자 관심 도서 등록
export const addFavoriteBookApi = async (bookId: number): Promise<number> => {
  try {
    const response = await api.post('/books/favorite', { bookId });
    return response.status;
  } catch (error) {
    console.error('Error adding favorite book:', error);
    throw error;
  }
};

// 사용자 관심 도서 목록 조회
export const getFavoriteBooksApi = async (
  params: BookCategoryParams
): Promise<BookCategoryBookProps> => {
  try {
    const response = await api.get<{ book: BookCategoryBookProps }>(
      '/books/favorite',
      { params }
    );
    return response.data.book;
  } catch (error) {
    console.error('Error fetching student recommended books:', error);
    throw error;
  }
};

// 사용자 관심 도서 삭제
export const removeFavoriteBookApi = async (
  bookId: number
): Promise<number> => {
  try {
    const response = await api.delete('/books/favorite', { data: { bookId } });
    return response.status;
  } catch (error) {
    console.error('Error removing favorite book:', error);
    throw error;
  }
};

// 학급 추천 도서 등록
export const addTeacherRecommendedBookApi = async (
  bookId: number
): Promise<number> => {
  try {
    const response = await api.post('/books/teachers/recommend/classes', {
      bookId,
    });
    return response.status;
  } catch (error) {
    console.error('Error adding teacher recommended book:', error);
    throw error;
  }
};

// 교사 학년 추천 도서 목록 조회
export const getTeacherRecommendedBooksApi = async (
  params: BookQueryParams
): Promise<RecommendedBook[]> => {
  try {
    const response = await api.get<RecommendedBook[]>(
      '/books/teachers/recommend/grades',
      { params }
    );
    return response.data;
  } catch (error) {
    console.error('Error fetching teacher recommended books:', error);
    throw error;
  }
};

// 교사 학급 추천 도서 목록 조회
export const getClassTeacherRecommendedBooksApi = async (
  params: BookQueryParams
): Promise<RecommendedBook[]> => {
  try {
    const response = await api.get<RecommendedBook[]>(
      '/books/teachers/recommend/classes',
      { params }
    );
    return response.data;
  } catch (error) {
    console.error('Error fetching teacher recommended books:', error);
    throw error;
  }
};

// 학생 학년 추천 도서 목록 조회
export const getStudentRecommendedBooksApi = async (
  params: BookCategoryParams
): Promise<BookCategoryBookProps> => {
  try {
    const response = await api.get<{ recommends: BookCategoryBookProps }>(
      '/books/students/recommend/grades',
      { params }
    );
    return response.data.recommends;
  } catch (error) {
    console.error('Error fetching student recommended books:', error);
    throw error;
  }
};

// 학생 학급 추천 도서 목록 조회
export const getClassStudentRecommendedBooksApi = async (
  params: BookCategoryParams
): Promise<BookCategoryBookProps> => {
  try {
    const response = await api.get<{ recommends: BookCategoryBookProps }>(
      '/books/students/recommend/classes',
      { params }
    );
    return response.data.recommends;
  } catch (error) {
    console.error('Error fetching student recommended books:', error);
    throw error;
  }
};

// 학급 추천 도서 삭제
export const removeTeacherRecommendedBookApi = async (
  bookId: number
): Promise<number> => {
  try {
    const response = await api.delete('/books/teachers/recommend/classes', {
      data: { bookId },
    });
    return response.status;
  } catch (error) {
    console.error('Error removing teacher recommended book:', error);
    throw error;
  }
};

// 학급 숙제 도서 등록
export const addHomeworkBookApi = async (bookId: number): Promise<number> => {
  try {
    const response = await api.post('/books/teachers/homework', { bookId });
    return response.status;
  } catch (error) {
    console.error('Error adding homework book:', error);
    throw error;
  }
};

// 학급 숙제 도서 삭제
export const removeHomeworkBookApi = async (
  bookId: number
): Promise<number> => {
  try {
    const response = await api.delete('/books/teachers/homework', {
      data: { bookId },
    });
    return response.status;
  } catch (error) {
    console.error('Error removing homework book:', error);
    throw error;
  }
};

// 교사 학급 숙제 도서 목록 조회
export const getTeacherHomeworkBooksApi = async (): Promise<Book[]> => {
  try {
    const response = await api.get<Book[]>('/books/teachers/homework');
    return response.data;
  } catch (error) {
    console.error('Error fetching teacher homework books:', error);
    throw error;
  }
};

// 교사 학급 숙제 도서 상세 조회
export const getTeacherHomeworkBookDetailApi = async (
  homeworkId: number
): Promise<BookDetail> => {
  try {
    const response = await api.get<BookDetail>(
      `/books/teachers/homework/${homeworkId}`
    );
    return response.data;
  } catch (error) {
    console.error('Error fetching teacher homework book detail:', error);
    throw error;
  }
};

// 학생 학급 숙제 도서 목록 조회
export const getStudentHomeworkBooksApi = async (
  params: BookCategoryParams
): Promise<BookCategoryBookProps> => {
  try {
    const response = await api.get<{
      homeworks: BookCategoryBookProps;
    }>('/books/students/homework', { params });
    return response.data.homeworks;
  } catch (error) {
    console.error('Error fetching student homework books:', error);
    throw error;
  }
};

// 학생 학급 숙제 도서 상세 조회
export const getStudentHomeworkBookDetailApi = async (
  // homeworkId:
  params: HomeworkDetailParams
): Promise<StudentHomeworkBookDetail> => {
  try {
    const response = await api.get<StudentHomeworkBookDetail>(
      `/books/students/homework/${params.homeworkId}`,
      { params: { page: params.page, size: params.size } }
    );
    return response.data;
  } catch (error) {
    console.error('Error fetching student homework book detail:', error);
    throw error;
  }
};
