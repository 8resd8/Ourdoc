const StudentAllReportList = () => {
  const bookData = [
    {
      title: '어린왕자',
      author: '생텍쥐페리',
      publisher: '새옴',
      date: '2024년 11월 24일 작성',
      isHomework: true,
      image: '/assets/images/bookImage.png',
    },
    {
      title: '어린왕자',
      author: '생텍쥐페리',
      publisher: '새옴',
      date: '2024년 11월 24일 작성',
      isHomework: false,
      image: '/assets/images/bookImage.png',
    },
    {
      title: '어린왕자',
      author: '생텍쥐페리',
      publisher: '새옴',
      date: '2024년 11월 24일 작성',
      isHomework: false,
      image: '/assets/images/bookImage.png',
    },
    {
      title: '어린왕자',
      author: '생텍쥐페리',
      publisher: '새옴',
      date: '2024년 11월 24일 작성',
      isHomework: false,
      image: '/assets/images/bookImage.png',
    },
    {
      title: '어린왕자',
      author: '생텍쥐페리',
      publisher: '새옴',
      date: '2024년 11월 24일 작성',
      isHomework: false,
      image: '/assets/images/bookImage.png',
    },
    {
      title: '어린왕자',
      author: '생텍쥐페리',
      publisher: '새옴',
      date: '2024년 11월 24일 작성',
      isHomework: false,
      image: '/assets/images/bookImage.png',
    },
    {
      title: '어린왕자',
      author: '생텍쥐페리',
      publisher: '새옴',
      date: '2024년 11월 24일 작성',
      isHomework: false,
      image: '/assets/images/bookImage.png',
    },
    {
      title: '어린왕자',
      author: '생텍쥐페리',
      publisher: '새옴',
      date: '2024년 11월 24일 작성',
      isHomework: false,
      image: '/assets/images/bookImage.png',
    },
    {
      title: '어린왕자',
      author: '생텍쥐페리',
      publisher: '새옴',
      date: '2024년 11월 24일 작성',
      isHomework: false,
      image: '/assets/images/bookImage.png',
    },
    {
      title: '어린왕자',
      author: '생텍쥐페리',
      publisher: '새옴',
      date: '2024년 11월 24일 작성',
      isHomework: false,
      image: '/assets/images/bookImage.png',
    },
  ];

  interface Book {
    title: string;
    author: string;
    publisher: string;
    date: string;
    isHomework: boolean;
    image: string;
  }

  const BookCard = ({ book }: { book: Book }) => (
    <div className="w-[185px] flex-col justify-start items-start gap-2 inline-flex">
      <div className="self-stretch h-80 rounded-[15px] flex-col justify-center items-center gap-2 flex">
        <div className="w-[185px] h-[232px] relative">
          <div className="w-[185px] h-[232px] left-0 top-0 absolute bg-gray-0 rounded-tr-[10px] rounded-br-[10px] shadow-xxsmall border border-gray-200" />
          <img
            className="w-[181px] h-[232px] left-0 top-0 absolute rounded-tr-[10px] rounded-br-[10px] shadow-xxsmall border border-gray-200"
            src={book.image}
            alt={book.title}
          />
          {book.isHomework && (
            <div className="w-[181px] h-11 px-[46px] py-1 left-0 top-[188px] absolute bg-primary-500/70 rounded-br-[10px] justify-center items-center gap-2.5 inline-flex">
              <div className="text-gray-0 body-medium">숙제 도서</div>
            </div>
          )}
        </div>
        <div className="self-stretch h-6 justify-start items-center gap-5 inline-flex">
          <div className="grow shrink basis-0 self-stretch text-gray-800 headline-small">
            {book.title}
          </div>
        </div>
        <div className="self-stretch h-12 text-gray-300 body-small">
          {book.isHomework
            ? book.date
            : `${book.author} 지음 | ${book.publisher}`}
        </div>
      </div>
    </div>
  );

  return (
    <div className="flex justify-center items-center ">
      <div className="">
        <div className="border-b border-gray-200 w-[calc(100vw_-_20px)]">
          <div className="text-center text-gray-800 headline-large mt-8">
            4학년
          </div>
          <div className="flex justify-center items-center gap-8 mt-[40px] w-full">
            {[1, 2, 3, 4].map((grade) => (
              <div
                key={grade}
                className="w-[72px] h-[72px] px-3 bg-gray-0 rounded-[15px] shadow-xxsmall flex justify-center items-center"
              >
                <div className="w-12 flex-col justify-center items-center inline-flex">
                  <div
                    className={`text-center ${grade === 4 ? 'text-primary-500' : 'text-gray-700'} headline-large`}
                  >
                    {grade}
                  </div>
                  <div
                    className={`text-center ${grade === 4 ? 'text-primary-500' : 'text-gray-700'} caption-medium`}
                  >
                    학년
                  </div>
                </div>
              </div>
            ))}
          </div>
          <div className="w-[1064px] text-right text-gray-700 body-small mb-2 mt-8 m-auto">
            14권 읽음
          </div>
        </div>
        <div className="w-[1064px] m-auto">
          <div className="text-gray-800 mt-10 headline-medium">1월</div>
          <div className="flex justify-center mt-4">
            <div className=" flex flex-wrap justify-between">
              {bookData.map((book, index) => (
                <BookCard key={index} book={book} />
              ))}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default StudentAllReportList;
