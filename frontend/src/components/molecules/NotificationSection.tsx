import { useNavigate } from 'react-router-dom';
import { NotificationTile } from '../atoms/NotificationTile';
import { PlusButton } from '../atoms/PlusButton';
import { AddDivider } from '../../utils/AddDivder';

export const NotificationSection = () => {
  const navigate = useNavigate();
  const foo = ['asdf', 'asdf', 'asdf'];

  return (
    <div className="w-[414px] h-[238px] p-6 bg-gray-0 rounded-[15px] shadow-xsmall flex-col justify-start items-start gap-5 inline-flex">
      <div className="self-stretch justify-between items-start inline-flex">
        <div>
          <span className="text-gray-800 headline-medium">알림 </span>
          <span className="text-gray-500 headline-small">1개</span>
        </div>
        <PlusButton
          onClick={() => {
            navigate('/teacher/noti');
          }}
        />
      </div>

      <div className="self-stretch h-[152px] flex-col items-start flex">
        <AddDivider
          itemList={[
            <NotificationTile
              type="상장"
              message="착한 어린이상"
              time="1시간 전"
            />,
            <NotificationTile
              type="독서록"
              message="착한 어린이상"
              time="1시간 전"
            />,
            <NotificationTile
              type="상장"
              message="착한 어린이상"
              time="1시간 전"
            />,
          ]}
        />
      </div>
    </div>
  );
};
