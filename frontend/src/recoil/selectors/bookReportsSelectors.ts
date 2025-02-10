import { selector, selectorFamily } from 'recoil';
import {
  teacherBookReportsState,
  studentBookReportsState,
  bookReportDetailState,
} from '../atoms/bookReportsAtoms';
import {
  getTeacherBookReportsApi,
  getStudentBookReportsApi,
  getBookReportDetailApi,
} from '../../services/bookReportsService';
import {
  BookReport,
  BookReportDetail,
} from '../../services/bookReportsService';

// 교사 독서록 목록 선택자
export const teacherBookReportsSelector = selector<BookReport[]>({
  key: 'teacherBookReportsSelector',
  get: async ({ get }) => {
    const cachedReports = get(teacherBookReportsState);
    if (cachedReports.length > 0) return cachedReports;

    try {
      return await getTeacherBookReportsApi();
    } catch (error) {
      console.error('교사 독서록 목록 조회 실패:', error);
      return [];
    }
  },
});

// 학생 독서록 목록 선택자
export const studentBookReportsSelector = selector<BookReport[]>({
  key: 'studentBookReportsSelector',
  get: async ({ get }) => {
    const cachedReports = get(studentBookReportsState);
    if (cachedReports.length > 0) return cachedReports;

    try {
      return await getStudentBookReportsApi();
    } catch (error) {
      console.error('학생 독서록 목록 조회 실패:', error);
      return [];
    }
  },
});

// 독서록 상세 정보 선택자
export const bookReportDetailSelector = selectorFamily<
  BookReportDetail | null,
  string
>({
  key: 'bookReportDetailSelector',
  get:
    (bookReportId) =>
    async ({ get }) => {
      const cachedDetail = get(bookReportDetailState);
      if (cachedDetail && cachedDetail.bookTitle === bookReportId)
        return cachedDetail;

      try {
        return await getBookReportDetailApi(bookReportId);
      } catch (error) {
        console.error('독서록 상세 조회 실패:', error);
        return null;
      }
    },
});
