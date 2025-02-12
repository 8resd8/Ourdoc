import { RankingProfile } from '../atoms/RankingProfile';

export const RankingSection = () => {
  return (
    <div className="w-[582px] h-[238px] p-6 bg-white rounded-[15px] shadow-xsmall flex-col justify-start items-start gap-3 inline-flex">
      <div className="self-stretch justify-start items-start gap-3 inline-flex">
        <div>
          <span className="text-gray-800 headline-medium">
            우리반 독서 랭킹{' '}
          </span>
          <span className="text-gray-500 headline-small">총 60권</span>
        </div>
      </div>
      <div className="self-stretch grow shrink basis-0 px-6 justify-between items-center inline-flex">
        <RankingProfile rank={1} name="1번 김현우" count={20} />
        <RankingProfile rank={2} name="1번 김현우" count={20} />
        <RankingProfile rank={3} name="1번 김현우" count={20} />
      </div>
    </div>
  );
};
