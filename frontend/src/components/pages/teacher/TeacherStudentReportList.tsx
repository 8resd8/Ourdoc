import { useState, useEffect } from 'react';
import {
  BookReports,
  StudentBook,
  TeacherBook,
  TeacherBookReports,
  getTeacherBookDetailsApi,
} from '../../../services/booksService';
import { useNavigate, useParams } from 'react-router-dom';
import { Table, TableAlignType } from '../../atoms/Table';
import { ReportBookCard } from '../../atoms/ReportBookCard';
import { monthDayFormat } from '../../../utils/DateFormat';
import { TeacherReportBookCardListTile } from '../../atoms/TeacherReportBookCardListTile';
import { PaginationButton } from '../../atoms/PagenationButton';

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

const TeacherStudentReportList = () => {
  const { id } = useParams();
  const navigate = useNavigate();

  const [bookDetail, setBookDetail] = useState<TeacherBook | null>(null);
  const [bookReports, setBookReports] = useState<TeacherBookReports>();
  const [currentPage, setCurrentPage] = useState(0);
  const [totalPages, setTotalPages] = useState(1);

  const fetchBookDetail = async (page = 0) => {
    try {
      const response = await getTeacherBookDetailsApi(Number(id), page);
      setBookDetail(response);
      setBookReports(response.bookReports);
      setTotalPages(response.bookReports.totalPages);
      setCurrentPage(page);
    } catch (error) {
      console.error('Error fetching book detail:', error);
    }
  };

  const onPageChange = (pageNumber: number) => {
    if (pageNumber >= 0 && pageNumber < totalPages) {
      fetchBookDetail(pageNumber);
    }
  };

  useEffect(() => {
    fetchBookDetail();
  }, [id]);

  return (
    <div className="mt-10 justify-items-center">
      {/* 숙제 도서 카드 */}
      {bookDetail && (
        <ReportBookCard
          imageUrl={bookDetail.imageUrl}
          title={bookDetail.title}
          author={bookDetail.author}
          publisher={bookDetail.publisher}
          genre={bookDetail.genre}
          publishYear={bookDetail.publishYear}
        />
      )}

      {/* 제출한 독서록 리스트 테이블 */}
      <div>
        <Table
          headers={TABLE_HEADER}
          datas={[
            bookReports?.content.map((report, index) => {
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

export default TeacherStudentReportList;
