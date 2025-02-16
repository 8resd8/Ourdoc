import { useEffect, useState } from 'react';
import { AddDivider } from '../../../utils/AddDivder';
import { NotificationDetailTile } from '../../atoms/NotificationDetailTile';
import {
  getNotificationsApi,
  NotificationPageable,
} from '../../../services/notificationsService';
import { PaginationButton } from '../../atoms/PagenationButton';

const TeacherNoti = () => {
  const [notifications, setnotifications] = useState<NotificationPageable>();
  const [currentPage, setCurrentPage] = useState(0);
  const [totalPages, setTotalPages] = useState(1);

  const fetchData = async (page = 0) => {
    try {
      const params = { size: 5, page };
      const response = await getNotificationsApi(params);

      setnotifications(response);
      setTotalPages(response.totalPages);
      setCurrentPage(page);
    } catch (error) {}
  };

  useEffect(() => {
    fetchData();
  }, []);

  const onPageChange = (pageNumber: number) => {
    if (pageNumber >= 0 && pageNumber < totalPages) {
      fetchData(pageNumber);
    }
  };
  return (
    <div className={'flex w-[846px] flex-col mx-auto py-[56px] space-y-[40px]'}>
      <div className="flex justify-between items-center mb-6">
        <h1 className="headline-medium text-gray-800">알림함</h1>
      </div>
      <AddDivider
        itemList={
          notifications
            ? notifications?.content.map((item, index) => (
                <NotificationDetailTile key={index} notification={item} />
              ))
            : []
        }
      />

      <PaginationButton
        currentPage={currentPage}
        totalPages={totalPages}
        onPageChange={onPageChange}
      />
    </div>
  );
};

export default TeacherNoti;
