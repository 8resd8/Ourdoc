import { atom } from 'recoil';
import {
  TeacherProfileResponse,
  StudentProfile,
  School,
} from '../../services/teachersService';

// 교사 인증 상태
export const teacherVerificationState = atom<boolean>({
  key: 'teacherVerificationState',
  default: false,
});

// 학생 초대 QR코드 상태
export const studentInviteCodeState = atom<string | null>({
  key: 'studentInviteCodeState',
  default: null,
});

// 교사 본인 프로필 상태
export const teacherProfileState = atom<TeacherProfileResponse | null>({
  key: 'teacherProfileState',
  default: null,
});

// 본인 반 학생 목록 상태
export const classStudentListState = atom<StudentProfile[]>({
  key: 'classStudentListState',
  default: [],
});

// 학생 임시 비밀번호 상태
export const temporaryPasswordState = atom<string | null>({
  key: 'temporaryPasswordState',
  default: null,
});

// 검색된 학교 목록 상태
export const schoolSearchResultsState = atom<School[] | null>({
  key: 'schoolSearchResultsState',
  default: null,
});
