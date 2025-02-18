import { useState, useEffect } from 'react';
import {
  BookReports,
  StudentBook,
  addFavoriteBookApi,
  getStudentBookDetailsApi,
  removeFavoriteBookApi,
} from '../../../services/booksService';
import { useNavigate, useParams } from 'react-router-dom';
import { Table, TableAlignType } from '../../atoms/Table';
import { ReportBookCardListTile } from '../../atoms/ReportBookCardListTile';
import { monthDayFormat } from '../../../utils/DateFormat';
import { PaginationButton } from '../../atoms/PagenationButton';
import { ReportBookCard } from '../../atoms/ReportBookCard';

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
];

const StudentReportList = () => {
  const { id } = useParams();
  const navigate = useNavigate();

  const [bookDetail, setBookDetail] = useState<StudentBook | null>(null);
  const [bookReports, setBookReports] = useState<BookReports>();
  const [currentPage, setCurrentPage] = useState(0);
  const [totalPages, setTotalPages] = useState(1);
  const [favorite, setFavorite] = useState(false);

  const fetchBookDetail = async (page = 0) => {
    try {
      const response = await getStudentBookDetailsApi(Number(id), page);
      setBookDetail(response);
      setBookReports(response.bookReports);
      setTotalPages(response.bookReports.totalPages);
      setFavorite(response.bookStatus.favorite);
      setCurrentPage(page);
    } catch (error) {
      console.error('Error fetching book detail:', error);
    }
  };

  const favoriteBook = async () => {
    await addFavoriteBookApi(Number(id));
    fetchBookDetail();
  };
  const favoriteCancel = async () => {
    await removeFavoriteBookApi(Number(id));
    fetchBookDetail();
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
      <div className="flex flex-col justify-center mb-8">
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
                `/student/report/write/${id}?title=${bookDetail?.title}&author=${bookDetail?.author}&publisher=${bookDetail?.publisher}`
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
            bookReports?.content.map((report, index) => {
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

export default StudentReportList;
