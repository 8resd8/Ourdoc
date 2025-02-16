import { useState, useEffect } from 'react';
import {
  BookReport,
  HomeworkBook,
  addFavoriteBookApi,
  getStudentHomeworkBookDetailApi,
} from '../../../services/booksService';
import { useLocation, useNavigate } from 'react-router-dom';

const StudentHomeWorkReportList = () => {
  const location = useLocation();
  const queryParams = new URLSearchParams(location.search);
  const homeworkId = queryParams.get('homeworkId');

  const [homeworkDetail, setHomeworkBook] = useState<HomeworkBook | null>(null);
  const [bookReports, setBookReports] = useState<BookReport[]>([]);
  const [currentPage, setCurrentPage] = useState(0);
  const [totalPages, setTotalPages] = useState(1);
  const PAGE_SIZE = 10;
  const fetchHomeworkDetail = async (page = 0) => {
    try {
      const data = await getStudentHomeworkBookDetailApi(Number(homeworkId));
      setHomeworkBook(data.book);
      setBookReports(data.bookReports.content);
      setTotalPages(data.bookReports.totalPages); // totalPages를 API 응답에 맞게 수정
      setCurrentPage(page);
    } catch (error) {
      console.error('Error fetching homework detail:', error);
    }
  };
  useEffect(() => {
    fetchHomeworkDetail();
  }, [homeworkId]);
  const handlePageChange = (page: number) => {
    if (page >= 0 && page < totalPages) {
      fetchHomeworkDetail(page);
    }
  };

  const formatDate = (dateString: string) => {
    const date = new Date(dateString);
    return `${date.getMonth() + 1}월 ${date.getDate()}일`;
  };
  const navigate = useNavigate();

  const favoriteBook = async () => {
    if (homeworkDetail) {
      const response = await addFavoriteBookApi(homeworkDetail.bookId);
      console.log(response);
    }
  };

  return (
    <div className="w-[1200px] mx-auto mt-8 justify-items-center">
      {/* 숙제 도서 카드 */}
      <div className="flex flex-col justify-center mb-8">
        <div className="flex bg-gray-0 shadow-xxsmall rounded-lg p-6 w-[630px] h-[340px]">
          <img
            src={homeworkDetail?.imageUrl}
            alt={homeworkDetail?.title}
            className="w-[190px] h-[282px] object-cover rounded-md mr-6"
          />
          <div className="">
            <h2 className="headline-medium font-bold mb-4">
              {homeworkDetail?.title}
            </h2>
            <p className="body-medium text-gray-700 mb-2">
              저자: {homeworkDetail?.author}
            </p>
            <p className="body-medium text-gray-700 mb-2">
              출판사: {homeworkDetail?.publisher}
            </p>
            <p className="body-medium text-gray-700 mb-2">
              장르: {homeworkDetail?.genre}
            </p>
            <p className="body-medium text-gray-700 mb-2">
              출판년도: {homeworkDetail?.publishYear}
            </p>
          </div>
        </div>
        {/* 버튼 섹션 */}
        <div className="flex justify-between space-x-4 mb-4  mt-4 w-[630px]">
          <button
            onClick={favoriteBook}
            className="cursor-pointer body-medium px-4 py-2 text-secondary-500 rounded-[10px]  border border-secondary-500"
          >
            관심 등록
          </button>
          <button
            onClick={() => {
              navigate(
                `/student/report/write/${homeworkDetail?.bookId}?homeworkId=${homeworkId}&title=${homeworkDetail?.title}&author=${homeworkDetail?.author}&publisher=${homeworkDetail?.publisher}`
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
              <th className="px-4 py-2 border-b border-gray-200">숙제 제출</th>
            </tr>
          </thead>
          <tbody>
            {bookReports.map((row, index) => (
              <tr key={index} className={`${index % 2 === 0 ? '' : ''}`}>
                <td className="px-4 py-2 text-center">{index + 1}</td>
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
                  {row.submitStatus === 'Y' ? (
                    <button className="body-small text-system-success border-system-success border w-[49px] h-[28px] rounded-[5px]">
                      완료
                    </button>
                  ) : (
                    <button className="body-small border border-system-danger text-system-danger w-[49px] h-[28px] rounded-[5px]">
                      미완료
                    </button>
                  )}
                </td>
                <td className="px-4 py-2 text-center">
                  {row.submitStatus === 'Y' ? (
                    <button className="body-small text-system-info border-system-info border w-[49px] h-[28px] rounded-[5px]">
                      제출
                    </button>
                  ) : (
                    <button className="body-small border border-gra-300 text-gray-300 w-[49px] h-[28px] rounded-[5px]">
                      미제출
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

export default StudentHomeWorkReportList;
