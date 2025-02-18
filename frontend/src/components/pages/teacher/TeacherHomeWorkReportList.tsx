import { useLocation, useNavigate } from 'react-router-dom';
import {
  getTeacherHomeworkBookDetailApi,
  HomeworkBook,
  TeacherHomeworkBookReport,
} from '../../../services/booksService';
import { useEffect, useState } from 'react';
import { Table, TableAlignType } from '../../atoms/Table';
import { ReportBookCard } from '../../atoms/ReportBookCard';
import { monthDayFormat } from '../../../utils/DateFormat';
import { TeacherReportBookCardListTile } from '../../atoms/TeacherReportBookCardListTile';
import { PaginationButton } from '../../atoms/PagenationButton';

const PAGE_SIZE = 10;

const TABLE_HEADER = [
  {
    label: 'No',
    width: 60,
  },
  {
    label: '내용',
    width: 360,
    align: TableAlignType.left,
  },
  {
    label: '번호',
    width: 60,
  },
  {
    label: '학생 이름',
  },
  {
    label: '제출 날짜',
  },
  {
    label: '승인 여부',
  },
];

const TeacherHomeWorkReportList = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const queryParams = new URLSearchParams(location.search);
  const homeworkId = queryParams.get('homeworkId');

  const [homeworkDetail, setHomeworkBook] = useState<HomeworkBook | null>(null);
  const [bookReports, setBookReports] = useState<TeacherHomeworkBookReport[]>(
    []
  );
  const [currentPage, setCurrentPage] = useState(0);
  const [totalPages, setTotalPages] = useState(1);

  const fetchHomeworkDetail = async (page = 0) => {
    try {
      const params = {
        page: page,
        size: PAGE_SIZE,
        homeworkId: Number(homeworkId),
      };
      const response = await getTeacherHomeworkBookDetailApi(params);
      setHomeworkBook(response.book);
      setBookReports(response.bookReports.content);
      setTotalPages(response.bookReports.totalPages);
      setCurrentPage(page);
    } catch (error) {
      console.error('Error fetching homework detail:', error);
    }
  };

  useEffect(() => {
    fetchHomeworkDetail();
  }, [homeworkId]);

  const onPageChange = (pageNumber: number) => {
    if (pageNumber >= 0 && pageNumber < totalPages) {
      fetchHomeworkDetail(pageNumber);
    }
  };

  return (
    <div className="mt-10 justify-items-center">
      {/* 숙제 도서 카드 */}
      <div className="flex justify-center mb-8">
        {homeworkDetail && (
          <ReportBookCard
            imageUrl={homeworkDetail.imageUrl}
            title={homeworkDetail.title}
            author={homeworkDetail.author}
            publisher={homeworkDetail.publisher}
            genre={homeworkDetail.genre}
            publishYear={homeworkDetail.publishYear}
          />
        )}
      </div>

      {/* 제출한 독서록 리스트 테이블 */}
      <div>
        <Table
          headers={TABLE_HEADER}
          datas={[
            bookReports.map((report, index) => {
              return (
                <TeacherReportBookCardListTile
                  key={index}
                  no={index + currentPage * 10 + 1}
                  content={report.beforeContent}
                  studentNumber={report.studentNumber}
                  studentName={report.studentName}
                  date={monthDayFormat(report.createdAt)}
                  onClick={() => {
                    navigate(
                      `/teacher/report/detail/${report.bookreportId}/?studentNumber=${report.studentNumber}&name=${report.studentName}`
                    );
                  }}
                  isApproved={report.bookReportApproveStatus == '있음'}
                />
              );
            }),
          ]}
        />

        <PaginationButton
          currentPage={currentPage}
          totalPages={totalPages}
          onPageChange={onPageChange}
        />
      </div>
    </div>
  );
};

export default TeacherHomeWorkReportList;
