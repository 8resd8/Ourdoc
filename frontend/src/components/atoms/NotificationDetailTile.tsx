import { useState } from 'react';
import {
  markNotificationAsReadApi,
  Notification,
} from '../../services/notificationsService';
import { detailDate } from '../../utils/DateFormat';
import Modal from '../commons/Modal';

export const NotificationDetailTile = ({
  notification,
}: {
  notification: Notification;
}) => {
  const [modal, setmodal] = useState(false);

  const markNotificationAsRead = async () => {
    await markNotificationAsReadApi(notification.notificationId);
  };

  return (
    <>
      <Modal
        isOpen={modal}
        title={''}
        body={
          <div className="flex flex-col gap-8 pb-8">
            <div className="self-stretch h-12 flex-col justify-start items-end flex">
              <div className="self-stretch justify-start items-center gap-[11px] inline-flex">
                <div className="text-center text-gray-700 body-medium">
                  보낸 사람
                </div>
                <div className="text-center text-gray-800 body-medium">
                  {notification.senderName}
                </div>
              </div>
              <div className="self-stretch justify-start items-center gap-[11px] inline-flex">
                <div className="text-center text-gray-700 body-medium">
                  보낸 날짜
                </div>
                <div className="text-center text-gray-800 body-medium">
                  {detailDate(notification.createdAt)}
                </div>
              </div>
            </div>
            <div className="self-stretch h-[124px] flex-col justify-start items-start gap-6 flex">
              <div className="self-stretch text-gray-800 body-medium text-start">
                {notification.content}
              </div>
            </div>
          </div>
        }
        confirmText={'확인'}
        cancelText={'닫기'}
        onConfirm={function (): void {
          markNotificationAsRead();
          setmodal(false);
        }}
        onCancel={function (): void {
          setmodal(false);
        }}
      />
      <div
        onClick={() => setmodal(true)}
        className="w-[846px] self-stretch p-[24px] justify-between items-center inline-flex hover:bg-primary-50 cursor-pointer"
      >
        <div className="w-20 text-center text-primary-500 body-medium">
          {notification.type}
        </div>
        <div className="w-[589px] flex-col justify-start items-end gap-3 inline-flex">
          <div
            className={`self-stretch ${notification.status == '안읽음' ? 'body-medium-bold' : 'body-medium'} text-gray-800 truncate`}
          >
            {notification.content}
          </div>
          <div className="self-stretch text-gray-800 caption-medium truncate">
            {notification.senderName}
          </div>
        </div>
        <div className="w-20 text-end text-[#4e4e4e] body-medium">
          {detailDate(notification.createdAt)}
        </div>
      </div>
    </>
  );
};
