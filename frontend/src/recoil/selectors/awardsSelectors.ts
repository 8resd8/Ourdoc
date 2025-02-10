import { selector, selectorFamily } from 'recoil';
import { awardsListState, awardDetailState } from '../atoms/awardsAtoms';
import {
  getAwardsListApi,
  getAwardDetailApi,
  createAwardApi,
} from '../../services/awardsService';
import { AwardDetail, CreateAwardRequest } from '../../services/awardsService';

// 상장 목록 조회 선택자
export const awardsListSelector = selector<AwardDetail[] | null>({
  key: 'awardsListSelector',
  get: async ({ get }) => {
    const cachedList = get(awardsListState);
    if (cachedList) return cachedList;

    try {
      const list = await getAwardsListApi();
      return list;
    } catch (error) {
      console.error('상장 목록 조회 실패:', error);
      return null;
    }
  },
});

// 상장 상세 조회 선택자 (동적 awardId 사용)
export const awardDetailSelector = selectorFamily<AwardDetail | null, number>({
  key: 'awardDetailSelector',
  get:
    (awardId) =>
    async ({ get }) => {
      const cachedDetail = get(awardDetailState);
      if (cachedDetail && cachedDetail.id === awardId) return cachedDetail;

      try {
        const detail = await getAwardDetailApi(awardId);
        return detail;
      } catch (error) {
        console.error(`상장 상세 조회 실패 (awardId: ${awardId}):`, error);
        return null;
      }
    },
});
