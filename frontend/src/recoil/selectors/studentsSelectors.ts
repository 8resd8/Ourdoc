import { selector } from 'recoil';
import {
  studentProfileState,
  studentChangeClassState,
} from '../atoms/studentsAtoms';
import {
  getStudentProfileApi,
  StudentProfile,
} from '../../services/studentsService';

// 학생 본인 프로필 반환
export const studentProfileSelector = selector<StudentProfile | null>({
  key: 'studentProfileSelector',
  get: async ({ get }) => {
    const cachedProfile = get(studentProfileState);
    if (cachedProfile) return cachedProfile;

    try {
      const profile = await getStudentProfileApi();
      return profile;
    } catch (error) {
      console.error('학생 프로필 조회 실패:', error);
      return null;
    }
  },
});

// 학생 소속 변경 요청 상태 반환
export const studentChangeClassSelector = selector<boolean>({
  key: 'studentChangeClassSelector',
  get: ({ get }) => get(studentChangeClassState),
});
