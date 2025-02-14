import { api, multipartApi } from '../services/api';

interface BookQueryParams {
  title: string;
  author: string;
  publisher: string;
}

export interface Book {
  id: number;
  title: string;
  author: string;
  genre: string;
  publisher: string;
  publishTime: string;
  imageUrl: string;
}

interface BookDetail extends Book {
  description: string;
}

export interface FavoriteBook {
  id: number;
  isbn: string;
  title: string;
  author: string;
  genre: string;
  publisher: string;
  publishTime: string;
  imageUrl: string;
}

export interface RecommendedBook {
  id: number;
  bookId: string;
  title: string;
  author: string;
  publisher: string;
  publishTime: string;
}

// 도서 목록 조회
export const getBooksApi = async (params: BookQueryParams): Promise<Book[]> => {
  try {
    const response = await api.get<Book[]>('/books', { params });
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
export const getFavoriteBooksApi = async (): Promise<FavoriteBook[]> => {
  try {
    const response = await api.get<FavoriteBook[]>('/books/favorite');
    return response.data;
  } catch (error) {
    console.error('Error fetching favorite books:', error);
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
    const response = await api.post('/books/teachers/recommend', { bookId });
    return response.status;
  } catch (error) {
    console.error('Error adding teacher recommended book:', error);
    throw error;
  }
};

// 교사 학년 추천 도서 목록 조회
export const getTeacherRecommendedBooksApi = async (params: BookQueryParams): Promise<
  RecommendedBook[]
> => {
  try {
    const response = await api.get<RecommendedBook[]>(
      '/books/teachers/recommend/grades', { params }
    );
    return response.data;
  } catch (error) {
    console.error('Error fetching teacher recommended books:', error);
    throw error;
  }
};

// 교사 학급 추천 도서 목록 조회
export const getClassTeacherRecommendedBooksApi = async (params: BookQueryParams): Promise<
  RecommendedBook[]
> => {
  try {
    const response = await api.get<RecommendedBook[]>(
      '/books/teachers/recommend/classes', { params }
    );
    return response.data;
  } catch (error) {
    console.error('Error fetching teacher recommended books:', error);
    throw error;
  }
};

// 학생 학년 추천 도서 목록 조회
export const getStudentRecommendedBooksApi = async (params: BookQueryParams): Promise<
  RecommendedBook[]
> => {
  try {
    const response = await api.get<RecommendedBook[]>(
      '/books/students/recommend/grades', { params }
    );
    return response.data;
  } catch (error) {
    console.error('Error fetching student recommended books:', error);
    throw error;
  }
};

// 학생 학급 추천 도서 목록 조회
export const getClassStudentRecommendedBooksApi = async (params: BookQueryParams): Promise<
  RecommendedBook[]
> => {
  try {
    const response = await api.get<RecommendedBook[]>(
      '/books/students/recommend/classes', { params }
    );
    return response.data;
  } catch (error) {
    console.error('Error fetching student recommended books:', error);
    throw error;
  }
};

// 학년 추천 도서 삭제
export const removeTeacherRecommendedBookApi = async (
  bookId: number
): Promise<number> => {
  try {
    const response = await api.delete('/books/teachers/recommend', {
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
export const getStudentHomeworkBooksApi = async (params: BookQueryParams): Promise<Book[]> => {
  try {
    const response = await api.get<Book[]>('/books/students/homework', { params });
    return response.data;
  } catch (error) {
    console.error('Error fetching student homework books:', error);
    throw error;
  }
};

// 학생 학급 숙제 도서 상세 조회
export const getStudentHomeworkBookDetailApi = async (
  homeworkId: number
): Promise<BookDetail> => {
  try {
    const response = await api.get<BookDetail>(
      `/books/students/homework/${homeworkId}`
    );
    return response.data;
  } catch (error) {
    console.error('Error fetching student homework book detail:', error);
    throw error;
  }
};
