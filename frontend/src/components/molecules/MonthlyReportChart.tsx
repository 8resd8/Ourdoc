import { useRecoilState } from 'recoil';
import { selectedMonthState } from '../../recoil/atoms/chartMonthAtom';
import { MonthlyBookReport } from '../../services/bookReportsService';
import { MonthType } from '../../styles/monthType';
import { ReportChartBar } from '../atoms/ReportChartBar';
import { ReportChartMonthTile } from '../atoms/ReportChartMonthTile';

//차트 상수
const MAX_CHART_BAR_TOP = 34.03;
const MIN_CHART_BAR_TOP = 241.97;
const BAR_HEIGHT_PER_COUNT = 8.4;

export const MonthlyReportChart = ({
  mockMonthlyReport,
}: {
  mockMonthlyReport: MonthlyBookReport[];
}) => {
  const monthList = Object.values(MonthType).filter(
    (v) => typeof v === 'number'
  );
  const [selectedMonth, setSelectedMonth] = useRecoilState(selectedMonthState);

  return (
    <div className="w-[630px] h-[368px] p-6 bg-gray-0 rounded-[15px] shadow-xsmall flex-col justify-start items-start gap-3 inline-flex">
      <div className="text-gray-800 headline-medium">월별 독서록 개수</div>

      <div className="w-[582px] h-[276px] relative">
        <div className="w-[544.12px] h-[22.68px] px-6 left-[37.88px] top-[253.32px] absolute justify-between items-center inline-flex">
          {monthList.map((month) => (
            <ReportChartMonthTile
              key={month}
              month={month as number}
              isActive={month === selectedMonth}
              onClick={() => setSelectedMonth(month as number)}
            />
          ))}
        </div>
        <div className="w-[505.32px] h-[0px] left-[557.98px] top-[241.97px] absolute origin-top-left rotate-180 border border-gray-200"></div>
        <div className="w-[505.32px] h-[0px] left-[557.98px] top-[200.38px] absolute origin-top-left rotate-180 border border-gray-200"></div>
        <div className="w-[505.32px] h-[0px] left-[557.98px] top-[158.79px] absolute origin-top-left rotate-180 border border-gray-200"></div>
        <div className="w-[505.32px] h-[0px] left-[557.98px] top-[117.21px] absolute origin-top-left rotate-180 border border-gray-200"></div>
        <div className="w-[505.32px] h-[0px] left-[557.98px] top-[75.62px] absolute origin-top-left rotate-180 border border-gray-200"></div>
        <div className="w-[505.32px] h-[0px] left-[557.98px] top-[34.03px] absolute origin-top-left rotate-180 border border-gray-200"></div>
        {/* 월별 Bar */}
        <div className="w-[544.12px] px-6 left-[37.88px] absolute justify-between items-end inline-flex">
          {mockMonthlyReport.map((report) => (
            <ReportChartBar
              key={report.month}
              count={report.readCount}
              top={Math.max(
                MAX_CHART_BAR_TOP,
                MIN_CHART_BAR_TOP - BAR_HEIGHT_PER_COUNT * report.readCount
              )}
              onClick={() => setSelectedMonth(report.month as number)}
            />
          ))}
        </div>
        <div className="h-[276px] px-3 py-6 left-0 top-0 absolute flex-col justify-between items-end inline-flex">
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
