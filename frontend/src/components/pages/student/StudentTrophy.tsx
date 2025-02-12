import { MonthlyReportChart } from '../../molecules/MonthlyReportChart';
import { MonthlyReportListSection } from '../../molecules/MonthlyReportListSection';

const StudentTrophy = () => {
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
          <div className="w-[185px] h-[232px] left-0 top-0 absolute bg-white rounded-tr-[10px] rounded-br-[10px] shadow-[0px_2px_14px_2px_rgba(33,33,33,0.03)] border border-[#e0e0e0]" />
          <img
            className="w-[181px] h-[232px] left-0 top-0 absolute rounded-tr-[10px] rounded-br-[10px] shadow-[0px_2px_14px_2px_rgba(33,33,33,0.03)] border border-[#e0e0e0]"
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
          <div className="grow shrink basis-0 self-stretch text-[#2c2c2c] text-lg font-semibold font-['Pretendard'] leading-normal">
            {book.title}
          </div>
        </div>
        <div className="self-stretch h-12 text-[#cccccc] text-sm font-normal font-['Pretendard'] leading-tight">
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
          <div className="text-center text-[#2c2c2c] text-[26px] font-semibold font-['Pretendard'] leading-[30px] mt-8">
            4학년
          </div>
          <div className="flex justify-center items-center gap-8 mt-[40px] w-full mb-6">
            {[1, 2, 3, 4].map((grade) => (
              <div
                key={grade}
                className="w-[72px] h-[72px] px-3 bg-white rounded-[15px] shadow-[0px_2px_14px_2px_rgba(33,33,33,0.03)] flex justify-center items-center"
              >
                <div className="w-12 flex-col justify-center items-center inline-flex">
                  <div
                    className={`text-center ${grade === 4 ? 'text-[#ff6f61]' : 'text-[#4e4e4e]'} text-[26px] font-semibold font-['Pretendard'] leading-[30px]`}
                  >
                    {grade}
                  </div>
                  <div
                    className={`text-center ${grade === 4 ? 'text-[#ff6f61]' : 'text-[#4e4e4e]'} text-xs font-normal font-['Pretendard'] leading-none`}
                  >
                    학년
                  </div>
                </div>
              </div>
            ))}
          </div>
        </div>
        <div className="w-[1064px] m-auto">
          <div className="flex justify-center mt-4">
            <MonthlyReportChart />
            <MonthlyReportListSection />
          </div>
          <div>
            <div className="w-[517px] h-[460px] p-6 left-[193px] top-[790px] absolute bg-white rounded-[15px] shadow-[0px_2px_12px_1px_rgba(33,33,33,0.09)] flex-col justify-start items-start gap-3 inline-flex">
              <div className="text-[#2c2c2c] text-[22px] font-semibold font-['Pretendard'] leading-relaxed">
                학급 평균과 나
              </div>
              <div className="self-stretch grow shrink basis-0 justify-center items-center gap-9 inline-flex">
                <div className="w-40 flex-col justify-start items-center gap-2 inline-flex">
                  <div className="self-stretch h-40 px-[19px] py-[49px] rounded-[80px] border border-[#4e4e4e] flex-col justify-center items-center gap-2.5 flex">
                    <div>
                      <span className="text-[#4e4e4e] text-5xl font-semibold font-['Pretendard'] leading-[54px]">
                        13.5
                      </span>
                      <span className="text-[#4e4e4e] text-base font-normal font-['Pretendard'] leading-normal">
                        권
                      </span>
                    </div>
                  </div>
                  <div className="self-stretch text-center text-black text-base font-normal font-['Pretendard'] leading-normal">
                    학급 평균
                  </div>
                </div>
                <div className="flex-col justify-start items-center gap-2 inline-flex">
                  <div className="w-60 h-60 px-[69px] py-[76px] rounded-[120px] border-2 border-[#ff6f61] flex-col justify-center items-center gap-2.5 flex">
                    <div>
                      <span className="text-[#ff6f61] text-5xl font-semibold font-['Pretendard'] leading-[54px]">
                        33
                      </span>
                      <span className="text-[#ff6f61] text-base font-normal font-['Pretendard'] leading-normal">
                        권
                      </span>
                    </div>
                  </div>
                  <div className="self-stretch text-center text-[#2c2c2c] text-base font-normal font-['Pretendard'] leading-normal">
                    나
                  </div>
                </div>
              </div>
            </div>
            <div className="h-[222px] p-6 left-[193px] w-[1000px] top-[1330px] absolute bg-white rounded-[15px] shadow-[0px_2px_12px_1px_rgba(33,33,33,0.09)] flex-col justify-start items-start gap-3 inline-flex">
              <div className="text-[#2c2c2c] text-[22px] font-semibold font-['Pretendard'] leading-relaxed">
                상장
              </div>
              <div className="self-stretch justify-start items-center gap-6 inline-flex ">
                <div className="w-32 flex-col justify-start items-center gap-3 inline-flex">
                  <img
                    className="w-[100px] h-[100px]"
                    src="https://placehold.co/100x100"
                  />
                  <div className="self-stretch text-center text-[#2c2c2c] text-base font-normal font-['Pretendard'] leading-normal">
                    착한 어린이 상
                  </div>
                </div>
                <div className="w-32 flex-col justify-start items-center gap-3 inline-flex">
                  <img
                    className="w-[100px] h-[100px]"
                    src="https://placehold.co/100x100"
                  />
                  <div className="self-stretch text-center text-[#ff6f61] text-base font-normal font-['Pretendard'] leading-normal">
                    착한 어린이 상
                  </div>
                </div>
                <div className="w-32 flex-col justify-start items-center gap-3 inline-flex">
                  <img
                    className="w-[100px] h-[100px]"
                    src="https://placehold.co/100x100"
                  />
                  <div className="self-stretch text-center text-[#2c2c2c] text-base font-normal font-['Pretendard'] leading-normal">
                    착한 어린이 상
                  </div>
                </div>
                <div className="w-32 flex-col justify-start items-center gap-3 inline-flex">
                  <img
                    className="w-[100px] h-[100px]"
                    src="https://placehold.co/100x100"
                  />
                  <div className="self-stretch text-center text-[#2c2c2c] text-base font-normal font-['Pretendard'] leading-normal">
                    착한 어린이 상
                  </div>
                </div>
              </div>
            </div>
            <div className="h-[242px] p-6 left-[193px] top-[1632px] absolute bg-white rounded-[15px] shadow-[0px_2px_12px_1px_rgba(33,33,33,0.09)] flex-col justify-start items-start gap-3 inline-flex">
              <div>
                <span className="text-[#2c2c2c] text-[22px] font-semibold font-['Pretendard'] leading-relaxed">
                  칭찬도장{' '}
                </span>
                <span className="text-[#4e4e4e] text-base font-normal font-['Pretendard'] leading-normal">
                  4개
                </span>
              </div>
              <div className="self-stretch justify-start items-center gap-6 inline-flex">
                <img
                  className="w-[72px] h-[72px]"
                  src="https://placehold.co/72x72"
                />
                <img
                  className="w-[72px] h-[72px]"
                  src="https://placehold.co/72x72"
                />
                <img
                  className="w-[72px] h-[72px]"
                  src="https://placehold.co/72x72"
                />
                <img
                  className="w-[72px] h-[72px]"
                  src="https://placehold.co/72x72"
                />
                <div className="w-[72px] h-[72px] px-6 py-2.5 rounded-[40px] border border-[#8c8c8c] flex-col justify-center items-center gap-2.5 inline-flex">
                  <div className="text-[#8c8c8c] text-[26px] font-semibold font-['Pretendard'] leading-[30px]">
                    5
                  </div>
                </div>
                <div className="w-[72px] h-[72px] px-6 py-2.5 rounded-[40px] border border-[#8c8c8c] flex-col justify-center items-center gap-2.5 inline-flex">
                  <div className="text-[#8c8c8c] text-[26px] font-semibold font-['Pretendard'] leading-[30px]">
                    5
                  </div>
                </div>
                <div className="w-[72px] h-[72px] px-6 py-2.5 rounded-[40px] border border-[#8c8c8c] flex-col justify-center items-center gap-2.5 inline-flex">
                  <div className="text-[#8c8c8c] text-[26px] font-semibold font-['Pretendard'] leading-[30px]">
                    5
                  </div>
                </div>
                <div className="w-[72px] h-[72px] px-6 py-2.5 rounded-[40px] border border-[#8c8c8c] flex-col justify-center items-center gap-2.5 inline-flex">
                  <div className="text-[#8c8c8c] text-[26px] font-semibold font-['Pretendard'] leading-[30px]">
                    5
                  </div>
                </div>
                <div className="w-[72px] h-[72px] px-6 py-2.5 rounded-[40px] border border-[#8c8c8c] flex-col justify-center items-center gap-2.5 inline-flex">
                  <div className="text-[#8c8c8c] text-[26px] font-semibold font-['Pretendard'] leading-[30px]">
                    5
                  </div>
                </div>
                <div className="w-[72px] h-[72px] px-6 py-2.5 rounded-[40px] border border-[#8c8c8c] flex-col justify-center items-center gap-2.5 inline-flex">
                  <div className="text-[#8c8c8c] text-[26px] font-semibold font-['Pretendard'] leading-[30px]">
                    5
                  </div>
                </div>
              </div>
              <div className="self-stretch justify-start items-center gap-6 inline-flex">
                <img
                  className="w-[72px] h-[72px]"
                  src="https://placehold.co/72x72"
                />
                <img
                  className="w-[72px] h-[72px]"
                  src="https://placehold.co/72x72"
                />
                <img
                  className="w-[72px] h-[72px]"
                  src="https://placehold.co/72x72"
                />
                <img
                  className="w-[72px] h-[72px]"
                  src="https://placehold.co/72x72"
                />
                <div className="w-[72px] h-[72px] px-6 py-2.5 rounded-[40px] border border-[#8c8c8c] flex-col justify-center items-center gap-2.5 inline-flex">
                  <div className="text-[#8c8c8c] text-[26px] font-semibold font-['Pretendard'] leading-[30px]">
                    5
                  </div>
                </div>
                <div className="w-[72px] h-[72px] px-6 py-2.5 rounded-[40px] border border-[#8c8c8c] flex-col justify-center items-center gap-2.5 inline-flex">
                  <div className="text-[#8c8c8c] text-[26px] font-semibold font-['Pretendard'] leading-[30px]">
                    5
                  </div>
                </div>
                <div className="w-[72px] h-[72px] px-6 py-2.5 rounded-[40px] border border-[#8c8c8c] flex-col justify-center items-center gap-2.5 inline-flex">
                  <div className="text-[#8c8c8c] text-[26px] font-semibold font-['Pretendard'] leading-[30px]">
                    5
                  </div>
                </div>
                <div className="w-[72px] h-[72px] px-6 py-2.5 rounded-[40px] border border-[#8c8c8c] flex-col justify-center items-center gap-2.5 inline-flex">
                  <div className="text-[#8c8c8c] text-[26px] font-semibold font-['Pretendard'] leading-[30px]">
                    5
                  </div>
                </div>
                <div className="w-[72px] h-[72px] px-6 py-2.5 rounded-[40px] border border-[#8c8c8c] flex-col justify-center items-center gap-2.5 inline-flex">
                  <div className="text-[#8c8c8c] text-[26px] font-semibold font-['Pretendard'] leading-[30px]">
                    5
                  </div>
                </div>
                <div className="w-[72px] h-[72px] px-6 py-2.5 rounded-[40px] border border-[#8c8c8c] flex-col justify-center items-center gap-2.5 inline-flex">
                  <div className="text-[#8c8c8c] text-[26px] font-semibold font-['Pretendard'] leading-[30px]">
                    5
                  </div>
                </div>
              </div>
            </div>
            <div className="w-[522px] h-[464px] p-6 left-[730px] top-[790px] absolute bg-white rounded-[15px] shadow-[0px_2px_12px_1px_rgba(33,33,33,0.09)] flex-col justify-start items-start gap-3 inline-flex">
              <div className="text-[#2c2c2c] text-[22px] font-semibold font-['Pretendard'] leading-relaxed">
                반 1등과 나
              </div>
              <div className="self-stretch grow shrink basis-0 justify-start items-start gap-2 inline-flex overflow-hidden">
                <div className="grow shrink basis-0 self-stretch justify-center items-center gap-9 flex">
                  <div className="flex-col justify-start items-center gap-2 inline-flex">
                    <div className="w-[200px] h-[200px] px-[31px] py-[55px] rounded-[100px] border border-[#4e4e4e] flex-col justify-center items-center gap-2.5 flex">
                      <div>
                        <span className="text-[#4e4e4e] text-5xl font-semibold font-['Pretendard'] leading-[54px]">
                          33
                        </span>
                        <span className="text-[#4e4e4e] text-base font-normal font-['Pretendard'] leading-normal">
                          권
                        </span>
                      </div>
                    </div>
                    <div className="self-stretch text-center text-black text-base font-normal font-['Pretendard'] leading-normal">
                      반 1등
                    </div>
                  </div>
                  <div className="flex-col justify-start items-center gap-2 inline-flex">
                    <div className="w-[200px] h-[200px] px-[69px] py-[76px] rounded-[120px] border-2 border-[#ff6f61] flex-col justify-center items-center gap-2.5 flex">
                      <div>
                        <span className="text-[#ff6f61] text-5xl font-semibold font-['Pretendard'] leading-[54px]">
                          33
                        </span>
                        <span className="text-[#ff6f61] text-base font-normal font-['Pretendard'] leading-normal">
                          권
                        </span>
                      </div>
                    </div>
                    <div className="self-stretch text-center text-[#2c2c2c] text-base font-normal font-['Pretendard'] leading-normal">
                      나
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};
export default StudentTrophy;
