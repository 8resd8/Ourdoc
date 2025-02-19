import { useEffect, useState } from 'react';
import SelectVariants from '../../commons/SelectVariants';
import { useNavigate } from 'react-router-dom';
import {
  BookCategoryExtension,
  BookCategoryType,
} from '../../../styles/bookCategoryType';
import {
  Book,
  BookCategoryParams,
  getClassTeacherRecommendedBooksApi,
  getFavoriteBooksApi,
  getTeacherHomeworkBooksApi,
  getTeacherRecommendedBooksApi,
  removeFavoriteBookApi,
  removeHomeworkBookApi,
  removeTeacherRecommendedBookApi,
} from '../../../services/booksService';
import { bookCategoryState } from '../../../recoil/atoms/bookCategoryAtom';
import { useRecoilState } from 'recoil';
import { PaginationButton } from '../../atoms/PagenationButton';
import SvgColor from '../../atoms/SvgColor';
import { Table, TableAlignType } from '../../atoms/Table';
import { TeacherBookCategoryListTile } from '../../atoms/TeacherBookCategoryListTile';
import { notify } from '../../commons/Toast';

const PAGE_SIZE = 10;
const TABLE_HEADER = [
  {
    label: 'No',
    width: 60,
  },
  {
    label: '도서명',
    width: 240,
    align: TableAlignType.left,
  },
  {
    label: '지은이',
  },
  {
    label: '출판사',
  },
  {
    label: '출판 년도',
  },
  {
    label: '제출 현황',
  },
  {
    label: '삭제',
  },
];

const NON_SUMBIT_COUNT_TABLE_HEADER = [
  {
    label: 'No',
    width: 60,
  },
  {
    label: '도서명',
    width: 240,
    align: TableAlignType.left,
  },
  {
    label: '지은이',
  },
  {
    label: '출판사',
  },
  {
    label: '출판 년도',
  },
  {
    label: '삭제',
  },
];

const NON_DELETE_TABLE_HEADER = [
  {
    label: 'No',
    width: 60,
  },
  {
    label: '도서명',
    width: 240,
    align: TableAlignType.left,
  },
  {
    label: '지은이',
  },
  {
    label: '출판사',
  },
  {
    label: '출판 년도',
  },
  {
    label: '제출 현황',
  },
];

interface BookProps {
  book: Book;
  submitCount: number;
  homeworkId?: number;
}

const TeacherBookCategory = () => {
  const navigate = useNavigate();

  const [currentPage, setCurrentPage] = useState(0);
  const [totalPages, setTotalPages] = useState(1);
  const [books, setBooks] = useState<BookProps[]>([]);

  const [searchTerm, setSearchTerm] = useState('');
  const [searchCategory, setSearchCategory] = useState('도서명');
  const [tableHeader, settableHeader] = useState(TABLE_HEADER);

  const [selectedCategory, setSelectedCategory] =
    useRecoilState(bookCategoryState);

  const fetchBooks = async (page = 0) => {
    const params: BookCategoryParams = {
      page: page,
      size: PAGE_SIZE,
    };
    try {
      let response;
      let bookList: BookProps[] = [];
      let totalPage: number;
      let responseList: BookProps[] = [];

      switch (selectedCategory) {
        case BookCategoryType.HomeWork:
          response = await getTeacherHomeworkBooksApi(params);

          responseList = [];
          response.homeworks.content.forEach((element) => {
            responseList.push({
              book: element.book,
              submitCount: element.homeworkSubmitCount,
              homeworkId: element.homeworkId,
            });
          });

          bookList = responseList;

          totalPage = response.homeworks.totalPages;
          break;
        case BookCategoryType.Class:
          responseList = [];
          response = await getClassTeacherRecommendedBooksApi(params);
          response.recommends.content.forEach((element) => {
            responseList.push({
              book: element.book,
              submitCount: element.submitCount,
            });
          });

          bookList = responseList;
          totalPage = response.recommends.totalPages;
          break;
        case BookCategoryType.Favorite:
          responseList = [];
          response = await getFavoriteBooksApi(params);
          response.content.forEach((element) => {
            responseList.push({
              book: element.book,
              submitCount: 0,
            });
          });

          bookList = responseList;

          totalPage = response.totalPages;
          break;
        case BookCategoryType.Grade:
          responseList = [];
          response = await getTeacherRecommendedBooksApi(params);
          response.recommends.content.forEach((element) => {
            responseList.push({
              book: element.book,
              submitCount: element.submitCount,
            });
          });

          bookList = responseList;
          totalPage = response.recommends.totalPages;
          break;
      }

      if (response) {
        setBooks(bookList);
        setTotalPages(totalPage);
        setCurrentPage(page);
      }
    } catch (error) {
      console.error('도서 목록 가져오기 실패:', error);
    }
  };

  const onPageChange = (pageNumber: number) => {
    if (pageNumber >= 0 && pageNumber < totalPages) {
      fetchBooks(pageNumber);
    }
  };

  const handleSearch = () => {
    navigate(
      `/teacher/book/search/?searchCategory=${searchCategory}&searchTerm=${searchTerm}`
    );
  };

  const handleDeleteClick = async (item: Book) => {
    try {
      switch (selectedCategory) {
        case BookCategoryType.HomeWork:
          await removeHomeworkBookApi(item.bookId);
          break;
        case BookCategoryType.Class:
          await removeTeacherRecommendedBookApi(item.bookId);
          break;
        case BookCategoryType.Favorite:
          await removeFavoriteBookApi(item.bookId);
          break;

        default:
          break;
      }

      notify({ type: 'success', text: '삭제가 완료되었습니다.' });
    } catch (error) {
      notify({ type: 'error', text: '삭제 중 오류가 발생했습니다.' });
    } finally {
      fetchBooks(0);
    }
  };

  useEffect(() => {
    fetchBooks(0);

    switch (selectedCategory) {
      case BookCategoryType.Favorite:
        settableHeader(NON_SUMBIT_COUNT_TABLE_HEADER);
        break;
      case BookCategoryType.Grade:
        settableHeader(NON_DELETE_TABLE_HEADER);
        break;

      default:
        settableHeader(TABLE_HEADER);
        break;
    }
  }, [selectedCategory]);

  useEffect(() => {
    setSelectedCategory(BookCategoryType.HomeWork);
  }, []);

  return (
    <div className="flex flex-col items-center pb-10">
      <div className="w-[1064px] m-auto mt-8">
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
                className="absolute right-2 top-1/2 transform -translate-y-1/2"
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

        {/* 숙제 도서 제목 */}
        <h2 className="headline-large text-center mb-[36px] mt-[44px]">
          {BookCategoryExtension[selectedCategory as BookCategoryType]?.korean}{' '}
          도서
        </h2>
        {/* 카테고리 버튼 */}
        <div className="flex justify-center space-x-8 mb-[36px]">
          {Object.values(BookCategoryType).map((type) => {
            const isSelected = selectedCategory === type;

            return (
              <button
                key={type}
                className={`flex flex-col items-center px-4 py-1 bg-gray-0 rounded-[15px] shadow-xxsmall cursor-pointer transition-colors
                ${isSelected ? 'bg-white text-primary-500' : 'bg-gray-0 text-gray-700'}
                ${!isSelected ? 'hover:border-primary-400 group' : ''}
              `}
                onClick={() => setSelectedCategory(type)}
              >
                <SvgColor
                  sx={{ width: 48, height: 48 }}
                  src={BookCategoryExtension[type as BookCategoryType]?.imgSrc}
                  className={`transition-colors 
                  ${isSelected ? 'bg-primary-500' : 'bg-gray-700'} 
                  ${!isSelected ? 'group-hover:text-primary-400' : ''}
                `}
                />
                <span
                  className={`transition-colors caption-medium
                  ${isSelected ? 'text-primary-500' : 'text-gray-700'} ${!isSelected ? 'group-hover:text-primary-400' : ''}`}
                >
                  {BookCategoryExtension[type as BookCategoryType]?.korean}
                </span>
              </button>
            );
          })}
        </div>
      </div>

      <div className="w-full border-b border-gray-200 mb-9" />

      <div>
        <Table
          headers={tableHeader}
          datas={books.map((item, index) => {
            return (
              <TeacherBookCategoryListTile
                no={currentPage * 10 + index + 1}
                title={item.book.title}
                author={item.book.author}
                publisher={item.book.publisher}
                publishYear={item.book.publishYear}
                readerCount={item.submitCount}
                onClick={() => {
                  navigate(
                    selectedCategory == BookCategoryType.HomeWork
                      ? `/teacher/homework/list/?homeworkId=${item.homeworkId}`
                      : `/teacher/book/report/list/${item.book.bookId}`
                  );
                }}
                deleteClick={() => {
                  handleDeleteClick(item.book);
                }}
                showCount={selectedCategory != BookCategoryType.Favorite}
                showDelete={selectedCategory != BookCategoryType.Grade}
              />
            );
          })}
        />
      </div>
      <PaginationButton
        currentPage={currentPage}
        totalPages={totalPages}
        onPageChange={onPageChange}
      />
    </div>
  );
};

export default TeacherBookCategory;
