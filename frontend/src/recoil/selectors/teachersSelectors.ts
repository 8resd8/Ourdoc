import { selector } from 'recoil';
import {
  teacherVerificationState,
  teacherProfileState,
  classStudentListState,
  temporaryPasswordState,
  studentInviteCodeState,
} from '../atoms/teachersAtoms';
import {
  TeacherProfileResponse,
  StudentProfile,
} from '../../services/teachersService';

// 교사 인증 상태 반환
export const teacherVerificationSelector = selector<boolean>({
  key: 'teacherVerificationSelector',
  get: ({ get }) => get(teacherVerificationState),
});

// 교사 본인 프로필 반환
export const teacherProfileSelector = selector<TeacherProfileResponse | null>({
  key: 'teacherProfileSelector',
  get: ({ get }) => get(teacherProfileState),
});

// 본인 반 학생 목록 반환
export const classStudentListSelector = selector<StudentProfile[]>({
  key: 'classStudentListSelector',
  get: ({ get }) => get(classStudentListState),
});

// 학생 임시 비밀번호 반환
export const temporaryPasswordSelector = selector<string | null>({
  key: 'temporaryPasswordSelector',
  get: ({ get }) => get(temporaryPasswordState),
});

// 학생 초대 QR코드 반환
export const studentInviteCodeSelector = selector<string | null>({
  key: 'studentInviteCodeSelector',
  get: ({ get }) => get(studentInviteCodeState),
});
