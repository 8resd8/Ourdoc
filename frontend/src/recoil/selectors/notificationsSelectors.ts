import { selectorFamily } from 'recoil';
import {
  getNotificationsApi,
  getNotificationDetailApi,
} from '../../services/notificationsService';
import {
  NotificationPageable,
  NotificationDetail,
} from '../../services/notificationsService';

// 받은 알림 목록 선택자 (동적 파라미터 적용)
export const notificationsSelector = selectorFamily<
  NotificationPageable | null,
  { page: number; size: number }
>({
  key: 'notificationsSelector',
  get:
    ({ page, size }) =>
    async () => {
      try {
        // API 호출: 동적 파라미터(page, size) 전달
        const notifications = await getNotificationsApi({ page, size });
        return notifications;
      } catch (error) {
        console.error('알림 목록 조회 실패:', error);
        return null;
      }
    },
});

// 알림 상세 선택자 (동적 notificationId 적용)
export const notificationDetailSelector = selectorFamily<
  NotificationDetail | null,
  number
>({
  key: 'notificationDetailSelector',
  get: (notificationId) => async () => {
    try {
      // API 호출: 동적 notificationId 전달
      const notificationDetail = await getNotificationDetailApi(notificationId);
      return notificationDetail;
    } catch (error) {
      console.error(`알림 상세 조회 실패 (ID: ${notificationId}):`, error);
      return null;
    }
  },
});
