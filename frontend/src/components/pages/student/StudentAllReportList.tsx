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
          <div className="w-[185px] h-[232px] left-0 top-0 absolute bg-white rounded-tr-[10px] rounded-br-[10px] shadow-[0px_2px_14px_2px_rgba(33,33,33,0.03)] border border-gray-200" />
          <img
            className="w-[181px] h-[232px] left-0 top-0 absolute rounded-tr-[10px] rounded-br-[10px] shadow-[0px_2px_14px_2px_rgba(33,33,33,0.03)] border border-gray-200"
            src={book.image}
            alt={book.title}
          />
          {book.isHomework && (
            <div className="w-[181px] h-11 px-[46px] py-1 left-0 top-[188px] absolute bg-[#ff6f61]/70 rounded-br-[10px] justify-center items-center gap-2.5 inline-flex">
              <div className="text-white text-base font-normal font-['Pretendard'] leading-normal">
                숙제 도서
              </div>
            </div>
          )}
        </div>
        <div className="self-stretch h-6 justify-start items-center gap-5 inline-flex">
          <div className="grow shrink basis-0 self-stretch text-gray-800 text-lg font-semibold font-['Pretendard'] leading-normal">
            {book.title}
          </div>
        </div>
        <div className="self-stretch h-12 text-gray-300 text-sm font-normal font-['Pretendard'] leading-tight">
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
          <div className="text-center text-gray-800 text-[26px] font-semibold font-['Pretendard'] leading-[30px] mt-8">
            4학년
          </div>
          <div className="flex justify-center items-center gap-8 mt-[40px] w-full">
            {[1, 2, 3, 4].map((grade) => (
              <div
                key={grade}
                className="w-[72px] h-[72px] px-3 bg-white rounded-[15px] shadow-[0px_2px_14px_2px_rgba(33,33,33,0.03)] flex justify-center items-center"
              >
                <div className="w-12 flex-col justify-center items-center inline-flex">
                  <div
                    className={`text-center ${grade === 4 ? 'text-[#ff6f61]' : 'text-gray-700'} text-[26px] font-semibold font-['Pretendard'] leading-[30px]`}
                  >
                    {grade}
                  </div>
                  <div
                    className={`text-center ${grade === 4 ? 'text-[#ff6f61]' : 'text-gray-700'} text-xs font-normal font-['Pretendard'] leading-none`}
                  >
                    학년
                  </div>
                </div>
              </div>
            ))}
          </div>
          <div className="w-[1064px] text-right text-gray-700 text-sm font-normal font-['Pretendard'] leading-tight mb-2 mt-8 m-auto">
            14권 읽음
          </div>
        </div>
        <div className="w-[1064px] m-auto">
          <div className="text-gray-800 mt-10 text-[22px] font-semibold font-['Pretendard'] leading-relaxed">
            1월
          </div>
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
