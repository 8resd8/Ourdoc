import { useState } from 'react';
import { detailDate } from '../../utils/DateFormat';
import Modal from '../commons/Modal';
import {
  markNotificationAsReadApi,
  Notification,
} from '../../services/notificationsService';

export const NotificationTile = ({
  notification,
  fetchData,
}: {
  notification: Notification;
  fetchData: () => void;
}) => {
  const [modal, setmodal] = useState(false);

  const markNotificationAsRead = async () => {
    await markNotificationAsReadApi(notification.notificationId);
    fetchData();
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
        onClick={() => {
          setmodal(true);
        }}
        className="self-stretch py-3 justify-start items-center gap-6 inline-flex hover:bg-primary-50 cursor-pointer"
      >
        <div className="grow shrink basis-0 h-6 justify-start items-center gap-6 flex px-2">
          <div className="w-[50px] text-start text-primary-500 body-medium">
            {notification.type}
          </div>
          <div className="grow shrink basis-0 h-6 justify-start items-center gap-3 flex">
            <div className="grow shrink basis-0 text-gray-800 body-medium truncate">
              {notification.content}
            </div>
            <div className="text-gray-800 caption-medium">
              {detailDate(notification.createdAt)}
            </div>
          </div>
        </div>
      </div>
    </>
  );
};
