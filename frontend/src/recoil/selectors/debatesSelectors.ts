import { selectorFamily } from 'recoil';
import { debateDetailState, debateTokenState } from '../atoms/debatesAtoms';
import {
  getDebateDetailApi,
  enterDebateApi,
} from '../../services/debatesService';
import { DebateRoomDetail } from '../../services/debatesService';

// 독서 토론 방 상세 정보 선택자 (동적 roomId 사용)
export const debateDetailSelector = selectorFamily<
  DebateRoomDetail | null,
  string
>({
  key: 'debateDetailSelector',
  get:
    (roomId) =>
    async ({ get }) => {
      const cachedDetail = get(debateDetailState);
      if (cachedDetail && cachedDetail.roomId === roomId) return cachedDetail;

      try {
        const detail = await getDebateDetailApi(roomId);
        return detail;
      } catch (error) {
        console.error(
          `독서 토론 방 상세 조회 실패 (roomId: ${roomId}):`,
          error
        );
        return null;
      }
    },
});

// 독서 토론 방 입장 토큰 선택자 (동적 roomId 및 password 사용)
export const debateTokenSelector = selectorFamily<
  string | null,
  { roomId: string; password: string }
>({
  key: 'debateTokenSelector',
  get:
    ({ roomId, password }) =>
    async ({ get }) => {
      const token = get(debateTokenState);
      if (token) return token;

      try {
        const newToken = await enterDebateApi(roomId, { password });
        return newToken;
      } catch (error) {
        console.error(`독서 토론 방 입장 실패 (roomId: ${roomId}):`, error);
        return null;
      }
    },
});
