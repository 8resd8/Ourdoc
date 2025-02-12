import { NotificationTile } from '../atoms/NotificationTile';

export const NotificationSection = () => {
  return (
    <div className="w-[414px] h-[238px] p-6 bg-white rounded-[15px] shadow-[0px_2px_12px_1px_rgba(33,33,33,0.09)] flex-col justify-start items-start gap-3 inline-flex">
      <div className="self-stretch justify-between items-start inline-flex">
        <div>
          <span className="text-gray-800 headline-medium">알림 </span>
          <span className="text-gray-500 headline-small">1개</span>
        </div>
        <div data-svg-wrapper className="relative">
          <svg
            width="24"
            height="24"
            viewBox="0 0 24 24"
            fill="none"
            xmlns="http://www.w3.org/2000/svg"
          >
            <path
              d="M12 5V19"
              stroke="#25282B"
              strokeWidth="2"
              strokeLinecap="round"
              strokeLinejoin="round"
            />
            <path
              d="M5 12H19"
              stroke="#25282B"
              strokeWidth="2"
              strokeLinecap="round"
              strokeLinejoin="round"
            />
          </svg>
        </div>
      </div>
      <div className="self-stretch h-[152px] pt-2 flex-col justify-start items-start flex">
        <NotificationTile type="상장" message="착한 어린이상" time="1시간 전" />
        <NotificationTile type="상장" message="착한 어린이상" time="1시간 전" />
        <NotificationTile type="상장" message="착한 어린이상" time="1시간 전" />
      </div>
    </div>
  );
};
