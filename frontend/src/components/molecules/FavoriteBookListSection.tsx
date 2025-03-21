import { useEffect, useState } from 'react';
import { BookBriefTile } from '../atoms/BookBriefTile';
import {
  BookCategoryBookProps,
  BookCategoryParams,
  getFavoriteBooksApi,
} from '../../services/booksService';

const PARAMS: BookCategoryParams = {
  page: 0,
  size: 5,
};

export const FavoriteBookListSection = () => {
  const [favoriteBooks, setFavoriteBooks] = useState<BookCategoryBookProps>();

  useEffect(() => {
    const fetchFavoriteBooks = async () => {
      const favoriteBooks = await getFavoriteBooksApi(PARAMS);

      setFavoriteBooks(favoriteBooks);
    };
    fetchFavoriteBooks();
  }, []);

  return (
    <div className="w-[1064px] h-[406px] p-6 bg-gray-0 rounded-[15px] shadow-xsmall flex-col justify-start items-start gap-3 inline-flex">
      <div className="text-gray-800 headline-medium">관심 도서</div>
      <div className="self-stretch grid grid-cols-5 items-start">
        {favoriteBooks &&
          favoriteBooks.content.map((book, index) => (
            <BookBriefTile
              key={index}
              book={book.book}
              isHomework={false}
              isStudent={true}
              searchBookId={book.book.bookId}
            />
          ))}
      </div>
    </div>
  );
};
