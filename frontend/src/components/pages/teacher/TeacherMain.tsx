import { MonthlyReportChart } from '../../molecules/MonthlyReportChart';
import { MonthlyReportListSection } from '../../molecules/MonthlyReportListSection';
import { HomeworkListSection } from '../../molecules/HomeworkListSection';
import { NotificationSection } from '../../molecules/NotificationSection';
import { RankingSection } from '../../molecules/RankingSection';
import { MostReadBookSection } from '../../molecules/MostReadBookSection';
import { DebateButton } from '../../atoms/DebateButton';
import {
  Book,
  getTeacherHomeworkBooksApi,
} from '../../../services/booksService';
import { useEffect, useState } from 'react';
import {
  MonthlyBookReport,
  classMonthlyReportApi,
  ClassReportRank,
  classReportRankApi,
  mostReadApi,
  MostReadBook,
} from '../../../services/bookReportsService';
import { teacherHomeworkBooksSelector } from '../../../recoil';

export const book: Book = {
  bookId: 1,
  title: '어린왕자',
  author: '생택쥐페리호',
  publisher: '새움',
  imageUrl: '/assets/images/bookImage.png',
  publishYear: Date.now.toString(),
  genre: '문학',
};

const TeacherMain = () => {
  const [classReportRank, setclassReportRank] = useState<ClassReportRank>();
  const [classMonthlyReport, setclassMonthlyReport] = useState<
    MonthlyBookReport[]
  >([]);
  const [mostRead, setmostRead] = useState<MostReadBook>();
  const [studentCount, setStudentCount] = useState(0);

  useEffect(() => {
    const fetchData = async () => {
      const classReportRank = await classReportRankApi();
      const classMonthlyReport = await classMonthlyReportApi();
      const teacherHomework = await getTeacherHomeworkBooksApi();
      console.log(teacherHomework);
      // setStudentCount(teacherHomework.homeworks);

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
