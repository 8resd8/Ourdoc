import { BookBriefTile } from '../atoms/BookBriefTile';
import { book } from '../pages/teacher/TeacherMain';

const mockFavoriteBooks = Array.from({ length: 5 }, (_, index) => book);

export const FavoriteBookListSection = () => {
  return (
    <div className="w-[1064px] h-[406px] p-6 bg-gray-0 rounded-[15px] shadow-xsmall flex-col justify-start items-start gap-3 inline-flex">
      <div className="text-gray-800 headline-medium">관심 도서</div>
      <div className="self-stretch justify-between items-start inline-flex">
        {mockFavoriteBooks.map((book, index) => (
          <BookBriefTile key={index} book={book} />
        ))}
      </div>
    </div>
  );
};
