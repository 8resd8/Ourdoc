import { useRecoilState } from 'recoil';
import { selectedMonthState } from '../../recoil/atoms/chartMonthAtom';

const ReportItem = ({ date, count }: { date: string; count: number }) => (
  <div className="w-[366px] px-[72px] py-3 border-b border-gray-200 justify-between items-center inline-flex">
    <div className="text-gray-800 body-medium">{date}</div>
    <div className="text-gray-800 body-medium">{count}권</div>
  </div>
);

export const MonthlyReportListSection = () => {
  const [selectedMonth] = useRecoilState(selectedMonthState);

  const reports = Array.from({ length: selectedMonth }, (_, index) => ({
    date: '11일',
    count: 1,
  }));

  return (
    <div className="w-[414px] h-[368px] p-6 bg-gray-0 rounded-[15px] shadow-xsmall flex-col justify-start items-start gap-3 inline-flex ">
      <div className="text-gray-800 headline-medium">
        {selectedMonth + '월'}
      </div>
      <div className="self-stretch grow shrink basis-0 justify-start items-start gap-2 inline-flex overflow-y-scroll">
        <div className="grow shrink basis-0 self-stretch flex-col justify-start items-start inline-flex">
          {reports.map((report, index) => (
            <ReportItem key={index} date={report.date} count={report.count} />
          ))}
        </div>
      </div>
    </div>
  );
};
