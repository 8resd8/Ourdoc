export const ClassNameHomeworkListSection = () => {
  return (
    <div className="w-[1064px] h-[289px] p-6 bg-white rounded-[15px] shadow-[0px_2px_12px_1px_rgba(33,33,33,0.09)] flex-col justify-start items-start gap-3 inline-flex">
      <div className="text-gray-800 headline-medium">숙제</div>
      <div className="self-stretch h-[203px] flex-col justify-start items-center gap-3 flex">
        <div className="self-stretch h-[203px] relative">
          <div className="w-[1016px] h-12 px-6 py-3 left-0 top-0 absolute border-b border-gray-900 justify-start items-center gap-[18px] inline-flex">
            <div className="w-[60px] text-center text-gray-800 body-medium">
              No
            </div>
            <div className="w-[332px] text-gray-800 body-medium">제목</div>
            <div className="w-[120px] text-center text-gray-800 body-medium">
              지은이
            </div>
            <div className="w-[120px] text-center text-gray-800 body-medium">
              출판사
            </div>
            <div className="w-[120px] text-center text-gray-800 body-medium">
              등록일
            </div>
            <div className="w-[120px] text-center text-gray-800 body-medium">
              숙제 제출
            </div>
          </div>
          <div className="w-[1016px] h-[156px] left-0 top-[47px] absolute flex-col justify-start items-start inline-flex">
            <div className="self-stretch px-6 py-3 justify-start items-center gap-[18px] inline-flex">
              <div className="w-[60px] text-center text-gray-800 body-medium">
                1
              </div>
              <div className="w-[332px] text-gray-800 body-medium truncate">
                "어린 왕자는 진정한 소중함은 눈에 보이지 않고 마음으로 보아야
                한다는 깨달음을 주는 책이다."
              </div>
              <div className="w-[120px] text-center text-gray-800 body-medium">
                5월 1일
              </div>
              <div className="w-[120px] text-center text-gray-800 body-medium">
                5월 1일
              </div>
              <div className="w-[120px] text-center text-gray-800 body-medium">
                5월 1일
              </div>
              <div className="w-[120px] flex-col justify-start items-center inline-flex">
                <div className="px-3 py-1 rounded-[5px] border border-system-success justify-center items-center inline-flex">
                  <div className="text-center text-system-success body-small">
                    제출 완료
                  </div>
                </div>
              </div>
            </div>
            <div className="self-stretch px-6 py-3 bg-primary-50 justify-start items-center gap-[18px] inline-flex">
              <div className="w-[60px] text-center text-gray-800 body-medium">
                2
              </div>
              <div className="w-[332px] text-gray-800 body-medium truncate">
                "어린 왕자는 진정한 소중함은 눈에 보이지 않고 마음으로 보아야
                한다는 깨달음을 주는 책이다."{' '}
              </div>
              <div className="w-[120px] text-center text-gray-800 body-medium">
                5월 1일
              </div>
              <div className="w-[120px] text-center text-gray-800 body-medium">
                5월 1일
              </div>
              <div className="w-[120px] text-center text-gray-800 body-medium">
                5월 1일
              </div>
              <div className="w-[120px] flex-col justify-start items-center inline-flex">
                <div className="px-3 py-1 rounded-[5px] border border-system-success justify-center items-center inline-flex">
                  <div className="text-center text-system-success body-small">
                    제출 완료
                  </div>
                </div>
              </div>
            </div>
            <div className="self-stretch px-6 py-3 justify-start items-center gap-[18px] inline-flex">
              <div className="w-[60px] text-center text-gray-800 body-medium">
                3
              </div>
              <div className="w-[332px] text-gray-800 body-medium truncate">
                "어린 왕자는 진정한 소중함은 눈에 보이지 않고 마음으로 보아야
                한다는 깨달음을 주는 책이다."{' '}
              </div>
              <div className="w-[120px] text-center text-gray-800 body-medium">
                5월 1일
              </div>
              <div className="w-[120px] text-center text-gray-800 body-medium">
                5월 1일
              </div>
              <div className="w-[120px] text-center text-gray-800 body-medium">
                5월 1일
              </div>
              <div className="w-[120px] flex-col justify-start items-center inline-flex">
                <div className="px-3 py-1 rounded-[5px] border border-system-danger justify-center items-center inline-flex">
                  <div className="text-center text-system-danger body-small">
                    미제출
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
