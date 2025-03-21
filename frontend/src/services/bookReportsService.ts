import { api } from '../services/api';
import { Book, Pageable, Sort } from './booksService';

// 인터페이스 정의
export interface CreateBookReportRequest {
  bookId: string;
  beforeContent: string;
  imageUrl: string;
  ocrCheck: string;
  // homeworkId: string | null;
}

export interface AiFeedbackRequest {
  bookReportId: string;
  feedbackContent: string;
  spellingContent: string;
}

export interface TeacherCommentRequest {
  comment: string;
}

export interface BookReport {
  bookTitle: string;
  studentNumber: number;
  studentName: string;
  createdAt: string;
  approveStatus: string;
}

export interface MonthlyBookReport {
  month: number;
  readCount: number;
}

export interface DayReport {
  day: number;
  readCount: number;
}

export interface BookReportDetail {
  bookId: number;
  bookTitle: string;
  author: string;
  publisher: string;
  createdAt: string;
  beforeContent: string;
  afterContent: string;
  content: string;
  aiComment: string;
  teacherComment: string;
  bookReportApproveStatus: string;
  schoolName: string;
  grade: number;
  classNumber: number;
  studentNumber: number;
}

export interface BookReportParams {
  grade: number;
  page: number;
  size: number;
}

export interface StudentBookReportParams extends BookReportParams {
  classId: number;
}

export interface ClassReportRank {
  rankList: {
    studentNumber: string;
    name: string;
    readCount: number;
    profileImagePath: string;
    rank: number;
  }[];
  totalReadCount: string;
}

export interface MostReadBook {
  gradeMost: {
    book: Book;
    submitCount: number;
  };
  classMost: {
    book: Book;
    submitCount: number;
  };
}

export interface ReportStatistics {
  averageReadCount: number;
  bestReadCount: number;
  readCount: number;
}

export interface StudentRecentAIFeedbackResponse {
  content: string;
}

export interface StudentRankResponse {
  stampCount: number;
  rank: number;
  lastRank: number;
}

export interface StudentBookReportListResponse {
  content: StudentBookReportListContents[];
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

export interface StudentBookReportListContents {
  bookId: number;
  bookImagePath: string;
  bookReportId: number;
  bookTitle: string;
  createdAt: string;
  isHomework: boolean;
}

export interface TeacherBookReportsListParams {
  size: number;
  page: number;
  year: string;
  studentNumber: number;
  studentName: string;
  schoolName: string;
}

// 독서록 작성
export const createBookReportApi = async (
  data: CreateBookReportRequest
): Promise<string> => {
  const response = await api.post('/bookreports/students', data);
  return response.data.bookReportId;
};

// 독서록 작성 AI 피드백 저장
export const saveAiFeedbackApi = async (
  data: AiFeedbackRequest
): Promise<void> => {
  await api.post('/bookreports/feedback', data);
};

// 교사 독서록 목록 조회
export const getTeacherBookReportsApi = async (): Promise<BookReport[]> => {
  const response = await api.get<{ bookReports: BookReport[] }>(
    '/bookreports/teachers'
  );
  return response.data.bookReports;
};

// 학생 독서록 목록 조회
export const getStudentBookReportsApi = async (
  params: StudentBookReportParams
): Promise<StudentBookReportListResponse> => {
  const response = await api.get<{
    bookReports: StudentBookReportListResponse;
  }>('/bookreports/students', { params });

  return response.data.bookReports;
};

// 독서록 상세 조회
export const getBookReportDetailApi = async (
  bookReportId: number
): Promise<BookReportDetail> => {
  const response = await api.get<BookReportDetail>(
    `/bookreports/${bookReportId}`
  );
  return response.data;
};

// 독서록 삭제
export const deleteBookReportApi = async (
  bookReportId: number
): Promise<void> => {
  await api.delete(`/bookreports/students/${bookReportId}`);
};

// 학생 독서록 승인
export const approveBookReportApi = async (
  bookReportId: number
): Promise<void> => {
  await api.patch(`/bookreports/teachers/${bookReportId}/stamp`);
};

// 교사 의견 작성
export const createTeacherCommentApi = async (
  bookReportId: number,
  data: TeacherCommentRequest
): Promise<void> => {
  await api.post(`/bookreports/teachers/${bookReportId}/comment`, data);
};

// 교사 의견 수정
export const updateTeacherCommentApi = async (
  bookReportId: number,
  data: TeacherCommentRequest
): Promise<void> => {
  await api.patch(`/bookreports/teachers/${bookReportId}/comment`, data);
};

// 교사 의견 삭제
export const deleteTeacherCommentApi = async (
  bookReportId: number
): Promise<void> => {
  await api.delete(`/bookreports/teachers/${bookReportId}/comment`);
};

// 학급 독서 랭킹
export const classReportRankApi = async (): Promise<ClassReportRank> => {
  const response = await api.get<ClassReportRank>(`/bookreports/teachers/rank`);

  return response.data;
};

// 학급 월별 독서록 통계 조회
export const classMonthlyReportApi = async (): Promise<MonthlyBookReport[]> => {
  const response = await api.get<MonthlyBookReport[]>(
    `/bookreports/teachers/statistics/months`
  );

  return response.data;
};

// 학급 일별 독서록 통계 조회
export const classDailyReportApi = async ({
  month,
}: {
  month: number;
}): Promise<DayReport[]> => {
  const response = await api.get<DayReport[]>(
    `/bookreports/teachers/statistics/days`,
    {
      params: {
        month,
      },
    }
  );
  return response.data;
};

// 학년/학급 많이 읽은 책 조회
export const mostReadApi = async (): Promise<MostReadBook> => {
  const response = await api.get<MostReadBook>(`/books/teachers/mostread`);
  return response.data;
};

// 학생 월별 독서록 통계 조회
export const studentMonthlyReportApi = async ({
  grade,
}: {
  grade: number;
}): Promise<MonthlyBookReport[]> => {
  const response = await api.get<MonthlyBookReport[]>(
    `/bookreports/students/statistics/months`,
    { params: { grade } }
  );

  return response.data;
};

// 학생 일별 독서록 통계 조회
export const studentDailyReportApi = async ({
  month,
  grade,
}: {
  month: number;
  grade: number;
}): Promise<DayReport[]> => {
  const response = await api.get<DayReport[]>(
    `/bookreports/students/statistics/days`,
    {
      params: {
        month,
        grade,
      },
    }
  );
  return response.data;
};

// 학생 독서록 통계 조회
export const studentReportStatisticsApi = async ({
  grade,
}: {
  grade: number;
}): Promise<ReportStatistics> => {
  const response = await api.get<ReportStatistics>(
    `/bookreports/students/statistics`,
    {
      params: { grade },
    }
  );

  return response.data;
};

// 학생이 독서록을 숙제로 제출
export const studentSubmitHomeworkReportApi = async (
  bookreportId: number,
  homeworkId: number
): Promise<void> => {
  await api.post(
    `/bookreports/students/${bookreportId}/homework/${homeworkId}`
  );
};

// 학생 최근 AI 피드백 조회
export const studentRecentAIFeedbackApi =
  async (): Promise<StudentRecentAIFeedbackResponse> => {
    const response = await api.get(`/bookreports/students/feedback/ai/recent`);
    return response.data;
  };

// 학생 독서록 순위 조회
export const getStudentRankApi = async (): Promise<StudentRankResponse> => {
  const response = await api.get(`/bookreports/students/rank`);
  return response.data;
};

// 교사 독서록 목록 조회
export const getTeacherBookReportsList = async (
  params: TeacherBookReportsListParams
) => {
  const response = await api.get('/bookreports/teachers', {
    params: {
      size: 10,
      page: params.page,
      year: params.year,
      studentNumber: params.studentNumber,
      studentName: params.studentName,
      schoolName: params.schoolName,
    },
  });

  return response.data;
};
