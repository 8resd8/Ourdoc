import SelectVariants from '../../commons/SelectVariants';
import classes from './TeacherBookCategory.module.css';

const TeacherBookSearch = () => {
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
            className={`${classes.input} flex items-center w-80 gap-2 h-[46px] overflow-hidden`}
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
                <div className="flex float-end gap-4">
                  <div className="h-6 pl-2 pr-3 py-1 bg-gray-0 rounded-[15px] border border-gray-800 justify-start items-center gap-1 inline-flex">
                    <div className="justify-start items-center gap-2 flex">
                      <div data-svg-wrapper className="relative">
                        <svg
                          width="16"
                          height="16"
                          viewBox="0 0 16 16"
                          fill="none"
                          xmlns="http://www.w3.org/2000/svg"
                        >
                          <g clipPath="url(#clip0_622_7633)">
                            <path
                              d="M13.8936 3.07333C13.5531 2.73267 13.1488 2.46243 12.7038 2.27805C12.2588 2.09368 11.7819 1.99878 11.3002 1.99878C10.8186 1.99878 10.3416 2.09368 9.89667 2.27805C9.4517 2.46243 9.04741 2.73267 8.70691 3.07333L8.00024 3.78L7.29357 3.07333C6.60578 2.38553 5.67293 1.99914 4.70024 1.99914C3.72755 1.99914 2.7947 2.38553 2.10691 3.07333C1.41911 3.76112 1.03271 4.69397 1.03271 5.66666C1.03271 6.63935 1.41911 7.5722 2.10691 8.26L2.81358 8.96666L8.00024 14.1533L13.1869 8.96666L13.8936 8.26C14.2342 7.91949 14.5045 7.51521 14.6889 7.07023C14.8732 6.62526 14.9681 6.14832 14.9681 5.66666C14.9681 5.185 14.8732 4.70807 14.6889 4.26309C14.5045 3.81812 14.2342 3.41383 13.8936 3.07333V3.07333Z"
                              stroke="#2C2C2C"
                              strokeWidth="2"
                              strokeLinecap="round"
                              strokeLinejoin="round"
                            />
                          </g>
                          <defs>
                            <clipPath id="clip0_622_7633">
                              <rect width="16" height="16" fill="white" />
                            </clipPath>
                          </defs>
                        </svg>
                      </div>
                      <div className="text-center text-gray-800 caption-medium">
                        관심
                      </div>
                    </div>
                  </div>
                  <div className="h-6 pl-2 pr-3 py-1 bg-gray-0 rounded-[15px] border border-gray-800 justify-start items-center gap-1 inline-flex">
                    <div className="justify-end items-center gap-2 flex">
                      <div className="w-4 h-4 relative">
                        <div className="w-4 h-4 left-0 top-0 absolute" />
                        <div
                          data-svg-wrapper
                          className="left-[1.33px] top-[3.33px] absolute"
                        >
                          <svg
                            width="16"
                            height="12"
                            viewBox="0 0 16 12"
                            fill="none"
                            xmlns="http://www.w3.org/2000/svg"
                          >
                            <path
                              d="M1.3335 1.33333V10.6667H14.6668V1.33333H14.3335H1.3335Z"
                              stroke="#2C2C2C"
                              strokeWidth="2"
                              strokeLinejoin="round"
                            />
                          </svg>
                        </div>
                        <div
                          data-svg-wrapper
                          className="left-[2.33px] top-[8px] absolute"
                        >
                          <svg
                            width="13"
                            height="6"
                            viewBox="0 0 13 6"
                            fill="none"
                            xmlns="http://www.w3.org/2000/svg"
                          >
                            <path
                              d="M13.0002 5.00002H11.0002M3.00016 4.00002L1.3335 5.00002M3.00016 5.00002H4.00016H5.00016M7.00016 1.00002H4.66683L9.00016 1C8.3335 0.999984 9.3335 1.00002 7.00016 1.00002Z"
                              stroke="#2C2C2C"
                              strokeWidth="2"
                              strokeLinejoin="round"
                            />
                          </svg>
                        </div>
                      </div>
                      <div className="text-center text-gray-800 caption-medium">
                        학급
                      </div>
                    </div>
                  </div>
                  <div className="h-6 pl-2 pr-3 py-1 bg-gray-0 rounded-[15px] border border-primary-500 justify-start items-center gap-1 inline-flex">
                    <div className="justify-start items-center gap-2 flex">
                      <div data-svg-wrapper className="relative">
                        <svg
                          width="16"
                          height="16"
                          viewBox="0 0 16 16"
                          fill="none"
                          xmlns="http://www.w3.org/2000/svg"
                        >
                          <g clipPath="url(#clip0_622_7594)">
                            <path
                              d="M7.3335 2.66667H2.66683C2.31321 2.66667 1.97407 2.80715 1.72402 3.0572C1.47397 3.30724 1.3335 3.64638 1.3335 4.00001V13.3333C1.3335 13.687 1.47397 14.0261 1.72402 14.2761C1.97407 14.5262 2.31321 14.6667 2.66683 14.6667H12.0002C12.3538 14.6667 12.6929 14.5262 12.943 14.2761C13.193 14.0261 13.3335 13.687 13.3335 13.3333V8.66667"
                              stroke="#FF6F61"
                              strokeWidth="2"
                              strokeLinecap="round"
                              strokeLinejoin="round"
                            />
                            <path
                              d="M12.3335 1.66667C12.5987 1.40145 12.9584 1.25246 13.3335 1.25246C13.7086 1.25246 14.0683 1.40145 14.3335 1.66667C14.5987 1.93189 14.7477 2.2916 14.7477 2.66667C14.7477 3.04174 14.5987 3.40145 14.3335 3.66667L8.00016 10L5.3335 10.6667L6.00016 8L12.3335 1.66667Z"
                              stroke="#FF6F61"
                              strokeWidth="2"
                              strokeLinecap="round"
                              strokeLinejoin="round"
                            />
                          </g>
                          <defs>
                            <clipPath id="clip0_622_7594">
                              <rect width="16" height="16" fill="white" />
                            </clipPath>
                          </defs>
                        </svg>
                      </div>
                      <div className="text-center text-primary-500 caption-medium">
                        숙제
                      </div>
                    </div>
                  </div>
                </div>
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

export default TeacherBookSearch;
