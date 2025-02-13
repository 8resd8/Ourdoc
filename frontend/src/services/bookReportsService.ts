import { api } from '../services/api';

// 인터페이스 정의
export interface CreateBookReportRequest {
  bookId: string;
  beforeContent: string;
  imageUrl: string;
  ocrCheck: string;
}

export interface AiFeedbackRequest {
  bookReportId: string;
  afterContent: string;
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

export interface BookReportDetail {
  bookTitle: string;
  author: string;
  publisher: string;
  createdAt: string;
  content: string;
  aiComment: string;
  teacherComment: string;
  approveStatus: string;
}

export interface BookReportParams {
  grade: number;
}

// 독서록 작성
export const createBookReportApi = async (
  data: CreateBookReportRequest
): Promise<void> => {
  await api.post('/bookreports/students', data);
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
export const getStudentBookReportsApi = async (params: BookReportParams): Promise<BookReport[]> => {
  const response = await api.get<{ bookReports: BookReport[] }>(
    '/bookreports/students', { params }
  );
  return response.data.bookReports;
};

// 독서록 상세 조회
export const getBookReportDetailApi = async (
  bookReportId: string
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
  await api.delete(`/bookreports/${bookReportId}`);
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
