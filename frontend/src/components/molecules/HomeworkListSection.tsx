import { HomeworkListTile } from '../atoms/HomeworkListTile';

export const HomeworkListSection = () => {
  return (
    <div className="w-[1064px] h-[277px] p-6 bg-white rounded-[15px] shadow-[0px_2px_12px_1px_rgba(33,33,33,0.09)] flex-col justify-start items-start gap-3 inline-flex">
      <div className="w-[1016px] justify-between items-start inline-flex">
        <div className="text-gray-800 headline-medium">숙제 현황</div>
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
      <div className="self-stretch h-[191px] flex-col justify-start items-center gap-3 flex">
        <div className="self-stretch h-[191px] relative">
          <div className="w-[1016px] h-12 px-6 py-3 left-0 top-0 absolute border-b border-gray-900 justify-start items-center gap-[18px] inline-flex">
            <div className="w-[60px] text-center text-gray-800 body-medium">
              No
            </div>
            <div className="w-[332px] text-gray-800 body-medium">제목</div>
            <div className="w-[120px] text-center text-gray-800 body-medium">
              지은이
            </div>
            <div className="w-[120px] text-center text-gray-800 body-medium">
              출판사
            </div>
            <div className="w-[120px] text-center text-gray-800 body-medium">
              출판 년도
            </div>
            <div className="w-[120px] text-center text-gray-800 body-medium">
              제출 현황
            </div>
          </div>
          <div className="w-[1016px] h-36 left-0 top-[47px] absolute flex-col justify-start items-start inline-flex">
            <HomeworkListTile
              no={1}
              title="어린 왕자는 진정한 소중함은 눈에 보이지 않고 마음으로 보아야 한다는 깨달음을 주는 책이다."
              date="5월 1일"
              status="10"
            />
            <HomeworkListTile
              no={2}
              title="어린 왕자는 진정한 소중함은 눈에 보이지 않고 마음으로 보아야 한다는 깨달음을 주는 책이다."
              date="5월 1일"
              status="10"
            />
            <HomeworkListTile
              no={3}
              title="어린 왕자는 진정한 소중함은 눈에 보이지 않고 마음으로 보아야 한다는 깨달음을 주는 책이다."
              date="5월 1일"
              status="10"
            />
          </div>
        </div>
      </div>
    </div>
  );
};
