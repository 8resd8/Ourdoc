import { Link } from 'react-router-dom';
import { TrophySummarySection } from '../../molecules/TrophySummarySection';
import { NotificationSection } from '../../molecules/NotificationSection';
import { ClassNameHomeworkListSection } from '../../molecules/ClassHomeworkListSection';
import { FavoriteBookListSection } from '../../molecules/FavoriteBookListSection';
import { WriteReportButton } from '../../atoms/WriteReportButton';
import { DebateButton } from '../../atoms/DebateButton';

const StudentMain = () => {
  return (
    <div>
      <Link to="/debate/room">debateRoom 이동</Link>
      <div>
        <WriteReportButton onClick={() => {}} />
        <DebateButton onClick={() => {}} />
      </div>
      <div>
        <TrophySummarySection />
        <NotificationSection />
      </div>
      <ClassNameHomeworkListSection />
      <FavoriteBookListSection />
    </div>
  );
};

export default StudentMain;
