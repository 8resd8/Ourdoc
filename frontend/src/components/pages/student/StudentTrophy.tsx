import { Divider } from '@mui/material';
import { MonthlyReportChart } from '../../molecules/MonthlyReportChart';
import { MonthlyReportListSection } from '../../molecules/MonthlyReportListSection';
import { useEffect, useState } from 'react';
import { StudentClassAverageSection } from '../../molecules/StudentClassAverageSection';
import { TrophyAwardSection } from '../../molecules/TrophyAwardSection';
import { TrophyStampSection } from '../../molecules/TrophyStampSection';
import {
  MonthlyBookReport,
  ReportStatistics,
  studentMonthlyReportApi,
  studentReportStatisticsApi,
} from '../../../services/bookReportsService';
import { getRecoil, setRecoil } from 'recoil-nexus';
import { currentUserState, studentGradeState } from '../../../recoil';
import {
  AwardDetail,
  getStudentAwardsListApi,
} from '../../../services/awardsService';

const StudentTrophy = () => {
  const user = getRecoil(currentUserState);
  const [selectedGrade, setSelectedGrade] = useState(user.grade ?? 1);
  const [studentMonthlyReport, setstudentMonthlyReport] = useState<
    MonthlyBookReport[]
  >([]);
  const [studentReportStatistics, setstudentReportStatistics] =
    useState<ReportStatistics>();
  const [studentAwardsList, setstudentAwardsList] = useState<AwardDetail[]>();
  const createIndexArray = (length: number) => {
    return Array.from({ length }, (_, i) => i + 1);
  };

  useEffect(() => {
    const fetchData = async () => {
      const classMonthlyReport = await studentMonthlyReportApi({
        grade: selectedGrade,
      });
      const studentReportStatistics = await studentReportStatisticsApi({
        grade: selectedGrade,
      });
      const studentAwardsList = await getStudentAwardsListApi();

      setstudentMonthlyReport(classMonthlyReport);
      setRecoil(studentGradeState, selectedGrade);
      setstudentReportStatistics(studentReportStatistics);
      setstudentAwardsList(studentAwardsList);
    };
    fetchData();
  }, [selectedGrade]);

  return (
    <div className="flex flex-col justify-center items-center ">
      <div className="flex flex-col justify-center items-center">
        {/* 숙제 도서 제목 */}
        <h2 className="headline-large text-center mb-[36px] mt-[40px]">
          {selectedGrade}학년
        </h2>
        {/* 카테고리 버튼 */}
        <div className="flex justify-center space-x-8 mb-10">
          {Object.values(createIndexArray(user.grade ?? 1)).map((type) => {
            const isSelected = selectedGrade === type;

            return (
              <button
                key={type}
                className={`flex flex-col items-center p-3 bg-gray-0 rounded-[15px] shadow-xxsmall cursor-pointer transition-colors
                ${isSelected ? 'bg-white text-primary-500' : 'bg-gray-0 text-gray-700'}
                ${!isSelected ? 'hover:border-primary-400 group' : ''}
              `}
                onClick={() => setSelectedGrade(type)}
              >
                <span
                  className={`transition-colors headline-large w-12
                ${isSelected ? 'text-primary-500' : 'text-gray-700'} ${!isSelected ? 'group-hover:text-primary-400' : ''}`}
                >
                  {type}
                </span>
                <span
                  className={`transition-colors caption-medium 
                  ${isSelected ? 'text-primary-500' : 'text-gray-700'} ${!isSelected ? 'group-hover:text-primary-400' : ''}`}
                >
                  학년
                </span>
              </button>
            );
          })}
        </div>

        <Divider
          className="border-gray-200"
          sx={{ width: '100vw', mb: '36px' }}
        />
        <div className="w-[1064px] flex flex-col pb-10 gap-20 justify-between">
          <div className="flex justify-between">
            <MonthlyReportChart mockMonthlyReport={studentMonthlyReport} />
            <MonthlyReportListSection isStudent />
          </div>
          {studentReportStatistics && (
            <div className="flex justify-between">
              <StudentClassAverageSection
                title={'학급 평균과 나'}
                otherCount={Math.round(
                  studentReportStatistics?.averageReadCount
                )}
                myCount={studentReportStatistics?.readCount}
                isClass
              />
              <StudentClassAverageSection
                title={'반 1등과 나'}
                otherCount={studentReportStatistics?.bestReadCount}
                myCount={studentReportStatistics?.readCount}
              />
            </div>
          )}
          {studentAwardsList && (
            <TrophyAwardSection awards={studentAwardsList} />
          )}

          <TrophyStampSection count={28} />
        </div>
      </div>
    </div>
  );
};
export default StudentTrophy;
