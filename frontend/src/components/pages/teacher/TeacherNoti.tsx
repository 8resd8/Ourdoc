import { useEffect } from 'react';
import { NotificationDetailTile } from '../../atoms/NotificationDetailTile';
import classes from './TeacherNoti.module.css';

const TeacherNoti = () => {
  const fetchNotifications = () => {
    try {
      // const response = await getNotificationsApi();
    } catch (error) {}
  };
  useEffect(() => {
    fetchNotifications();
  }, []);
  return (
    <div className={`${classes.root} mx-auto p-4`}>
      <div className="flex justify-between items-center mb-6">
        <h1 className="headline-medium text-gray-800">알림함</h1>
      </div>
      <NotificationDetailTile />
      <NotificationDetailTile />
      <NotificationDetailTile />
      <NotificationDetailTile />
      <NotificationDetailTile />
    </div>
  );
};

export default TeacherNoti;
