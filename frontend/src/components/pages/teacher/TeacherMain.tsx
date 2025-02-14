import { MonthlyReportChart } from '../../molecules/MonthlyReportChart';
import { MonthlyReportListSection } from '../../molecules/MonthlyReportListSection';
import { HomeworkListSection } from '../../molecules/HomeworkListSection';
import { NotificationSection } from '../../molecules/NotificationSection';
import { RankingSection } from '../../molecules/RankingSection';
import { MostReadBookSection } from '../../molecules/MostReadBookSection';
import { DebateButton } from '../../atoms/DebateButton';

const TeacherMain = () => {
  return (
    <div className="flex w-[1064px] flex-col mx-auto py-[56px] space-y-[40px]">
      <div
        className={`bottom-[40px] right-[40px] fixed xl:top-[165px] xl:right-[calc((100vw)/12)]`}
      >
        <DebateButton />
      </div>
      <div className="flex justify-between">
        <RankingSection />
        <NotificationSection />
      </div>
      <HomeworkListSection />
      <div className="flex justify-between">
        <MonthlyReportChart />
        <MonthlyReportListSection />
      </div>
      <div className="flex justify-between">
        <MostReadBookSection title={'학년에서 가장 많이 읽은 책'} />
        <MostReadBookSection title={'반에서 가장 많이 읽은 책'} />
      </div>
    </div>
  );
};

export default TeacherMain;
