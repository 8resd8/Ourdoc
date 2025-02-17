import { MonthlyReportChart } from '../../molecules/MonthlyReportChart';
import { MonthlyReportListSection } from '../../molecules/MonthlyReportListSection';
import { HomeworkListSection } from '../../molecules/HomeworkListSection';
import { NotificationSection } from '../../molecules/NotificationSection';
import { RankingSection } from '../../molecules/RankingSection';
import { MostReadBookSection } from '../../molecules/MostReadBookSection';
import { DebateButton } from '../../atoms/DebateButton';
import { useEffect, useState } from 'react';
import {
  MonthlyBookReport,
  classMonthlyReportApi,
  ClassReportRank,
  classReportRankApi,
  mostReadApi,
  MostReadBook,
} from '../../../services/bookReportsService';

const TeacherMain = () => {
  const [classReportRank, setclassReportRank] = useState<ClassReportRank>();
  const [classMonthlyReport, setclassMonthlyReport] = useState<
    MonthlyBookReport[]
  >([]);
  const [mostRead, setmostRead] = useState<MostReadBook>();

  useEffect(() => {
    const fetchData = async () => {
      const classReportRank = await classReportRankApi();
      const classMonthlyReport = await classMonthlyReportApi();
      const mostRead = await mostReadApi();
      setclassReportRank(classReportRank);
      setclassMonthlyReport(classMonthlyReport);
      setmostRead(mostRead);
    };
    fetchData();
  }, []);

  return (
    <div className="flex w-[1064px] flex-col mx-auto py-[56px] space-y-[40px]">
      <div
        className={`bottom-[40px] right-[40px] fixed xl:top-[165px] xl:right-[calc((100vw)/12)]`}
      >
        <DebateButton />
      </div>
      <div className="flex justify-between">
        <RankingSection data={classReportRank} />
        <NotificationSection />
      </div>
      <HomeworkListSection />
      <div className="flex justify-between">
        <MonthlyReportChart mockMonthlyReport={classMonthlyReport} />
        <MonthlyReportListSection />
      </div>
      {mostRead && (
        <div className="flex justify-between">
          <MostReadBookSection
            title={'학년에서 가장 많이 '}
            book={mostRead?.gradeMost.book}
            count={mostRead?.gradeMost.submitCount}
          />
          <MostReadBookSection
            title={'반에서 가장 많이 읽은 책'}
            book={mostRead?.classMost.book}
            count={mostRead?.gradeMost.submitCount}
          />
        </div>
      )}
    </div>
  );
};

export default TeacherMain;
