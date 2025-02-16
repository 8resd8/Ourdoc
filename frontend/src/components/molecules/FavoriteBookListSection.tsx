import { useEffect, useState } from 'react';
import { Book, getFavoriteBooksApi } from '../../services/booksService';
import { BookBriefTile } from '../atoms/BookBriefTile';

export const FavoriteBookListSection = () => {
  const [book, setBook] = useState<Book[]>([]);
  const fetchFavorite = async () => {
    const response = await getFavoriteBooksApi();
    setBook(response.book.content);
  };

  useEffect(() => {
    fetchFavorite();
  }, []);
  return (
    <div className="w-[1064px] h-[406px] p-6 bg-gray-0 rounded-[15px] shadow-xsmall flex-col justify-start items-start gap-3 inline-flex">
      <div className="text-gray-800 headline-medium">관심 도서</div>
      <div className="self-stretch justify-between items-start inline-flex">
        {book.map((book, index) => (
          <BookBriefTile key={index} book={book} />
        ))}
      </div>
    </div>
  );
};
