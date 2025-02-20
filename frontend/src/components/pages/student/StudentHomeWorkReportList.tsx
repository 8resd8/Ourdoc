import { useState, useEffect } from 'react';
import {
  BookReport,
  HomeworkBook,
  addFavoriteBookApi,
  getStudentHomeworkBookDetailApi,
  removeFavoriteBookApi,
} from '../../../services/booksService';
import { useLocation, useNavigate } from 'react-router-dom';
import { studentSubmitHomeworkReportApi } from '../../../services/bookReportsService';
import { ReportBookCard } from '../../atoms/ReportBookCard';
import { Table, TableAlignType } from '../../atoms/Table';
import { ReportBookCardListTile } from '../../atoms/ReportBookCardListTile';
import { PaginationButton } from '../../atoms/PagenationButton';
import { monthDayFormat } from '../../../utils/DateFormat';
import { notify } from '../../commons/Toast';

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
    label: '제출 날짜',
  },
  {
    label: '도장 여부',
  },
  {
    label: '숙제 제출',
  },
];

const StudentHomeWorkReportList = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const queryParams = new URLSearchParams(location.search);
  const homeworkId = queryParams.get('homeworkId');

  const [homeworkDetail, setHomeworkBook] = useState<HomeworkBook | null>(null);
  const [bookReports, setBookReports] = useState<BookReport[]>([]);
  const [currentPage, setCurrentPage] = useState(0);
  const [totalPages, setTotalPages] = useState(1);
  const [favorite, setFavorite] = useState(false);
  const [submitHomework, setSubmitHomework] = useState(false);

  const fetchHomeworkDetail = async (page = 0) => {
    try {
      const params = {
        page: page,
        size: PAGE_SIZE,
        homeworkId: Number(homeworkId),
      };
      const response = await getStudentHomeworkBookDetailApi(params);
      setHomeworkBook(response.book);
      setFavorite(response.book.bookStatus.favorite);
      setBookReports(response.bookReports.content);
      setTotalPages(response.bookReports.totalPages);
      setCurrentPage(page);
      setSubmitHomework(response.homeworkSubmitStatus);
    } catch (error) {
      console.error('Error fetching homework detail:', error);
    }
  };

  useEffect(() => {
    fetchHomeworkDetail();
  }, [homeworkId]);

  const favoriteBook = async () => {
    try {
      if (homeworkDetail) {
        await addFavoriteBookApi(homeworkDetail.bookId);
        notify({ type: 'success', text: '관심 도서가 등록되었습니다.' });
        fetchHomeworkDetail();
      }
    } catch (error) {
      notify({ type: 'error', text: '관심 도서 등록 중 오류가 발생했습니다.' });
    }
  };
  const favoriteCancel = async () => {
    try {
      if (homeworkDetail) {
        await removeFavoriteBookApi(homeworkDetail.bookId);
        notify({ type: 'success', text: '관심 도서가 해제제되었습니다.' });
        fetchHomeworkDetail();
      }
    } catch (error) {
      notify({ type: 'error', text: '관심 도서 해제 중 오류가 발생했습니다.' });
    }
  };

  const submitHomeworkHandle = async (
    bookreportId: number,
    homeworkId: number
  ) => {
    await studentSubmitHomeworkReportApi(bookreportId, homeworkId);
    fetchHomeworkDetail();
  };

  const onPageChange = (pageNumber: number) => {
    if (pageNumber >= 0 && pageNumber < totalPages) {
      fetchHomeworkDetail(pageNumber);
    }
  };

  return (
    <div className="mt-10 justify-items-center">
      {/* 숙제 도서 카드 */}
      <div className="flex flex-col justify-center mb-8">
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
        {/* 버튼 섹션 */}
        <div className="flex space-x-4 mb-4 justify-between mt-4 w-[630px]">
          {favorite ? (
            <button
              onClick={favoriteCancel}
              className="cursor-pointer body-medium px-4 py-2 text-gray-500 rounded-[10px]  border border-gray-500 hover:brightness-80"
            >
              관심 해제
            </button>
          ) : (
            <button
              onClick={favoriteBook}
              className="cursor-pointer body-medium px-4 py-2 text-secondary-500 rounded-[10px]  border border-secondary-500 hover:brightness-80"
            >
              관심 등록
            </button>
          )}
          <button
            onClick={() => {
              navigate(
                `/student/report/write/${homeworkDetail?.bookId}?homeworkId=${homeworkId}&title=${homeworkDetail?.title}&author=${homeworkDetail?.author}&publisher=${homeworkDetail?.publisher}`
              );
            }}
            className="cursor-pointer body-medium px-4 py-2 text-gray-0 rounded-[10px] bg-primary-500 hover:bg-primary-400"
          >
            독서록 작성
          </button>
        </div>
      </div>

      {/* 제출한 독서록 리스트 테이블 */}
      <div>
        <Table
          headers={TABLE_HEADER}
          datas={[
            bookReports.map((report, index) => {
              return (
                <ReportBookCardListTile
                  key={index}
                  no={index + currentPage * 10 + 1}
                  content={report.beforeContent}
                  date={monthDayFormat(report.createdAt)}
                  onClick={() => {
                    navigate(`/student/report/detail/${report.id}`);
                  }}
                  isApproved={report.bookReportApproveStatus == '있음'}
                  HomeworkButton={
                    submitHomework === false ? (
                      <div
                        onClick={() =>
                          submitHomeworkHandle(
                            Number(report.id),
                            Number(homeworkId)
                          )
                        }
                        className="px-3 py-1 rounded-[5px] text-center body-small border justify-center items-center inline-flex border-system-info text-system-info cursor-pointer"
                      >
                        제출하기
                      </div>
                    ) : (
                      <div
                        className={`px-3 py-1 rounded-[5px] text-center body-small border justify-center items-center inline-flex ${report.homeworkSubmitStatus === '제출' ? 'border-system-info text-system-info' : 'border-gray-300 text-gray-300'}`}
                      >
                        {report.homeworkSubmitStatus === '제출'
                          ? '제출'
                          : '미제출'}
                      </div>
                    )
                  }
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

export default StudentHomeWorkReportList;
