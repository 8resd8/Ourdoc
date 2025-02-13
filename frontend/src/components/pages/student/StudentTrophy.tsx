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
          <div className="flex justify-center items-center gap-8 mt-[40px] w-full mb-6">
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
        </div>
        <div className="w-[1064px] m-auto">
          <div className="flex justify-center mt-4">
            <MonthlyReportChart />
            <MonthlyReportListSection />
          </div>
          <div>
            <div className="w-[517px] h-[460px] p-6 left-[193px] top-[790px] absolute bg-gray-0 rounded-[15px] shadow-xsmall flex-col justify-start items-start gap-3 inline-flex">
              <div className="text-gray-800 headline-medium">
                학급 평균과 나
              </div>
              <div className="self-stretch grow shrink basis-0 justify-center items-center gap-9 inline-flex">
                <div className="w-40 flex-col justify-start items-center gap-2 inline-flex">
                  <div className="self-stretch h-40 px-[19px] py-[49px] rounded-[80px] border border-gray-700 flex-col justify-center items-center gap-2.5 flex">
                    <div>
                      <span className="text-gray-700 display-medium">13.5</span>
                      <span className="text-gray-700 body-medium">권</span>
                    </div>
                  </div>
                  <div className="self-stretch text-center text-gray-800 body-medium">
                    학급 평균
                  </div>
                </div>
                <div className="flex-col justify-start items-center gap-2 inline-flex">
                  <div className="w-60 h-60 px-[69px] py-[76px] rounded-[120px] border-2 border-primary-500 flex-col justify-center items-center gap-2.5 flex">
                    <div>
                      <span className="text-primary-500 display-medium">
                        33
                      </span>
                      <span className="text-primary-500 body-medium">권</span>
                    </div>
                  </div>
                  <div className="self-stretch text-center text-gray-800 body-medium">
                    나
                  </div>
                </div>
              </div>
            </div>
            <div className="h-[222px] p-6 left-[193px] w-[1000px] top-[1330px] absolute bg-gray-0 rounded-[15px] shadow-xsmall flex-col justify-start items-start gap-3 inline-flex">
              <div className="text-gray-800 headline-medium">상장</div>
              <div className="self-stretch justify-start items-center gap-6 inline-flex ">
                <div className="w-32 flex-col justify-start items-center gap-3 inline-flex">
                  <img
                    className="w-[100px] h-[100px]"
                    src="https://placehold.co/100x100"
                  />
                  <div className="self-stretch text-center text-gray-800 body-medium">
                    착한 어린이 상
                  </div>
                </div>
                <div className="w-32 flex-col justify-start items-center gap-3 inline-flex">
                  <img
                    className="w-[100px] h-[100px]"
                    src="https://placehold.co/100x100"
                  />
                  <div className="self-stretch text-center text-primary-500 body-medium">
                    착한 어린이 상
                  </div>
                </div>
                <div className="w-32 flex-col justify-start items-center gap-3 inline-flex">
                  <img
                    className="w-[100px] h-[100px]"
                    src="https://placehold.co/100x100"
                  />
                  <div className="self-stretch text-center text-gray-800 body-medium">
                    착한 어린이 상
                  </div>
                </div>
                <div className="w-32 flex-col justify-start items-center gap-3 inline-flex">
                  <img
                    className="w-[100px] h-[100px]"
                    src="https://placehold.co/100x100"
                  />
                  <div className="self-stretch text-center text-gray-800 body-medium">
                    착한 어린이 상
                  </div>
                </div>
              </div>
            </div>
            <div className="h-[242px] p-6 left-[193px] top-[1632px] absolute bg-gray-0 rounded-[15px] shadow-xsmall flex-col justify-start items-start gap-3 inline-flex">
              <div>
                <span className="text-gray-800 headline-medium">칭찬도장 </span>
                <span className="text-gray-700 body-medium">4개</span>
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
                <div className="w-[72px] h-[72px] px-6 py-2.5 rounded-[40px] border border-gray-500 flex-col justify-center items-center gap-2.5 inline-flex">
                  <div className="text-gray-500 headline-large">5</div>
                </div>
                <div className="w-[72px] h-[72px] px-6 py-2.5 rounded-[40px] border border-gray-500 flex-col justify-center items-center gap-2.5 inline-flex">
                  <div className="text-gray-500 headline-large">5</div>
                </div>
                <div className="w-[72px] h-[72px] px-6 py-2.5 rounded-[40px] border border-gray-500 flex-col justify-center items-center gap-2.5 inline-flex">
                  <div className="text-gray-500 headline-large">5</div>
                </div>
                <div className="w-[72px] h-[72px] px-6 py-2.5 rounded-[40px] border border-gray-500 flex-col justify-center items-center gap-2.5 inline-flex">
                  <div className="text-gray-500 headline-large">5</div>
                </div>
                <div className="w-[72px] h-[72px] px-6 py-2.5 rounded-[40px] border border-gray-500 flex-col justify-center items-center gap-2.5 inline-flex">
                  <div className="text-gray-500 headline-large">5</div>
                </div>
                <div className="w-[72px] h-[72px] px-6 py-2.5 rounded-[40px] border border-gray-500 flex-col justify-center items-center gap-2.5 inline-flex">
                  <div className="text-gray-500 headline-large">5</div>
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
                <div className="w-[72px] h-[72px] px-6 py-2.5 rounded-[40px] border border-gray-500 flex-col justify-center items-center gap-2.5 inline-flex">
                  <div className="text-gray-500 headline-large">5</div>
                </div>
                <div className="w-[72px] h-[72px] px-6 py-2.5 rounded-[40px] border border-gray-500 flex-col justify-center items-center gap-2.5 inline-flex">
                  <div className="text-gray-500 headline-large">5</div>
                </div>
                <div className="w-[72px] h-[72px] px-6 py-2.5 rounded-[40px] border border-gray-500 flex-col justify-center items-center gap-2.5 inline-flex">
                  <div className="text-gray-500 headline-large">5</div>
                </div>
                <div className="w-[72px] h-[72px] px-6 py-2.5 rounded-[40px] border border-gray-500 flex-col justify-center items-center gap-2.5 inline-flex">
                  <div className="text-gray-500 headline-large">5</div>
                </div>
                <div className="w-[72px] h-[72px] px-6 py-2.5 rounded-[40px] border border-gray-500 flex-col justify-center items-center gap-2.5 inline-flex">
                  <div className="text-gray-500 headline-large">5</div>
                </div>
                <div className="w-[72px] h-[72px] px-6 py-2.5 rounded-[40px] border border-gray-500 flex-col justify-center items-center gap-2.5 inline-flex">
                  <div className="text-gray-500 headline-large">5</div>
                </div>
              </div>
            </div>
            <div className="w-[522px] h-[464px] p-6 left-[730px] top-[790px] absolute bg-gray-0 rounded-[15px] shadow-xsmall flex-col justify-start items-start gap-3 inline-flex">
              <div className="text-gray-800 headline-medium">반 1등과 나</div>
              <div className="self-stretch grow shrink basis-0 justify-start items-start gap-2 inline-flex overflow-hidden">
                <div className="grow shrink basis-0 self-stretch justify-center items-center gap-9 flex">
                  <div className="flex-col justify-start items-center gap-2 inline-flex">
                    <div className="w-[200px] h-[200px] px-[31px] py-[55px] rounded-[100px] border border-gray-700 flex-col justify-center items-center gap-2.5 flex">
                      <div>
                        <span className="text-gray-700 display-medium">33</span>
                        <span className="text-gray-700 body-medium">권</span>
                      </div>
                    </div>
                    <div className="self-stretch text-center text-gray-800 body-medium">
                      반 1등
                    </div>
                  </div>
                  <div className="flex-col justify-start items-center gap-2 inline-flex">
                    <div className="w-[200px] h-[200px] px-[69px] py-[76px] rounded-[120px] border-2 border-primary-500 flex-col justify-center items-center gap-2.5 flex">
                      <div>
                        <span className="text-primary-500 display-medium">
                          33
                        </span>
                        <span className="text-primary-500 body-medium">권</span>
                      </div>
                    </div>
                    <div className="self-stretch text-center text-gray-800 body-medium">
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
