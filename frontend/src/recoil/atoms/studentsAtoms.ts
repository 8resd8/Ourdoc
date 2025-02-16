import { atom } from 'recoil';
import { StudentProfile } from '../../services/studentsService';

// 학생 본인 프로필 상태
export const studentProfileState = atom<StudentProfile | null>({
  key: 'studentProfileState',
  default: null,
});

// 학생 소속 변경 요청 상태
export const studentChangeClassState = atom<boolean>({
  key: 'studentChangeClassState',
  default: false,
});

// 학생 소속 변경 요청 상태
export const studentGradeState = atom<number>({
  key: 'studentGradeState',
  default: 1,
});
