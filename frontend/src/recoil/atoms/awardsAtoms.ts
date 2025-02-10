import { atom } from 'recoil';
import { AwardDetail } from '../../services/awardsService';

// 상장 목록 상태
export const awardsListState = atom<AwardDetail[] | null>({
  key: 'awardsListState',
  default: null,
});

// 상장 상세 정보 상태
export const awardDetailState = atom<AwardDetail | null>({
  key: 'awardDetailState',
  default: null,
});
