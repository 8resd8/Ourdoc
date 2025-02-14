import { useNavigate } from 'react-router-dom';
import { TrophySummarySection } from '../../molecules/TrophySummarySection';
import { NotificationSection } from '../../molecules/NotificationSection';
import { FavoriteBookListSection } from '../../molecules/FavoriteBookListSection';
import { WriteReportButton } from '../../atoms/WriteReportButton';
import { DebateButton } from '../../atoms/DebateButton';
import { StudentMainHomeworkListSection } from '../../molecules/StudentMainHomeworkListSection';

const StudentMain = () => {
  const navigate = useNavigate();
  return (
    <div className="flex w-[1064px] flex-col mx-auto py-[56px] space-y-[40px]">
      <div
        className={`bottom-[40px] right-[40px] fixed xl:top-[165px] xl:right-[calc((100vw)/12)]`}
      >
        <WriteReportButton onClick={() => {}} />
        <DebateButton />
      </div>
      <div className="flex justify-between">
        <TrophySummarySection />
        <NotificationSection />
      </div>
      <StudentMainHomeworkListSection />
      <FavoriteBookListSection />
    </div>
  );
};

export default StudentMain;
