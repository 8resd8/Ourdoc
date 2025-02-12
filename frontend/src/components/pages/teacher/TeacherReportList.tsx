const TeacherReportList = () => {
  return (
    <div className="">
      <div className="w-[226px] h-[914px] left-0 top-[110px] absolute bg-white border-r border-gray-200  overflow-hidden">
        <div className="w-[194px] p-2 left-[16px] top-[56px] absolute border-b border-gray-900 justify-between items-center inline-flex">
          <div className="text-gray-300 text-base font-normal font-['Pretendard'] leading-normal">
            도서를 검색해주세요.
          </div>
          <div data-svg-wrapper className="relative">
            <svg
              width="24"
              height="24"
              viewBox="0 0 24 24"
              fill="none"
              xmlns="http://www.w3.org/2000/svg"
            >
              <path
                d="M11 19C15.4183 19 19 15.4183 19 11C19 6.58172 15.4183 3 11 3C6.58172 3 3 6.58172 3 11C3 15.4183 6.58172 19 11 19Z"
                stroke="black"
                strokeWidth="2"
                strokeLinecap="round"
                strokeLinejoin="round"
              />
              <path
                d="M20.9999 21L16.6499 16.65"
                stroke="black"
                strokeWidth="2"
                strokeLinecap="round"
                strokeLinejoin="round"
              />
            </svg>
          </div>
        </div>
        <div className="w-[226px] h-[796px] left-0 top-[112px] absolute bg-white border-t border-gray-200" />
        <div className="left-[16px] top-[24px] absolute text-gray-800 text-xs font-normal font-['Pretendard'] leading-none">
          도서 검색
        </div>
        <div className="left-[16px] top-[136px] absolute text-gray-800 text-xs font-normal font-['Pretendard'] leading-none">
          반별 조회
        </div>
        <div className="h-[296px] left-0 top-[164px] absolute flex-col justify-start items-start gap-4 inline-flex">
          <div className="h-[136px] relative">
            <div className="w-[226px] h-10 px-10 py-2 left-0 top-[48px] absolute bg-primary-50 flex-col justify-start items-start gap-2 inline-flex">
              <div className="text-primary-500 text-base font-bold font-['Pretendard'] leading-normal">
                성룡초 1학년 2반
              </div>
            </div>
            <div className="w-[226px] h-10 px-10 py-2 left-0 top-[96px] absolute bg-white flex-col justify-start items-start gap-2 inline-flex">
              <div className="text-gray-800 text-base font-normal font-['Pretendard'] leading-normal">
                성천초 1학년 7반
              </div>
            </div>
            <div className="w-[226px] h-10 px-4 py-2 left-0 top-0 absolute flex-col justify-start items-start gap-2 inline-flex">
              <div className="self-stretch grow shrink basis-0 justify-start items-center gap-2 inline-flex">
                <div data-svg-wrapper className="relative">
                  <svg
                    width="16"
                    height="16"
                    viewBox="0 0 16 16"
                    fill="none"
                    xmlns="http://www.w3.org/2000/svg"
                  >
                    <path
                      d="M12 10L8 6L4 10"
                      stroke="black"
                      strokeWidth="2"
                      strokeLinecap="round"
                      strokeLinejoin="round"
                    />
                  </svg>
                </div>
                <div className="text-gray-800 text-base font-bold font-['Pretendard'] leading-normal">
                  2025년도
                </div>
              </div>
            </div>
          </div>
          <div className="h-[88px] relative">
            <div className="w-[226px] h-10 px-10 py-2 left-0 top-[48px] absolute bg-white flex-col justify-start items-start gap-2 inline-flex">
              <div className="text-gray-800 text-base font-normal font-['Pretendard'] leading-normal">
                성룡초 1학년 2반
              </div>
            </div>
            <div className="w-[226px] h-10 px-4 py-2 left-0 top-0 absolute flex-col justify-start items-start gap-2 inline-flex">
              <div className="self-stretch grow shrink basis-0 justify-start items-center gap-2 inline-flex">
                <div data-svg-wrapper className="relative">
                  <svg
                    width="16"
                    height="16"
                    viewBox="0 0 16 16"
                    fill="none"
                    xmlns="http://www.w3.org/2000/svg"
                  >
                    <path
                      d="M12 10L8 6L4 10"
                      stroke="black"
                      strokeWidth="2"
                      strokeLinecap="round"
                      strokeLinejoin="round"
                    />
                  </svg>
                </div>
                <div className="text-gray-800 text-base font-bold font-['Pretendard'] leading-normal">
                  2024년도
                </div>
              </div>
            </div>
          </div>
          <div className="self-stretch grow shrink basis-0 px-4 py-2 flex-col justify-start items-start gap-2 flex">
            <div className="w-[98px] justify-start items-center gap-2 inline-flex">
              <div data-svg-wrapper className="relative">
                <svg
                  width="16"
                  height="16"
                  viewBox="0 0 16 16"
                  fill="none"
                  xmlns="http://www.w3.org/2000/svg"
                >
                  <path
                    d="M4 6L8 10L12 6"
                    stroke="black"
                    strokeWidth="2"
                    strokeLinecap="round"
                    strokeLinejoin="round"
                  />
                </svg>
              </div>
              <div className="text-gray-800 text-base font-bold font-['Pretendard'] leading-normal">
                2023년도
              </div>
            </div>
          </div>
        </div>
      </div>
      <div className="w-[158px] h-[914px] left-[227px] top-[110px] absolute bg-white border-r border-gray-200">
        <div className="left-[16px] top-[24px] absolute text-center text-gray-800 text-xs font-normal font-['Pretendard'] leading-none">
          학생별 보기
        </div>
        <div className="h-[184px] left-0 top-[52px] absolute flex-col justify-start items-start gap-2 inline-flex">
          <div className="self-stretch h-10 px-4 py-2 bg-white justify-start items-center gap-2 inline-flex">
            <div className="text-center text-gray-800 text-sm font-normal font-['Pretendard'] leading-tight">
              1번
            </div>
            <div className="w-[97px] text-gray-800 text-base font-normal font-['Pretendard'] leading-normal">
              김보민
            </div>
          </div>
          <div className="self-stretch h-10 px-4 py-2 bg-primary-50 justify-start items-center gap-2 inline-flex">
            <div className="text-center text-primary-500 text-sm font-bold font-['Pretendard'] leading-tight">
              2번
            </div>
            <div className="w-[97px] text-primary-500 text-base font-bold font-['Pretendard'] leading-normal">
              김융융
            </div>
          </div>
          <div className="self-stretch h-10 px-4 py-2 bg-white justify-start items-center gap-2 inline-flex">
            <div className="text-center text-gray-800 text-sm font-normal font-['Pretendard'] leading-tight">
              3번
            </div>
            <div className="w-[97px] text-gray-800 text-base font-normal font-['Pretendard'] leading-normal">
              김김보김보민보민
            </div>
          </div>
          <div className="self-stretch h-10 px-4 py-2 bg-white justify-start items-center gap-2 inline-flex">
            <div className="text-center text-gray-800 text-sm font-normal font-['Pretendard'] leading-tight">
              4번
            </div>
            <div className="w-[97px] text-gray-800 text-base font-normal font-['Pretendard'] leading-normal">
              김김보김보민루루보민
            </div>
          </div>
        </div>
      </div>

      <div className="left-[405px] top-[254px] absolute flex-col justify-start items-center gap-3 inline-flex">
        <div className="w-[858px] h-[567px] relative">
          <div className="h-12 px-6 py-3 left-0 top-0 absolute border-b border-gray-900 justify-start items-center gap-[18px] inline-flex">
            <div className="w-[60px] text-center text-gray-800 text-base font-normal font-['Pretendard'] leading-normal">
              No
            </div>
            <div className="w-60 text-gray-800 text-base font-normal font-['Pretendard'] leading-normal">
              도서명
            </div>
            <div className="w-[60px] text-center text-gray-800 text-base font-normal font-['Pretendard'] leading-normal">
              번호
            </div>
            <div className="w-[120px] text-center text-gray-800 text-base font-normal font-['Pretendard'] leading-normal">
              학생 이름
            </div>
            <div className="w-[120px] text-center text-gray-800 text-base font-normal font-['Pretendard'] leading-normal">
              제출 날짜
            </div>
            <div className="w-[120px] text-center text-gray-800 text-base font-normal font-['Pretendard'] leading-normal">
              승인 여부
            </div>
          </div>
          <div className="h-[520px] left-0 top-[47px] absolute flex-col justify-start items-start inline-flex">
            <div className="self-stretch px-6 py-3 justify-start items-center gap-[18px] inline-flex">
              <div className="w-[60px] text-center text-gray-800 text-base font-normal font-['Pretendard'] leading-normal">
                1
              </div>
              <div className="w-60 text-gray-800 text-base font-normal font-['Pretendard'] leading-normal">
                별이 빛나는 밤에
              </div>
              <div className="w-[60px] text-center text-gray-800 text-base font-normal font-['Pretendard'] leading-normal">
                8번
              </div>
              <div className="w-[120px] text-center text-gray-800 text-base font-normal font-['Pretendard'] leading-normal">
                김현우현우
              </div>
              <div className="w-[120px] text-center text-gray-800 text-base font-normal font-['Pretendard'] leading-normal">
                5월 1일
              </div>
              <div className="w-[120px] flex-col justify-start items-center inline-flex">
                <div className="px-3 py-1 rounded-[5px] border border-system-success justify-center items-center inline-flex">
                  <div className="text-center text-system-success text-sm font-normal font-['Pretendard'] leading-tight">
                    완료
                  </div>
                </div>
              </div>
            </div>
            <div className="self-stretch px-6 py-3 justify-start items-center gap-[18px] inline-flex">
              <div className="w-[60px] text-center text-gray-800 text-base font-normal font-['Pretendard'] leading-normal">
                1
              </div>
              <div className="w-60 text-gray-800 text-base font-normal font-['Pretendard'] leading-normal">
                별이 빛나는 밤에
              </div>
              <div className="w-[60px] text-center text-gray-800 text-base font-normal font-['Pretendard'] leading-normal">
                8번
              </div>
              <div className="w-[120px] text-center text-gray-800 text-base font-normal font-['Pretendard'] leading-normal">
                김현우현우
              </div>
              <div className="w-[120px] text-center text-gray-800 text-base font-normal font-['Pretendard'] leading-normal">
                5월 1일
              </div>
              <div className="w-[120px] flex-col justify-start items-center inline-flex">
                <div className="px-3 py-1 rounded-[5px] border border-system-danger justify-center items-center inline-flex">
                  <div className="text-center text-system-danger text-sm font-normal font-['Pretendard'] leading-tight">
                    미완료
                  </div>
                </div>
              </div>
            </div>
            <div className="self-stretch px-6 py-3 bg-primary-50 justify-start items-center gap-[18px] inline-flex">
              <div className="w-[60px] text-center text-gray-800 text-base font-normal font-['Pretendard'] leading-normal">
                1
              </div>
              <div className="w-60 text-gray-800 text-base font-normal font-['Pretendard'] leading-normal">
                별이 빛나는 밤에
              </div>
              <div className="w-[60px] text-center text-gray-800 text-base font-normal font-['Pretendard'] leading-normal">
                8번
              </div>
              <div className="w-[120px] text-center text-gray-800 text-base font-normal font-['Pretendard'] leading-normal">
                김현우현우
              </div>
              <div className="w-[120px] text-center text-gray-800 text-base font-normal font-['Pretendard'] leading-normal">
                5월 1일
              </div>
              <div className="w-[120px] flex-col justify-start items-center inline-flex">
                <div className="px-3 py-1 bg-white rounded-[5px] border border-system-success justify-center items-center inline-flex">
                  <div className="text-center text-system-success text-sm font-normal font-['Pretendard'] leading-tight">
                    완료
                  </div>
                </div>
              </div>
            </div>
            <div className="self-stretch px-6 py-3 justify-start items-center gap-[18px] inline-flex">
              <div className="w-[60px] text-center text-gray-800 text-base font-normal font-['Pretendard'] leading-normal">
                1
              </div>
              <div className="w-60 text-gray-800 text-base font-normal font-['Pretendard'] leading-normal">
                별이 빛나는 밤에
              </div>
              <div className="w-[60px] text-center text-gray-800 text-base font-normal font-['Pretendard'] leading-normal">
                8번
              </div>
              <div className="w-[120px] text-center text-gray-800 text-base font-normal font-['Pretendard'] leading-normal">
                김현우현우
              </div>
              <div className="w-[120px] text-center text-gray-800 text-base font-normal font-['Pretendard'] leading-normal">
                5월 1일
              </div>
              <div className="w-[120px] flex-col justify-start items-center inline-flex">
                <div className="px-3 py-1 rounded-[5px] border border-system-success justify-center items-center inline-flex">
                  <div className="text-center text-system-success text-sm font-normal font-['Pretendard'] leading-tight">
                    완료
                  </div>
                </div>
              </div>
            </div>
            <div className="self-stretch px-6 py-3 justify-start items-center gap-[18px] inline-flex">
              <div className="w-[60px] text-center text-gray-800 text-base font-normal font-['Pretendard'] leading-normal">
                1
              </div>
              <div className="w-60 text-gray-800 text-base font-normal font-['Pretendard'] leading-normal">
                별이 빛나는 밤에
              </div>
              <div className="w-[60px] text-center text-gray-800 text-base font-normal font-['Pretendard'] leading-normal">
                8번
              </div>
              <div className="w-[120px] text-center text-gray-800 text-base font-normal font-['Pretendard'] leading-normal">
                김현우현우
              </div>
              <div className="w-[120px] text-center text-gray-800 text-base font-normal font-['Pretendard'] leading-normal">
                5월 1일
              </div>
              <div className="w-[120px] flex-col justify-start items-center inline-flex">
                <div className="px-3 py-1 rounded-[5px] border border-system-success justify-center items-center inline-flex">
                  <div className="text-center text-system-success text-sm font-normal font-['Pretendard'] leading-tight">
                    완료
                  </div>
                </div>
              </div>
            </div>
            <div className="self-stretch px-6 py-3 justify-start items-center gap-[18px] inline-flex">
              <div className="w-[60px] text-center text-gray-800 text-base font-normal font-['Pretendard'] leading-normal">
                1
              </div>
              <div className="w-60 text-gray-800 text-base font-normal font-['Pretendard'] leading-normal">
                별이 빛나는 밤에
              </div>
              <div className="w-[60px] text-center text-gray-800 text-base font-normal font-['Pretendard'] leading-normal">
                8번
              </div>
              <div className="w-[120px] text-center text-gray-800 text-base font-normal font-['Pretendard'] leading-normal">
                김현우현우
              </div>
              <div className="w-[120px] text-center text-gray-800 text-base font-normal font-['Pretendard'] leading-normal">
                5월 1일
              </div>
              <div className="w-[120px] flex-col justify-start items-center inline-flex">
                <div className="px-3 py-1 rounded-[5px] border border-system-success justify-center items-center inline-flex">
                  <div className="text-center text-system-success text-sm font-normal font-['Pretendard'] leading-tight">
                    완료
                  </div>
                </div>
              </div>
            </div>
            <div className="self-stretch px-6 py-3 justify-start items-center gap-[18px] inline-flex">
              <div className="w-[60px] text-center text-gray-800 text-base font-normal font-['Pretendard'] leading-normal">
                1
              </div>
              <div className="w-60 text-gray-800 text-base font-normal font-['Pretendard'] leading-normal">
                별이 빛나는 밤에
              </div>
              <div className="w-[60px] text-center text-gray-800 text-base font-normal font-['Pretendard'] leading-normal">
                8번
              </div>
              <div className="w-[120px] text-center text-gray-800 text-base font-normal font-['Pretendard'] leading-normal">
                김현우현우
              </div>
              <div className="w-[120px] text-center text-gray-800 text-base font-normal font-['Pretendard'] leading-normal">
                5월 1일
              </div>
              <div className="w-[120px] flex-col justify-start items-center inline-flex">
                <div className="px-3 py-1 rounded-[5px] border border-system-success justify-center items-center inline-flex">
                  <div className="text-center text-system-success text-sm font-normal font-['Pretendard'] leading-tight">
                    완료
                  </div>
                </div>
              </div>
            </div>
            <div className="self-stretch px-6 py-3 justify-start items-center gap-[18px] inline-flex">
              <div className="w-[60px] text-center text-gray-800 text-base font-normal font-['Pretendard'] leading-normal">
                1
              </div>
              <div className="w-60 text-gray-800 text-base font-normal font-['Pretendard'] leading-normal">
                별이 빛나는 밤에
              </div>
              <div className="w-[60px] text-center text-gray-800 text-base font-normal font-['Pretendard'] leading-normal">
                8번
              </div>
              <div className="w-[120px] text-center text-gray-800 text-base font-normal font-['Pretendard'] leading-normal">
                김현우현우
              </div>
              <div className="w-[120px] text-center text-gray-800 text-base font-normal font-['Pretendard'] leading-normal">
                5월 1일
              </div>
              <div className="w-[120px] flex-col justify-start items-center inline-flex">
                <div className="px-3 py-1 rounded-[5px] border border-system-success justify-center items-center inline-flex">
                  <div className="text-center text-system-success text-sm font-normal font-['Pretendard'] leading-tight">
                    완료
                  </div>
                </div>
              </div>
            </div>
            <div className="self-stretch px-6 py-3 justify-start items-center gap-[18px] inline-flex">
              <div className="w-[60px] text-center text-gray-800 text-base font-normal font-['Pretendard'] leading-normal">
                1
              </div>
              <div className="w-60 text-gray-800 text-base font-normal font-['Pretendard'] leading-normal">
                별이 빛나는 밤에
              </div>
              <div className="w-[60px] text-center text-gray-800 text-base font-normal font-['Pretendard'] leading-normal">
                8번
              </div>
              <div className="w-[120px] text-center text-gray-800 text-base font-normal font-['Pretendard'] leading-normal">
                김현우현우
              </div>
              <div className="w-[120px] text-center text-gray-800 text-base font-normal font-['Pretendard'] leading-normal">
                5월 1일
              </div>
              <div className="w-[120px] flex-col justify-start items-center inline-flex">
                <div className="px-3 py-1 rounded-[5px] border border-system-success justify-center items-center inline-flex">
                  <div className="text-center text-system-success text-sm font-normal font-['Pretendard'] leading-tight">
                    완료
                  </div>
                </div>
              </div>
            </div>
            <div className="self-stretch px-6 py-3 justify-start items-center gap-[18px] inline-flex">
              <div className="w-[60px] text-center text-gray-800 text-base font-normal font-['Pretendard'] leading-normal">
                10
              </div>
              <div className="w-60 text-gray-800 text-base font-normal font-['Pretendard'] leading-normal">
                별이 빛나는 밤에
              </div>
              <div className="w-[60px] text-center text-gray-800 text-base font-normal font-['Pretendard'] leading-normal">
                8번
              </div>
              <div className="w-[120px] text-center text-gray-800 text-base font-normal font-['Pretendard'] leading-normal">
                김현우현우
              </div>
              <div className="w-[120px] text-center text-gray-800 text-base font-normal font-['Pretendard'] leading-normal">
                5월 1일
              </div>
              <div className="w-[120px] flex-col justify-start items-center inline-flex">
                <div className="px-3 py-1 rounded-[5px] border border-system-success justify-center items-center inline-flex">
                  <div className="text-center text-system-success text-sm font-normal font-['Pretendard'] leading-tight">
                    완료
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div className="justify-start items-center gap-2 inline-flex">
          <div data-svg-wrapper className="relative">
            <svg
              width="20"
              height="20"
              viewBox="0 0 20 20"
              fill="none"
              xmlns="http://www.w3.org/2000/svg"
            >
              <path
                d="M12.5 15L7.5 10L12.5 5"
                stroke="#25282B"
                strokeWidth="2"
                strokeLinecap="round"
                strokeLinejoin="round"
              />
            </svg>
          </div>
          <div className="text-center">
            <span className="text-gray-800 text-base font-normal font-['Pretendard'] leading-normal">
              1
            </span>
            <span className="text-gray-500 text-base font-normal font-['Pretendard'] leading-normal">
              {' '}
            </span>
            <span className="text-gray-500 text-sm font-normal font-['Pretendard'] leading-tight">
              / 24
            </span>
          </div>
          <div data-svg-wrapper className="relative">
            <svg
              width="20"
              height="20"
              viewBox="0 0 20 20"
              fill="none"
              xmlns="http://www.w3.org/2000/svg"
            >
              <path
                d="M7.5 15L12.5 10L7.5 5"
                stroke="#25282B"
                strokeWidth="2"
                strokeLinecap="round"
                strokeLinejoin="round"
              />
            </svg>
          </div>
        </div>
      </div>
    </div>
  );
};

export default TeacherReportList;
