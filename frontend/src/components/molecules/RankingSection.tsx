import { ClassReportRank } from '../../services/bookReportsService';
import { RankingProfile } from '../atoms/RankingProfile';

export const RankingSection = ({ data }: { data?: ClassReportRank }) => {
  return (
    <div className="w-[630px] h-[238px] p-[24px] bg-gray-0 rounded-[15px] shadow-xsmall flex-col justify-start items-start gap-[12px] inline-flex">
      <div className="self-stretch justify-start items-start gap-3 inline-flex">
        <div>
          <span className="text-gray-800 headline-medium">
            우리반 독서 랭킹{' '}
          </span>
          <span className="text-gray-500 headline-small">{`총 ${data?.totalReadCount}권`}</span>
        </div>
      </div>
      <div className="self-stretch grow shrink basis-0 px-6 justify-between items-center inline-flex">
        {data?.rankList.map((student, index) => {
          return (
            <RankingProfile
              key={index}
              rank={student.rank}
              imagePath={student.profileImagePath}
              name={`${student.studentNumber}번 ${student.name}`}
              count={student.readCount}
            />
          );
        })}
      </div>
    </div>
  );
};
