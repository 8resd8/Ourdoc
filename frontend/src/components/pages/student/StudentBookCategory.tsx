import { Divider, Grid2, Stack } from '@mui/material';
import dayjs from 'dayjs';
import { useEffect, useState } from 'react';
import { useRecoilState } from 'recoil';
import { bookCategoryState } from '../../../recoil/atoms/bookCategoryAtom';
import {
  getStudentHomeworkBooksApi,
  HomeworkItem,
  PaginatedHomeworks,
} from '../../../services/booksService';
import {
  BookCategoryExtension,
  BookCategoryType,
} from '../../../styles/bookCategoryType';
import { BookBriefTile } from '../../atoms/BookBriefTile';
import { FavoriteBookButton } from '../../atoms/FavoriteBookButton';
import { HomeWorkButton } from '../../atoms/HomeWorkButton';
import SvgColor from '../../atoms/SvgColor';
import SelectVariants from '../../commons/SelectVariants';
import { book } from '../teacher/TeacherMain';
import { useNavigate } from 'react-router-dom';

const homeWorkBook = (id: number): HomeworkItem => {
  return {
    homeworkId: id,
    book: { ...book, description: '' },
    createdAt: dayjs(new Date()).toString(),
    submitStatus: id % 2 === 0,
    bookReports: [''],
  };
};

export const mockHomeWorkBooks: HomeworkItem[] = Array.from(
  { length: 12 },
  (_, index) => homeWorkBook(index)
);
const mockClasskBooks: HomeworkItem[] = Array.from({ length: 13 }, (_, index) =>
  homeWorkBook(index + 100)
);
const mockFavoriteBooks: HomeworkItem[] = Array.from(
  { length: 5 },
  (_, index) => homeWorkBook(index + 200)
);
const mockGradeBooks: HomeworkItem[] = Array.from({ length: 19 }, (_, index) =>
  homeWorkBook(index + 300)
);

const StudentBookCategory = () => {
  const [selectedCategory, setSelectedCategory] =
    useRecoilState(bookCategoryState);
  const [books, setBooks] = useState<HomeworkItem[]>(mockHomeWorkBooks);

  useEffect(() => {
    switch (selectedCategory) {
      case BookCategoryType.HomeWork:
        setBooks(mockHomeWorkBooks);
        break;
      case BookCategoryType.Class:
        setBooks(mockClasskBooks);
        break;
      case BookCategoryType.Favorite:
        setBooks(mockFavoriteBooks);
        break;
      case BookCategoryType.Grade:
        setBooks(mockGradeBooks);
        break;
    }
  }, [selectedCategory]);

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
    <div>
      <div className="w-[1200px] m-auto mt-8">
        {/* 검색 영역 */}
        <div className="flex items-center justify-center mb-6">
          <div className="flex items-center space-x-4">
            <SelectVariants />
            <div
              className={`flex items-center  w-80  gap-2 h-[46px] overflow-hidden`}
            >
              <input
                className={` w-full h-full pl-5 outline-none placeholder-gray-500 body-medium`}
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

      <Divider
        className="border-gray-200"
        sx={{ width: '100vw', mb: '36px' }}
      />
      <div className="w-[1200px] m-auto mb-[100px]">
        <Grid2 container spacing={'40px'} rowSpacing={'34.75px'}>
          {books.map((item, index) => (
            <Grid2
              key={index}
              size={{ xs: 12 / 5 }}
              display="flex"
              justifyContent="center"
            >
              <Stack>
                <BookBriefTile book={item.book} />
                {selectedCategory === BookCategoryType.HomeWork ? (
                  <HomeWorkButton
                    id={item.homeworkId}
                    isSubmitted={item.submitStatus}
                  />
                ) : selectedCategory === BookCategoryType.Favorite ? (
                  <FavoriteBookButton id={item.homeworkId} />
                ) : (
                  <></>
                )}
              </Stack>
            </Grid2>
          ))}
        </Grid2>
      </div>
    </div>
  );
};

export default StudentBookCategory;
