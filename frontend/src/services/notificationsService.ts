import { api } from './api';

// 인터페이스 정의
export interface Notification {
  notificationId: number;
  type: string;
  content: string;
  createdAt: string;
  senderName: string;
}

export interface NotificationPageable {
  content: Notification[];
  pageable: {
    pageNumber: number;
    pageSize: number;
    offset: number;
    paged: boolean;
    unpaged: boolean;
  };
  last: boolean;
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
  first: boolean;
  numberOfElements: number;
  empty: boolean;
}

export interface NotificationDetail {
  notificationId: number;
  type: string;
  senderName: string;
  content: string;
  createdAt: string;
}

// 알림 구독 요청
export const subscribeToNotificationsApi = async (): Promise<void> => {
  const headers = { Accept: 'text/event-stream' };
  await api.get('/notifications/subscribe', { headers });
};

// 알림 읽음 표시
export const markNotificationAsReadApi = async (
  notificationId: number
): Promise<void> => {
  await api.patch(`/notifications/${notificationId}/read`);
};

// 받은 알림 목록 전체 조회
export const getNotificationsApi = async (params: {
  status?: string;
  type?: string;
  page?: number;
  size?: number;
}): Promise<NotificationPageable> => {
  const response = await api.get<NotificationPageable>('/notifications', {
    params,
  });
  return response.data;
};

// 알림 상세 조회
export const getNotificationDetailApi = async (
  notificationId: number
): Promise<NotificationDetail> => {
  const response = await api.get<{ notificationDetail: NotificationDetail }>(
    `/notifications/${notificationId}`
  );
  return response.data.notificationDetail;
};

// 알림 삭제
export const deleteNotificationApi = async (
  notificationId: number
): Promise<void> => {
  await api.delete(`/notifications/${notificationId}`);
};
