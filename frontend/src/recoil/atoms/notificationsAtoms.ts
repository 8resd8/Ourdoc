import { atom } from 'recoil';
import {
  NotificationPageable,
  NotificationDetail,
} from '../../services/notificationsService';

// 받은 알림 목록 상태
export const notificationsState = atom<NotificationPageable | null>({
  key: 'notificationsState',
  default: null,
});

// 알림 상세 상태
export const notificationDetailState = atom<NotificationDetail | null>({
  key: 'notificationDetailState',
  default: null,
});
