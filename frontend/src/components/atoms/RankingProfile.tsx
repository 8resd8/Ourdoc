export const RankingProfile = ({
  rank,
  name,
  count,
  imagePath,
}: {
  rank: number;
  name: string;
  count: number;
  imagePath: string;
}) => (
  <div className="self-stretch p-3 rounded-[10px] flex-col justify-end items-center inline-flex">
    <div className="text-center text-gray-700 caption-medium">{count}권</div>
    <div className="flex-col justify-start items-center gap-1 flex">
      <img
        className="w-[60px] h-[60px] rounded-full border border-gray-200"
        src={imagePath ?? '/assets/images/profile.png'}
      />
      <div className="text-center text-gray-800 body-medium">{name}</div>
    </div>
    <div className="text-center text-gray-800 headline-medium">{rank}등</div>
  </div>
);
