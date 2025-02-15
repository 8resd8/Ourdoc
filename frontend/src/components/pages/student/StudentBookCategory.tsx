import { useState, useEffect } from 'react';
import {
  Book,
  HomeworkItem,
  PaginatedHomeworks,
  getStudentHomeworkBooksApi,
} from '../../../services/booksService';
import SelectVariants from '../../commons/SelectVariants';
import { useNavigate } from 'react-router-dom';

const StudentBookCategory = () => {
  const param = {
    page: 0,
    size: 0,
    title: '',
    author: '',
    publisher: '',
  };
  const navigate = useNavigate();
  const [bookList, setBookList] = useState<HomeworkItem[]>([]);
  const [paginationInfo, setPaginationInfo] = useState<Omit<
    PaginatedHomeworks,
    'content'
  > | null>(null);
  const [currentPage, setCurrentPage] = useState(0);
  const [totalPages, setTotalPages] = useState(1);
  const PAGE_SIZE = 10;

  const fetchHomeworkList = async (page = 0) => {
    try {
      const response = await getStudentHomeworkBooksApi(param);
      setBookList(response.homeworks.content);
      console.log(response.homeworks.content);

      const { content, ...paginationData } = response.homeworks;
      console.log('숙제 목록:', response);

      setPaginationInfo(paginationData);
    } catch (error) {
      console.error('숙제 목록 가져오기 실패:', error);
    }
  };
  console.log(bookList);

  const handlePageChange = (page: number) => {
    if (page >= 0 && page < totalPages) {
      fetchHomeworkList(page);
    }
  };
  useEffect(() => {
    fetchHomeworkList(currentPage);
  }, []);

  const formatDate = (dateString: string) => {
    const date = new Date(dateString);
    return `${date.getMonth() + 1}월 ${date.getDate()}일`;
  };
  return (
    <div className="w-[1200px] m-auto mt-8">
      {/* 검색 영역 */}
      <div className="flex items-center justify-center mb-6">
        <div className="flex items-center space-x-4">
          <SelectVariants />
          <div
            className={`flex items-center  w-80  gap-2 h-[46px] overflow-hidden`}
          >
            <input
              className={`border-b border-gray-500 w-full h-full pl-5 outline-none placeholder-gray-500 body-medium`}
              placeholder="검색어를 입력해주세요."
              type="text"
            />
            <svg
              fill="#6B7280"
              viewBox="0 0 30 30"
              height="22"
              width="22"
              xmlns="http://www.w3.org/2000/svg"
            >
              <path d="M 13 3 C 7.4889971 3 3 7.4889971 3 13 C 3 18.511003 7.4889971 23 13 23 C 15.396508 23 17.597385 22.148986 19.322266 20.736328 L 25.292969 26.707031 A 1.0001 1.0001 0 1 0 26.707031 25.292969 L 20.736328 19.322266 C 22.148986 17.597385 23 15.396508 23 13 C 23 7.4889971 18.511003 3 13 3 z M 13 5 C 17.430123 5 21 8.5698774 21 13 C 21 17.430123 17.430123 21 13 21 C 8.5698774 21 5 17.430123 5 13 C 5 8.5698774 8.5698774 5 13 5 z"></path>
            </svg>
          </div>
        </div>
      </div>

      {/* 숙제 도서 제목 */}
      <h2 className="headline-large text-center mb-[36px] mt-[72px]">
        숙제 도서
      </h2>

      {/* 카테고리 버튼 */}
      <div className="flex justify-center space-x-4 mb-6">
        <button className="flex flex-col items-center px-4 py-2 bg-gray-0 rounded-[15px] shadow-xxsmall">
          <img
            width={48}
            height={48}
            src="/assets/images/edit.png"
            className="mb-1"
            alt="숙제 아이콘"
          />
          <span>숙제</span>
        </button>
        <button className="flex flex-col items-center px-4 py-2 bg-gray-0 rounded-[15px] shadow-xxsmall">
          <img
            width={48}
            height={48}
            src="/assets/images/class.png"
            className="mb-1"
            alt="학급 아이콘"
          />
          <span>학급</span>
        </button>
        <button className="flex flex-col items-center px-4 py-2 bg-gray-0 rounded-[15px] shadow-xxsmall">
          <img
            width={48}
            height={48}
            src="/assets/images/heart.png"
            className="mb-1"
            alt="관심 아이콘"
          />
          <span>관심</span>
        </button>
        <button className="flex flex-col items-center px-4 py-2 bg-gray-0 rounded-[15px] shadow-xxsmall">
          <img
            width={48}
            height={48}
            src="/assets/images/school.png"
            className="mb-1"
            alt="학년 아이콘"
          />
          <span>학년</span>
        </button>
      </div>

      <div className="w-[1064px] flex flex-wrap justify-between items-start">
        {bookList.map((book, index) => (
          <div
            key={index}
            className="w-[185px] flex-col justify-start items-start gap-2 inline-flex mb-8"
          >
            <div className="self-stretch h-80 rounded-[15px] flex-col justify-center items-center gap-2 flex">
              <div className="w-[185px] h-[232px] relative">
                <div className="w-[185px] h-[232px] left-0 top-0 absolute bg-gray-0 rounded-tr-[10px] rounded-br-[10px] shadow-xxsmall border border-gray-200"></div>
                <img
                  className="w-[181px] h-[232px] left-0 top-0 absolute rounded-tr-[10px] rounded-br-[10px] shadow-xxsmall border border-gray-200"
                  src={book.book.imageUrl}
                  alt={book.book.title}
                />
              </div>
              <div className="self-stretch h-6 justify-start items-center gap-5 inline-flex">
                <div className="grow shrink basis-0 self-stretch text-gray-800 headline-small">
                  {book.book.title}
                </div>
              </div>
              <div className="self-stretch h-12 text-gray-300 body-small">
                {book.book.author} | {book.book.publisher}
              </div>
            </div>
            <div
              onClick={() => {
                navigate(`/student/report/write/${book.book.bookId}`);
              }}
              className="cursor-pointer self-stretch py-[4px] hover:bg-primary-200 bg-gray-0 rounded-[10px] border border-primary-500 justify-center items-center gap-2.5 inline-flex"
            >
              <button className="text-primary-500 body-medium cursor-pointer ">
                숙제하기
              </button>
            </div>
          </div>
        ))}
      </div>
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
  );
};

export default StudentBookCategory;
