import { api } from '../services/api';

// 인터페이스 정의
export interface DebateRoom {
  roomId: string;
  title: string;
  creatorName: string;
  maxPeople: number;
  currentPeople: number;
}

export interface CreateDebateRequest {
  title: string;
  password: string;
  maxPeople: number;
}

export interface EnterDebateRequest {
  password: string;
}

export interface DebateRoomDetail extends DebateRoom {
  onlineUser: {
    userId: string;
    userName: string;
    userType: string;
    profileImagePath: string;
  }[];
}

// 독서 토론 방 목록 조회
export const getDebatesApi = async (): Promise<DebateRoom[]> => {
  const response = await api.get<DebateRoom[]>('/debates');
  return response.data;
};

// 독서 토론 방 생성
export const createDebateApi = async (
  data: CreateDebateRequest
): Promise<void> => {
  await api.post('/debates', data);
};

// 독서 토론 방 정보 조회
export const getDebateDetailApi = async (
  roomId: string
): Promise<DebateRoomDetail> => {
  const response = await api.get<DebateRoomDetail>(`/debates/${roomId}`);
  return response.data;
};

// 독서 토론 방 정보 수정
export const updateDebateDetailApi = async (
  roomId: string,
  data: CreateDebateRequest
): Promise<void> => {
  await api.patch(`/debates/${roomId}`, data);
};

// 독서 토론 방 삭제
export const deleteDebateApi = async (roomId: string): Promise<void> => {
  await api.delete(`/debates/${roomId}`);
};

// 독서 토론 방 입장
export const enterDebateApi = async (
  roomId: string,
  data: EnterDebateRequest
): Promise<string> => {
  const response = await api.post(`/debates/${roomId}/connection`, data);
  return response.data.token;
};

// 독서 토론 방 퇴장
export const exitDebateApi = async (roomId: string): Promise<void> => {
  await api.delete(`/debates/${roomId}/exit`);
};
