import { useNavigate } from 'react-router-dom';
import { Book } from '../../services/booksService';

type BookBriefProps = {
  book: Book;
  isHomework: boolean;
  isStudent: boolean;
  searchBookId: number;
};

export const BookBriefTile = ({
  book,
  isHomework,
  isStudent,
  searchBookId,
}: BookBriefProps) => {
  const navigate = useNavigate();
  let url = '';

  if (isHomework) {
    if (isStudent) {
      url = `/student/homework/list/?homeworkId=${searchBookId}`;
    } else {
      url = `/teacher/homework/list/?homeworkId=${searchBookId}`;
    }
  } else {
    if (isStudent) {
      url = `/student/book/report/list/${searchBookId}`;
    } else {
      url = `/teacher/book/report/list/${searchBookId}`;
    }
  }

  return (
    <div
      onClick={() => navigate(url)}
      className="cursor-pointer w-[185px] flex-col justify-start items-start gap-2 inline-flex"
    >
      <div className="self-stretch h-80 rounded-[15px] flex-col justify-center items-center gap-2 flex">
        <div className="w-[185px] h-[232px] relative">
          <div className="w-[185px] h-[232px] left-0 top-0 absolute bg-gray-0 rounded-tr-[10px] rounded-br-[10px] shadow-xxsmall border border-gray-200"></div>
          <img
            className="w-[181px] h-[232px] left-0 top-0 absolute rounded-tr-[10px] rounded-br-[10px] shadow-xxsmall border border-gray-200 object-fill"
            src={book.imageUrl}
          />
        </div>
        <div className="self-stretch h-6 justify-start items-center gap-5 inline-flex">
          <div className="grow shrink basis-0 self-stretch text-gray-800 headline-small truncate">
            {book.title}
          </div>
        </div>
        <div className="self-stretch h-12 text-gray-300 body-small truncate">
          {book.author} <br />
          지음 | {book.publisher}
        </div>
      </div>
    </div>
  );
};
