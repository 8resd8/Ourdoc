import { useEffect, useState } from 'react';
import {
  addFavoriteBookApi,
  addHomeworkBookApi,
  addTeacherRecommendedBookApi,
  Book,
  getBooksApi,
  removeFavoriteBookApi,
  removeHomeworkBookApi,
  removeTeacherRecommendedBookApi,
} from '../../../services/booksService';
import SelectVariants from '../../commons/SelectVariants';
import classes from './TeacherBookCategory.module.css';
import { useLocation } from 'react-router-dom';
import { PaginationButton } from '../../atoms/PagenationButton';
import { AddDivider } from '../../../utils/AddDivder';

const TeacherBookSearch = () => {
  const location = useLocation();
  const queryParams = new URLSearchParams(location.search);
  const searchCategoryParam = queryParams.get('searchCategory');
  const searchTermParam = queryParams.get('searchTerm');

  const [book, setBook] = useState<Book[]>([]);
  const [searchTerm, setSearchTerm] = useState(searchTermParam || '');
  const [searchCategory, setSearchCategory] = useState(
    searchCategoryParam || '도서명'
  );
  const [currentPage, setCurrentPage] = useState(0);
  const [totalPages, setTotalPages] = useState(1);
  const [totalElements, setTotalElements] = useState(0);

  const fetchBook = async (page = 0) => {
    const params = {
      size: 10,
      page: page,
      title: searchCategory === '도서명' ? searchTerm : '',
      author: searchCategory === '저자' ? searchTerm : '',
      publisher: searchCategory === '출판사' ? searchTerm : '',
    };
    const response = await getBooksApi(params);
    console.log(response);

    setBook(response.book.content);
    setTotalPages(response.book.totalPages);
    setTotalElements(response.book.totalElements);
    setCurrentPage(page);
  };
  console.log(currentPage);

  const onPageChange = (pageNumber: number) => {
    if (pageNumber >= 0 && pageNumber < totalPages) {
      fetchBook(pageNumber);
    }
  };

  useEffect(() => {
    fetchBook();
  }, [searchCategory]);

  const handleSearch = () => {
    fetchBook();
  };

  const addClassHomeworkBook = async (bookId: number) => {
    const response = await addHomeworkBookApi(bookId);
  };
  const addClassRecommendBook = async (bookId: number) => {
    const response = await addTeacherRecommendedBookApi(bookId);
  };
  const addFavoriteBook = async (bookId: number) => {
    const response = await addFavoriteBookApi(bookId);
  };

  const removeClassHomeworkBook = async (bookId: number) => {
    const response = await removeHomeworkBookApi(bookId);
  };
  const removeClassRecommendBook = async (bookId: number) => {
    const response = await removeTeacherRecommendedBookApi(bookId);
  };
  const removeFavoriteBook = async (bookId: number) => {
    const response = await removeFavoriteBookApi(bookId);
  };

  return (
    <div className="w-full m-auto mt-8">
      {/* 검색 영역 */}
      <div className="flex items-center justify-center mb-6">
        <div className="flex items-center space-x-4">
          <SelectVariants onCategoryChange={setSearchCategory} />
          <div
            className={`relative flex items-center w-80 h-[47px] overflow-hidden`}
          >
            <input
              className={`w-full h-full pl-5 pr-10 border-b border-gray-500 outline-none placeholder-gray-500 body-medium`}
              placeholder="검색어를 입력해주세요."
              type="text"
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
              onKeyDown={(e) => {
                if (e.key === 'Enter') {
                  handleSearch();
                }
              }}
            />
            <button
              onClick={handleSearch}
              className="absolute right-2 top-1/2 transform -translate-y-1/2 cursor-pointer"
            >
              <svg
                fill="#6B7280"
                viewBox="0 0 30 30"
                height="22"
                width="22"
                xmlns="http://www.w3.org/2000/svg"
              >
                <path d="M 13 3 C 7.4889971 3 3 7.4889971 3 13 C 3 18.511003 7.4889971 23 13 23 C 15.396508 23 17.597385 22.148986 19.322266 20.736328 L 25.292969 26.707031 A 1.0001 1.0001 0 1 0 26.707031 25.292969 L 20.736328 19.322266 C 22.148986 17.597385 23 15.396508 23 13 C 23 7.4889971 18.511003 3 13 3 z M 13 5 C 17.430123 5 21 8.5698774 21 13 C 21 17.430123 17.430123 21 13 21 C 8.5698774 21 5 17.430123 5 13 C 5 8.5698774 8.5698774 5 13 5 z"></path>
              </svg>
            </button>
          </div>
        </div>
      </div>

      <div className="border border-gray-200 mb-6"></div>

      <div className="w-[1100px] m-auto">
        <h2 className="body-medium text-left ml-36 text-gray-900 mb-4">
          총 {totalElements}권
        </h2>

        {/* 책 리스트 */}
        <div className="justify-items-center">
          {AddDivider({
            itemList: book.map((book, index) => (
              <div key={index} className={`flex w-[850px] h-[240px] mt-3`}>
                <img
                  src={book.imageUrl}
                  alt={book.title}
                  className="w-[180px] h-[230px]  object-cover rounded-md mr-6"
                />
                <div className="mt-12 w-full">
                  <div className="flex float-end gap-4">
                    <div className="flex float-end gap-4">
                      <IconButton
                        icon="heart"
                        text="관심"
                        onClick={() =>
                          !book.bookStatus?.favorite
                            ? removeFavoriteBook(book.bookId)
                            : addFavoriteBook(book.bookId)
                        }
                        // removeClick={()=>removeClassHomeworkBook(book.bookId)}
                        isActive={book.bookStatus?.favorite} // 상태에 따라 변경
                      />
                      <IconButton
                        icon="class"
                        text="학급"
                        onClick={() =>
                          !book.bookStatus?.recommend
                            ? addClassRecommendBook(book.bookId)
                            : removeClassRecommendBook(book.bookId)
                        }
                        isActive={book.bookStatus?.recommend} // 상태에 따라 변경
                      />
                      <IconButton
                        icon="homework"
                        text="숙제"
                        onClick={() =>
                          !book.bookStatus?.homework
                            ? addClassHomeworkBook(book.bookId)
                            : removeClassHomeworkBook(book.bookId)
                        }
                        isActive={book.bookStatus?.homework} // 상태에 따라 변경
                      />
                    </div>
                  </div>
                  <h3 className="mb-[10px] text-gray-800 headline-medium">
                    {book.title}
                  </h3>
                  <p className="body-medium text-gray-600">
                    저자: {book.author}
                  </p>
                  <p className="body-medium text-gray-600">
                    출판사: {book.publisher}
                  </p>
                  <p className="body-medium text-gray-600">
                    장르: {book.genre}
                  </p>
                  <p className="body-medium text-gray-600">
                    출판년도: {book.publishYear}
                  </p>
                </div>
              </div>
            )),
          })}
        </div>
        <PaginationButton
          currentPage={currentPage}
          totalPages={totalPages}
          onPageChange={onPageChange}
        />
      </div>
    </div>
  );
};

export default TeacherBookSearch;

export const SVGIcons = {
  heart: (
    <path d="M13.8936 3.07333C13.5531 2.73267 13.1488 2.46243 12.7038 2.27805C12.2588 2.09368 11.7819 1.99878 11.3002 1.99878C10.8186 1.99878 10.3416 2.09368 9.89667 2.27805C9.4517 2.46243 9.04741 2.73267 8.70691 3.07333L8.00024 3.78L7.29357 3.07333C6.60578 2.38553 5.67293 1.99914 4.70024 1.99914C3.72755 1.99914 2.7947 2.38553 2.10691 3.07333C1.41911 3.76112 1.03271 4.69397 1.03271 5.66666C1.03271 6.63935 1.41911 7.5722 2.10691 8.26L2.81358 8.96666L8.00024 14.1533L13.1869 8.96666L13.8936 8.26C14.2342 7.91949 14.5045 7.51521 14.6889 7.07023C14.8732 6.62526 14.9681 6.14832 14.9681 5.66666C14.9681 5.185 14.8732 4.70807 14.6889 4.26309C14.5045 3.81812 14.2342 3.41383 13.8936 3.07333V3.07333Z" />
  ),
  class: (
    <>
      <path d="M1.3335 1.33333V10.6667H14.6668V1.33333H14.3335H1.3335Z" />
      <path d="M13.0002 5.00002H11.0002M3.00016 4.00002L1.3335 5.00002M3.00016 5.00002H4.00016H5.00016M7.00016 1.00002H4.66683L9.00016 1C8.3335 0.999984 9.3335 1.00002 7.00016 1.00002Z" />
    </>
  ),
  homework: (
    <>
      <path d="M7.3335 2.66667H2.66683C2.31321 2.66667 1.97407 2.80715 1.72402 3.0572C1.47397 3.30724 1.3335 3.64638 1.3335 4.00001V13.3333C1.3335 13.687 1.47397 14.0261 1.72402 14.2761C1.97407 14.5262 2.31321 14.6667 2.66683 14.6667H12.0002C12.3538 14.6667 12.6929 14.5262 12.943 14.2761C13.193 14.0261 13.3335 13.687 13.3335 13.3333V8.66667" />
      <path d="M12.3335 1.66667C12.5987 1.40145 12.9584 1.25246 13.3335 1.25246C13.7086 1.25246 14.0683 1.40145 14.3335 1.66667C14.5987 1.93189 14.7477 2.2916 14.7477 2.66667C14.7477 3.04174 14.5987 3.40145 14.3335 3.66667L8.00016 10L5.3335 10.6667L6.00016 8L12.3335 1.66667Z" />
    </>
  ),
};

// 아이콘 버튼 컴포넌트
interface IconButtonProps {
  icon: keyof typeof SVGIcons;
  text: string;
  onClick: () => void;
  isActive: any;
}

export const IconButton = ({
  icon,
  text,
  onClick,
  isActive,
}: IconButtonProps) => (
  <div
    onClick={onClick}
    className={`h-6 pl-2 pr-3 py-1 bg-gray-0 rounded-[15px] border ${
      isActive ? 'border-primary-500' : 'border-gray-800'
    } justify-start items-center gap-1 inline-flex cursor-pointer`}
  >
    <div className="justify-start items-center gap-2 flex">
      <svg
        width="16"
        height="16"
        viewBox="0 0 16 16"
        fill="none"
        xmlns="http://www.w3.org/2000/svg"
        stroke={isActive ? '#FF6F61' : '#2C2C2C'}
        strokeWidth="2"
        strokeLinecap="round"
        strokeLinejoin="round"
      >
        {SVGIcons[icon]}
      </svg>
      <div
        className={`text-center ${isActive ? 'text-primary-500' : 'text-gray-800'} caption-medium`}
      >
        {text}
      </div>
    </div>
  </div>
);
