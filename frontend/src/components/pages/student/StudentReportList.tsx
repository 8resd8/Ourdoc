import { useState, useEffect } from 'react';
import {
  BookReport,
  BookReports,
  StudentBook,
  addFavoriteBookApi,
  getStudentBookDetailsApi,
  removeFavoriteBookApi,
} from '../../../services/booksService';
import { useLocation, useNavigate, useParams } from 'react-router-dom';

const StudentReportList = () => {
  const { id } = useParams();

  const [bookDetail, setBookDetail] = useState<StudentBook | null>(null);
  const [bookReports, setBookReports] = useState<BookReports>();
  const [currentPage, setCurrentPage] = useState(0);
  const [totalPages, setTotalPages] = useState(1);
  const [favorite, setFavorite] = useState(false);
  const [recommend, setRecommend] = useState(false);
  const [homework, setHomework] = useState(false);
  const [submitHomework, setSubmitHomework] = useState(false);
  const PAGE_SIZE = 10;
  const fetchBookDetail = async (page = 0) => {
    try {
      const response = await getStudentBookDetailsApi(Number(id), page);
      setBookDetail(response);
      setBookReports(response.bookReports);
      setTotalPages(response.bookReports.totalPages);
      setFavorite(response.bookStatus.favorite);
      setCurrentPage(page);
      console.log(response);
    } catch (error) {
      console.error('Error fetching homework detail:', error);
    }
  };
  console.log(bookReports);

  useEffect(() => {
    fetchBookDetail();
  }, [id]);
  const handlePageChange = (page: number) => {
    if (page >= 0 && page < totalPages) {
      fetchBookDetail(page);
    }
  };

  const formatDate = (dateString: string) => {
    const date = new Date(dateString);
    return `${date.getMonth() + 1}월 ${date.getDate()}일`;
  };
  const navigate = useNavigate();

  const favoriteBook = async () => {
    const response = await addFavoriteBookApi(Number(id));
    console.log(response);
    fetchBookDetail();
  };
  const favoriteCancel = async () => {
    if (id) {
      const response = await removeFavoriteBookApi(Number(id));
      console.log(response);
      fetchBookDetail();
    }
    fetchBookDetail();
  };

  return (
    <div className="w-[1200px] mx-auto mt-8 justify-items-center">
      {/* 숙제 도서 카드 */}
      <div className="flex flex-col justify-center mb-8">
        <div className="flex bg-gray-0 shadow-xxsmall rounded-lg p-6 w-[630px] h-[340px]">
          <img
            src={bookDetail?.imageUrl}
            alt={bookDetail?.title}
            className="w-[190px] h-[282px] object-cover rounded-md mr-6"
          />
          <div className="mt-12">
            <h2 className="headline-medium font-bold mb-4">
              {bookDetail?.title}
            </h2>
            <p className="body-medium text-gray-700 mb-4">
              저자: {bookDetail?.author}
            </p>
            <p className="body-medium text-gray-700 mb-4">
              출판사: {bookDetail?.publisher}
            </p>
            <p className="body-medium text-gray-700 mb-4">
              장르: {bookDetail?.genre}
            </p>
            <p className="body-medium text-gray-700">
              출판년도: {bookDetail?.publishYear}
            </p>
          </div>
        </div>
        {/* 버튼 섹션 */}
        <div className="flex space-x-4 mb-4 justify-between mt-4 w-[630px]">
          {favorite ? (
            <button
              onClick={favoriteCancel}
              className="cursor-pointer body-medium px-4 py-2 text-gray-500 rounded-[10px]  border border-gray-500"
            >
              관심 해제
            </button>
          ) : (
            <button
              onClick={favoriteBook}
              className="cursor-pointer body-medium px-4 py-2 text-secondary-500 rounded-[10px]  border border-secondary-500"
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
            className="cursor-pointer body-medium px-4 py-2 text-gray-0 rounded-[10px] bg-primary-500 "
          >
            독서록 작성
          </button>
        </div>
      </div>

      {/* 제출한 독서록 리스트 테이블 */}
      <div className="w-full flex flex-col items-center">
        {/* 테이블 */}
        <table className="w-[868px] mb-6 table-fixed">
          <thead>
            <tr className="">
              <th className="px-4 py-2 border-b border-gray-200">No</th>
              <th className="px-4 py-2 border-b border-gray-200">내용</th>
              <th className="px-4 py-2 border-b border-gray-200">제출 날짜</th>
              <th className="px-4 py-2 border-b border-gray-200">도장 여부</th>
            </tr>
          </thead>
          <tbody>
            {bookReports &&
              bookReports.content.map((row: any, index: any) => (
                <tr key={index} className={`${index % 2 === 0 ? '' : ''}`}>
                  <td className="px-4 py-2 text-center">
                    {index + currentPage * 10 + 1}
                  </td>
                  <td
                    onClick={() => {
                      navigate(`/student/report/detail/${row.id}`);
                    }}
                    className="px-4 py-2 truncate cursor-pointer"
                  >
                    {row.beforeContent}
                  </td>
                  <td className="px-4 py-2 text-center ">
                    {formatDate(row.createdAt)}
                  </td>
                  <td className="px-4 py-2 text-center">
                    {row.bookReportApproveStatus === '있음' ? (
                      <button className="body-small text-system-success border-system-success border w-[49px] h-[28px] rounded-[5px]">
                        완료
                      </button>
                    ) : (
                      <button className="body-small border border-system-danger text-system-danger w-[49px] h-[28px] rounded-[5px]">
                        미완료
                      </button>
                    )}
                  </td>
                </tr>
              ))}
          </tbody>
        </table>
        {/* 페이지네이션 */}
        <div className="flex justify-center items-center mt-8 space-x-2">
          {/* 이전 페이지 버튼 */}
          <button
            className={`px-4 py-2 rounded-lg cursor-pointer ${
              currentPage === 0
                ? 'text-gray-400 cursor-not-allowed'
                : 'hover:bg-gray-100'
            }`}
            onClick={() => handlePageChange(currentPage - 1)}
            disabled={currentPage === 0}
          >
            &lt;
          </button>

          {/* 페이지 번호 표시 */}
          {Array.from({ length: totalPages }, (_, index) => (
            <button
              key={index}
              className={`px-4 py-2 body-small rounded-lg cursor-pointer ${
                currentPage === index
                  ? 'bg-primary-500 text-white'
                  : 'hover:bg-gray-100 text-gray-500'
              }`}
              onClick={() => handlePageChange(index)}
            >
              {index + 1}
            </button>
          ))}

          {/* 다음 페이지 버튼 */}
          <button
            className={`px-4 py-2 rounded-lg cursor-pointer ${
              currentPage === totalPages - 1
                ? 'text-gray-400 cursor-not-allowed'
                : 'hover:bg-gray-100'
            }`}
            onClick={() => handlePageChange(currentPage + 1)}
            disabled={currentPage === totalPages - 1}
          >
            &gt;
          </button>
        </div>
      </div>
    </div>
  );
};

export default StudentReportList;
