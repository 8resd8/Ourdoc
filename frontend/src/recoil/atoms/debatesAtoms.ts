import { atom } from 'recoil';
import { DebateRoom, DebateRoomDetail } from '../../services/debatesService';

// 독서 토론 방 목록 상태
export const debatesState = atom<DebateRoom[]>({
  key: 'debatesState',
  default: [],
});

// 독서 토론 방 상세 정보 상태
export const debateDetailState = atom<DebateRoomDetail | null>({
  key: 'debateDetailState',
  default: null,
});

// 독서 토론 방 입장 상태
export const debateTokenState = atom<string | null>({
  key: 'debateTokenState',
  default: null,
});
