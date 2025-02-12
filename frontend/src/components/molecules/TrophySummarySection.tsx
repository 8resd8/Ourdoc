export const TrophySummarySection = () => {
  return (
    <div className="h-[238px] p-6 bg-white rounded-[15px] shadow-[0px_2px_12px_1px_rgba(33,33,33,0.09)] flex-col justify-start items-start gap-3 inline-flex">
      <div className="text-gray-800 text-[22px] font-semibold font-['Pretendard'] leading-relaxed">
        성취도
      </div>
      <div className="self-stretch grow shrink basis-0 flex-col justify-center items-start flex">
        <div className="self-stretch py-6 justify-center items-center gap-9 inline-flex">
          <div>
            <span className="text-[#ff6f61] text-base font-normal font-['Pretendard'] leading-normal">
              칭찬도장{' '}
            </span>
            <span className="text-[#ff6f61] text-[22px] font-semibold font-['Pretendard'] leading-relaxed">
              7
            </span>
            <span className="text-[#ff6f61] text-base font-normal font-['Pretendard'] leading-normal">
              개를 모았어요.
            </span>
          </div>
          <div>
            <span className="text-[#ff6f61] text-base font-normal font-['Pretendard'] leading-normal">
              반 친구 12명 중에{' '}
            </span>
            <span className="text-[#ff6f61] text-[22px] font-semibold font-['Pretendard'] leading-relaxed">
              1
            </span>
            <span className="text-[#ff6f61] text-base font-normal font-['Pretendard'] leading-normal">
              번째로 많이 읽었어요.
            </span>
          </div>
        </div>
        <div className="flex-col justify-start items-start gap-6 flex">
          <div className="w-[582px] h-[0px] border border-gray-200"></div>
          <div className="grow shrink basis-0 flex-col justify-start items-start flex">
            <div className="text-gray-800 text-xs font-normal font-['Pretendard'] leading-none">
              최근 AI 선생님 의견
            </div>
            <div className="py-3 justify-center items-center inline-flex">
              <div className="w-[582px] text-gray-700 text-sm font-normal font-['Pretendard'] leading-tight truncate">
                참 잘했어요!참 잘했어요!참 잘했어요!참 잘했어요!참 잘했어요!참
                잘했어요!참 잘했어요!참 잘했어요!참 잘했어요!참 잘했어요!참
                잘했어요!참 잘했어요!참 잘했어요!참 잘했어요!참 잘했어요!참
                잘했어요!참 잘했어요!참 잘했어요!참 잘했어요!참 잘했어요!
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};
