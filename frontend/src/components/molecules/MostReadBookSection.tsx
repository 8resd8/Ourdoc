import { Book } from '../../services/booksService';
import { BookBriefTile } from '../atoms/BookBriefTile';

interface MostReadBookProps {
  title: string;
  book: Book;
  count: number;
}

export const MostReadBookSection = ({
  title,
  book,
  count,
}: MostReadBookProps) => {
  return (
    <div className="w-[522px] h-[430px] p-6 bg-gray-0 rounded-[15px] shadow-xsmall flex-col justify-start items-start gap-3 inline-flex">
      <div className="text-gray-800 headline-medium">{title}</div>
      <div className="self-stretch px-6 py-3 justify-between items-center inline-flex">
        {book && <BookBriefTile book={book} />}
        <div className="w-[200px] h-[200px] rounded-[120px] border-2 border-primary-500 flex-col justify-center items-center gap-2.5 inline-flex ">
          <div className="">
            <span className="text-primary-500 display-medium">{count}</span>
            <span className="text-primary-500 body-medium">권</span>
          </div>
        </div>
      </div>
    </div>
  );
};
