import { getRecoil } from 'recoil-nexus';
import { currentUserState } from '../../../recoil';
import { useEffect, useState } from 'react';
import { createIndexArray } from '../../../utils/CreateIndexArray';
import { PaginationButton } from '../../atoms/PagenationButton';
import { PageDivider } from '../../atoms/PageDivider';
import {
  getStudentBookReportsApi,
  StudentBookReportListContents,
} from '../../../services/bookReportsService';
import { DateFormat } from '../../../utils/DateFormat';
import { useNavigate } from 'react-router-dom';

const StudentAllReportList = () => {
  const user = getRecoil(currentUserState);
  const [selectedGrade, setSelectedGrade] = useState(user.grade ?? 1);
  const [bookReports, setbookReports] = useState<
    StudentBookReportListContents[]
  >([]);
  const [currentPage, setCurrentPage] = useState(0);
  const [totalPages, setTotalPages] = useState(1);
  const [totalElements, setTotalElements] = useState(0);

  const fetchBooks = async (page = 0) => {
    const params = {
      size: 10,
      page: page,
      classId: user.classId ?? 0,
      grade: selectedGrade,
    };

    const response = await getStudentBookReportsApi(params);

    setbookReports(response.content);
    setTotalPages(response.totalPages);
    setTotalElements(response.totalElements);
    setCurrentPage(page);
  };

  const onPageChange = (pageNumber: number) => {
    if (pageNumber >= 0 && pageNumber < totalPages) {
      fetchBooks(pageNumber);
    }
  };

  useEffect(() => {
    fetchBooks();
  }, [selectedGrade]);

  return (
    <div className="flex flex-col items-center pb-10">
      <div className="flex flex-col justify-center items-center">
        <h2 className="headline-large text-center mb-[36px] mt-[40px]">
          {selectedGrade}학년
        </h2>
        {/* 카테고리 버튼 */}
        <div className="flex justify-center space-x-8 mb-10">
          {Object.values(createIndexArray(user.grade ?? 1)).map((type) => {
            const isSelected = selectedGrade === type;

            return (
              <button
                key={type}
                className={`flex flex-col items-center p-3 bg-gray-0 rounded-[15px] shadow-xxsmall cursor-pointer transition-colors
                ${isSelected ? 'bg-white text-primary-500' : 'bg-gray-0 text-gray-700'}
                ${!isSelected ? 'hover:border-primary-400 group' : ''}
              `}
                onClick={() => setSelectedGrade(type)}
              >
                <span
                  className={`transition-colors headline-large w-12
                ${isSelected ? 'text-primary-500' : 'text-gray-700'} ${!isSelected ? 'group-hover:text-primary-400' : ''}`}
                >
                  {type}
                </span>
                <span
                  className={`transition-colors caption-medium 
                  ${isSelected ? 'text-primary-500' : 'text-gray-700'} ${!isSelected ? 'group-hover:text-primary-400' : ''}`}
                >
                  학년
                </span>
              </button>
            );
          })}
        </div>
      </div>

      <div className="w-[1064px] text-right text-gray-700 body-small mb-2 mt-8 m-auto">
        {totalElements}권 읽음
      </div>
      <PageDivider />
      <div className="w-[1064px] grid grid-cols-5 gap-6">
        {bookReports.map((bookReport, index) => (
          <BookCard key={index} bookReport={bookReport} />
        ))}
      </div>
      <PaginationButton
        currentPage={currentPage}
        totalPages={totalPages}
        onPageChange={onPageChange}
      />
    </div>
  );
};

const BookCard = ({
  bookReport,
}: {
  bookReport: StudentBookReportListContents;
}) => {
  const navigate = useNavigate();

  return (
    <div
      onClick={() => {
        navigate(`/student/report/detail/${bookReport.bookReportId}`);
      }}
      className="w-[185px] flex-col justify-start items-start gap-2 inline-flex group"
    >
      <div className="self-stretch h-80 rounded-[15px] flex-col justify-center items-center gap-2 flex cursor-pointer">
        <div className="w-[185px] h-[232px] relative">
          <div className="w-[185px] h-[232px] left-0 top-0 absolute bg-gray-0 rounded-tr-[10px] rounded-br-[10px] shadow-xxsmall border border-gray-200" />
          <img
            className="w-[181px] h-[232px] left-0 top-0 absolute rounded-tr-[10px] rounded-br-[10px] shadow-xxsmall border border-gray-200"
            src={bookReport.bookImagePath}
            alt={bookReport.bookTitle}
          />
          {bookReport.isHomework && (
            <div className="w-[181px] h-11 px-[46px] py-1 left-0 top-[188px] absolute bg-primary-500/70 rounded-br-[10px] justify-center items-center gap-2.5 inline-flex">
              <div className="text-gray-0 body-medium">숙제 도서</div>
            </div>
          )}
        </div>
        <div className="self-stretch h-6 justify-start items-center gap-5 inline-flex">
          <div className="grow shrink basis-0 self-stretch text-gray-800 headline-small truncate group-hover:text-primary-400">
            {bookReport.bookTitle}
          </div>
        </div>
        <div className="self-stretch h-12 text-gray-300 body-small">
          {DateFormat(bookReport.createdAt, '')} 작성
        </div>
      </div>
    </div>
  );
};

export default StudentAllReportList;
