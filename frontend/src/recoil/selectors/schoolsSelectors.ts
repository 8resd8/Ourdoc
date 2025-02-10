import { selectorFamily } from 'recoil';
import { schoolsState } from '../atoms/schoolsAtoms';
import { getSchoolsApi } from '../../services/schoolsService';
import { School } from '../../services/schoolsService';

// 학교 목록 조회 선택자 (동적 schoolName 사용)
export const schoolsSelector = selectorFamily<School[] | null, string>({
  key: 'schoolsSelector',
  get:
    (schoolName) =>
    async ({ get }) => {
      const cachedSchools = get(schoolsState);
      if (
        cachedSchools &&
        cachedSchools.some((school) => school.schoolName.includes(schoolName))
      ) {
        return cachedSchools.filter((school) =>
          school.schoolName.includes(schoolName)
        );
      }

      try {
        const schools = await getSchoolsApi(schoolName);
        return schools;
      } catch (error) {
        console.error(
          `학교 목록 조회 실패 (schoolName: ${schoolName}):`,
          error
        );
        return null;
      }
    },
});
