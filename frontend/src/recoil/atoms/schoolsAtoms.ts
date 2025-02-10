import { atom } from 'recoil';
import { School } from '../../services/schoolsService';

// 학교 목록 상태
export const schoolsState = atom<School[] | null>({
  key: 'schoolsState',
  default: null,
});
