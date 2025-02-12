import { signoutApi } from '../../../services/usersService';
import { useNavigate } from 'react-router';
import { MonthlyReportChart } from '../../organisms/MonthlyReportChart';
import { MonthlyReportListSection } from '../../organisms/MonthlyReportListSection';
import { HomeworkListSection } from '../../organisms/HomeworkListSection';
import { NotificationSection } from '../../organisms/NotificationSection';
import { RankingSection } from '../../organisms/RankingSection';
import { MostReadBookSection } from '../../organisms/MostReadBookSection';

const TeacherMain = () => {
  const navigate = useNavigate();

  const logout = async () => {
    try {
      await signoutApi();
      navigate('/');
    } catch (error) {
      console.error('로그아웃 중 오류가 발생했습니다:', error);
    }
  };

  return (
    <div className="mx-auto p-4">
      <div onClick={logout} className="cursor-pointer text-primary-500">
        logout
      </div>
      <RankingSection />
      <NotificationSection />
      <HomeworkListSection />
      <MonthlyReportChart />
      <MonthlyReportListSection />
      <div>
        <MostReadBookSection title={'학년에서 가장 많이 읽은 책'} />
        <MostReadBookSection title={'반에서 가장 많이 읽은 책'} />
      </div>
    </div>
  );
};

export default TeacherMain;
