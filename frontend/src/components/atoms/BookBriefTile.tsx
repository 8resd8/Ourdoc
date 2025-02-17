import { Book } from '../../services/booksService';

type BookBriefProps = {
  book: Book;
};

export const BookBriefTile = ({ book }: BookBriefProps) => {
  return (
    <div className="w-[185px] flex-col justify-start items-start gap-2 inline-flex">
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
