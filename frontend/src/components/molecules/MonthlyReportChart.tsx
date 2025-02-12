export const MonthlyReportChart = () => {
  return (
    <div className="w-[630px] h-[368px] p-6 bg-white rounded-[15px] shadow-[0px_2px_12px_1px_rgba(33,33,33,0.09)] flex-col justify-start items-start gap-3 inline-flex">
      <div className="text-gray-800 headline-medium">월별 독서록 개수</div>
      <div className="w-[582px] h-[276px] relative">
        <div className="w-[544.12px] h-[22.68px] px-6 left-[37.88px] top-[253.32px] absolute justify-between items-center inline-flex">
          <div className="text-gray-800 body-medium">3월</div>
          <div className="text-gray-800 body-medium">4월</div>
          <div className="text-gray-800 body-medium">5월</div>
          <div className="text-gray-800 body-medium">6월</div>
          <div className="text-gray-800 body-medium">7월</div>
          <div className="text-gray-800 body-medium">8월</div>
          <div className="text-gray-800 body-medium">9월</div>
          <div className="text-primary-500 body-medium">10월</div>
          <div className="text-gray-800 body-medium">11월</div>
          <div className="text-gray-800 body-medium">12월</div>
          <div className="text-gray-800 body-medium">1월</div>
          <div className="text-gray-800 body-medium">2월</div>
        </div>
        <div className="w-[505.32px] h-[0px] left-[557.98px] top-[241.97px] absolute origin-top-left rotate-180 border border-gray-200"></div>
        <div className="w-[505.32px] h-[0px] left-[557.98px] top-[200.38px] absolute origin-top-left rotate-180 border border-gray-200"></div>
        <div className="w-[505.32px] h-[0px] left-[557.98px] top-[158.79px] absolute origin-top-left rotate-180 border border-gray-200"></div>
        <div className="w-[505.32px] h-[0px] left-[557.98px] top-[117.21px] absolute origin-top-left rotate-180 border border-gray-200"></div>
        <div className="w-[505.32px] h-[0px] left-[557.98px] top-[75.62px] absolute origin-top-left rotate-180 border border-gray-200"></div>
        <div className="w-[505.32px] h-[0px] left-[557.98px] top-[34.03px] absolute origin-top-left rotate-180 border border-gray-200"></div>

        <div className="w-[22.17px] h-[94.52px] left-[60.05px] top-[147.45px] absolute bg-primary-500"></div>
        <div className="left-[63.67px] top-[128.55px] absolute text-center text-primary-500 body-small">
          12
        </div>
        <div className="w-[22.17px] h-[94.52px] left-[186.61px] top-[147.45px] absolute bg-primary-500"></div>
        <div className="w-[22.17px] h-[94.52px] left-[358.44px] top-[147.45px] absolute bg-primary-500"></div>
        <div className="left-[190.23px] top-[128.55px] absolute text-center text-primary-500 body-small">
          12
        </div>
        <div className="left-[362.06px] top-[128.55px] absolute text-center text-primary-500 body-small">
          12
        </div>
        <div className="w-[52.66px] h-[276px] px-3 py-6 left-0 top-0 absolute flex-col justify-between items-end inline-flex">
          <div className="text-right text-gray-800 body-medium">25권</div>
          <div className="text-right text-gray-800 body-medium">20권</div>
          <div className="text-right text-gray-800 body-medium">15권</div>
          <div className="text-right text-gray-800 body-medium">10권</div>
          <div className="self-stretch text-right text-gray-800 body-medium">
            5권
          </div>
          <div className="text-right text-gray-800 body-medium">0권</div>
        </div>
      </div>
    </div>
  );
};
