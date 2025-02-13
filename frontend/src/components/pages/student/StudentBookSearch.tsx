import SelectVariants from '../../commons/SelectVariants';

const StudentBookSearch = () => {
  const data = [
    {
      no: 1,
      title: '어린왕자 (Le Petit Prince)어린왕자 (Le Petit Prince)',
      author: '앙투안 드 생텍쥐페리',
      publisher: '새움',
      genre: '문학',
      year: 2022,
      image: '/assets/images/little-prince.jpg', // 이미지 경로
    },
    {
      no: 2,
      title: '어린왕자 (Le Petit Prince)',
      author: '앙투안 드 생텍쥐페리',
      publisher: '새움',
      genre: '문학',
      year: 2022,
      image: '/assets/images/little-prince.jpg', // 이미지 경로
    },
    {
      no: 3,
      title: '어린왕자 (Le Petit Prince)',
      author: '앙투안 드 생텍쥐페리',
      publisher: '새움',
      genre: '문학',
      year: 2022,
      image: '/assets/images/little-prince.jpg', // 이미지 경로
    },
  ];

  return (
    <div className="w-full m-auto mt-8">
      {/* 검색 영역 */}
      <div className="flex items-center justify-center mb-6">
        <div className="flex items-center space-x-4">
          <SelectVariants />
          <div
            className={`flex items-center w-80 gap-2 h-[46px] overflow-hidden`}
          >
            <input
              className={`w-full h-full pl-5 outline-none placeholder-gray-500 body-medium`}
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

      <div className="border border-gray-200 mb-6"></div>

      <div className="w-[1100px] m-auto">
        <h2 className="body-medium text-left ml-36 text-gray-900 mb-4">
          총 {data.length}권
        </h2>

        {/* 책 리스트 */}
        <div className="justify-items-center">
          {data.map((book, index) => (
            <div
              key={index}
              className={`flex w-[850px] h-[240px] mb-4 border-b border-gray-300`}
            >
              <img
                src="/assets/images/bookImage.png"
                alt={book.title}
                className="w-[180px] h-[230px] object-cover"
              />
              <div className="mt-12 w-full">
                <h3 className="mb-[10px] text-gray-800 headline-medium">
                  {book.title}
                </h3>
                <p className="body-medium text-gray-600">저자: {book.author}</p>
                <p className="body-medium text-gray-600">
                  출판사: {book.publisher}
                </p>
                <p className="body-medium text-gray-600">장르: {book.genre}</p>
                <p className="body-medium text-gray-600">
                  출판년도: {book.year}
                </p>
              </div>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
};

export default StudentBookSearch;
