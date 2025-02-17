import { useRecoilState } from 'recoil';
import { selectedMonthState } from '../../recoil/atoms/chartMonthAtom';
import { useEffect, useState } from 'react';
import {
  classDailyReportApi,
  DayReport,
  studentDailyReportApi,
} from '../../services/bookReportsService';
import { AddDivider } from '../../utils/AddDivder';
import { getRecoil } from 'recoil-nexus';
import { studentGradeState } from '../../recoil';

const ReportItem = ({ date, count }: { date: number; count: number }) => (
  <div className="w-[348px] px-[72px] py-3 justify-between items-center inline-flex">
    <div className="text-gray-800 body-medium">{date}일</div>
    <div className="text-gray-800 body-medium">{count}권</div>
  </div>
);

export const MonthlyReportListSection = ({
  isStudent,
}: {
  isStudent?: boolean;
}) => {
  const [selectedMonth] = useRecoilState(selectedMonthState);
  const [classDailyReport, setclassDailyReport] = useState<DayReport[]>([]);
  const grade = getRecoil(studentGradeState);
  useEffect(() => {
    const fetchData = async () => {
      if (isStudent) {
        const classDailyReport = await studentDailyReportApi({
          month: selectedMonth,
          grade: grade,
        });
        setclassDailyReport(classDailyReport);
      } else {
        const classDailyReport = await classDailyReportApi({
          month: selectedMonth,
        });
        setclassDailyReport(classDailyReport);
      }
    };
    fetchData();
  }, [selectedMonth, grade]);

  return (
    <div className="w-[414px] h-[368px] p-6 bg-gray-0 rounded-[15px] shadow-xsmall flex-col justify-start items-start gap-3 inline-flex ">
      <div className="text-gray-800 headline-medium">
        {selectedMonth + '월'}
      </div>
      <div className="self-stretch grow shrink basis-0 justify-start items-start gap-2 inline-flex overflow-y-scroll">
        <div className="grow shrink basis-0 self-stretch flex-col justify-start items-start inline-flex">
          {AddDivider({
            itemList: classDailyReport.map((report, index) => (
              <ReportItem
                key={index}
                date={report.day}
                count={report.readCount}
              />
            )),
          })}
        </div>
      </div>
    </div>
  );
};
