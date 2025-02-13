import { AddDivider } from '../../../utils/AddDivder';
import { NotificationDetailTile } from '../../atoms/NotificationDetailTile';

const TeacherNoti = () => {
  return (
    <div className={'flex w-[846px] flex-col mx-auto py-[56px] space-y-[40px]'}>
      <div className="flex justify-between items-center mb-6">
        <h1 className="headline-medium text-gray-800">알림함</h1>
      </div>
      <AddDivider
        itemList={[
          <NotificationDetailTile />,
          <NotificationDetailTile />,
          <NotificationDetailTile />,
          <NotificationDetailTile />,
          <NotificationDetailTile />,
        ]}
      />
    </div>
  );
};

export default TeacherNoti;
