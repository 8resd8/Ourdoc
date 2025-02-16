import { useNavigate } from 'react-router-dom';
import { NotificationTile } from '../atoms/NotificationTile';
import { PlusButton } from '../atoms/PlusButton';
import { AddDivider } from '../../utils/AddDivder';
import { useEffect, useState } from 'react';
import {
  getNotificationsApi,
  NotificationPageable,
} from '../../services/notificationsService';

export const NotificationSection = ({ isStudent }: { isStudent?: boolean }) => {
  const navigate = useNavigate();
  const [notifications, setnotifications] = useState<NotificationPageable>();

  useEffect(() => {
    const fetchData = async () => {
      const notifications = await getNotificationsApi({});
      setnotifications(notifications);
    };
    fetchData();
  }, []);

  return (
    <div className="w-[414px] h-[238px] p-6 bg-gray-0 rounded-[15px] shadow-xsmall flex-col justify-start items-start gap-5 inline-flex">
      <div className="self-stretch justify-between items-start inline-flex">
        <div>
          <span className="text-gray-800 headline-medium">알림 </span>
          <span className="text-gray-500 headline-small">
            {notifications?.numberOfElements}개
          </span>
        </div>
        <PlusButton
          onClick={() => {
            navigate(`/${isStudent ? 'student' : 'teacher'}/noti`);
          }}
        />
      </div>

      <div className="self-stretch h-[152px] flex-col items-start flex">
        <AddDivider
          itemList={
            notifications
              ? notifications?.content
                  .map((item, index) => (
                    <NotificationTile key={index} notification={item} />
                  ))
                  .splice(0, 3)
              : []
          }
        />
      </div>
    </div>
  );
};
