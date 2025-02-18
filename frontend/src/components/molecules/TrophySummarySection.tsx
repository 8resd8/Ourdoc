export const TrophySummarySection = ({
  stampCount,
  studentTotalCount,
  myRank,
  recentFeedback,
}: {
  stampCount: number;
  studentTotalCount: number;
  myRank: number;
  recentFeedback: string;
}) => {
  return (
    <div className="h-[238px] p-6 bg-gray-0 rounded-[15px] shadow-xsmall flex-col justify-start items-start gap-3 inline-flex">
      <div className="text-gray-800 headline-medium">성취도</div>
      <div className="self-stretch grow shrink basis-0 flex-col justify-center items-start flex">
        <div className="self-stretch py-6 justify-center items-center gap-9 inline-flex">
          <div>
            <span className="text-primary-500 body-medium">칭찬도장 </span>
            <span className="text-primary-500 headline-medium">
              {stampCount}
            </span>
            <span className="text-primary-500 body-medium">개를 모았어요.</span>
          </div>
          <div>
            <span className="text-primary-500 body-medium">
              반 친구 {studentTotalCount}명 중에{' '}
            </span>
            <span className="text-primary-500 headline-medium">{myRank}</span>
            <span className="text-primary-500 body-medium">
              번째로 많이 읽었어요.
            </span>
          </div>
        </div>
        <div className="flex-col justify-start items-start gap-6 flex">
          <div className="w-[582px] h-[0px] border border-gray-200"></div>
          <div className="grow shrink basis-0 flex-col justify-start items-start flex">
            <div className="text-gray-800 caption-medium">
              최근 AI 선생님 의견
            </div>
            <div className="py-3 justify-center items-center inline-flex">
              <div className="w-[582px] text-gray-700 body-small truncate">
                {recentFeedback}
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};
