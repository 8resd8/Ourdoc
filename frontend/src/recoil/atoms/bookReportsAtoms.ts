import { atom } from 'recoil';
import {
  BookReport,
  BookReportDetail,
} from '../../services/bookReportsService';

// 교사 독서록 목록 상태
export const teacherBookReportsState = atom<BookReport[]>({
  key: 'teacherBookReportsState',
  default: [],
});

// 학생 독서록 목록 상태
export const studentBookReportsState = atom<BookReport[]>({
  key: 'studentBookReportsState',
  default: [],
});

// 독서록 상세 정보 상태
export const bookReportDetailState = atom<BookReportDetail | null>({
  key: 'bookReportDetailState',
  default: null,
});
