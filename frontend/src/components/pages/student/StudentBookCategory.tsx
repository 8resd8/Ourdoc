import SelectVariants from '../../commons/SelectVariants';

const StudentBookCategory = () => {
  return (
    <div className="w-[1200px] m-auto mt-8">
      {/* 검색 영역 */}
      <div className="flex items-center justify-center mb-6">
        <div className="flex items-center space-x-4">
          <SelectVariants />
          <div
            className={`flex items-center  w-80  gap-2 h-[46px] overflow-hidden`}
          >
            <input
              className={` w-full h-full pl-5 outline-none placeholder-gray-500 text-sm`}
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

      {/* 숙제 도서 제목 */}
      <h2 className="headline-large text-center mb-[36px] mt-[72px]">
        숙제 도서
      </h2>

      {/* 카테고리 버튼 */}
      <div className="flex justify-center space-x-4 mb-6">
        <button className="flex flex-col items-center px-4 py-2 bg-gray-0 rounded-[15px] shadow-xxsmall">
          <img
            width={48}
            height={48}
            src="/assets/images/edit.png"
            className="mb-1"
            alt="숙제 아이콘"
          />
          <span>숙제</span>
        </button>
        <button className="flex flex-col items-center px-4 py-2 bg-gray-0 rounded-[15px] shadow-xxsmall">
          <img
            width={48}
            height={48}
            src="/assets/images/class.png"
            className="mb-1"
            alt="학급 아이콘"
          />
          <span>학급</span>
        </button>
        <button className="flex flex-col items-center px-4 py-2 bg-gray-0 rounded-[15px] shadow-xxsmall">
          <img
            width={48}
            height={48}
            src="/assets/images/heart.png"
            className="mb-1"
            alt="관심 아이콘"
          />
          <span>관심</span>
        </button>
        <button className="flex flex-col items-center px-4 py-2 bg-gray-0 rounded-[15px] shadow-xxsmall">
          <img
            width={48}
            height={48}
            src="/assets/images/school.png"
            className="mb-1"
            alt="학년 아이콘"
          />
          <span>학년</span>
        </button>
      </div>

      <div className="w-[1064px] h-[360px] justify-between items-start inline-flex">
        <div className="w-[185px] flex-col justify-start items-start gap-2 inline-flex">
          <div className="self-stretch h-80 rounded-[15px] flex-col justify-center items-center gap-2 flex">
            <div className="w-[185px] h-[232px] relative">
              <div className="w-[185px] h-[232px] left-0 top-0 absolute bg-white rounded-tr-[10px] rounded-br-[10px] shadow-[0px_2px_14px_2px_rgba(33,33,33,0.03)] border border-gray-200"></div>
              <img
                className="w-[181px] h-[232px] left-0 top-0 absolute rounded-tr-[10px] rounded-br-[10px] shadow-[0px_2px_14px_2px_rgba(33,33,33,0.03)] border border-gray-200"
                src="https://placehold.co/181x232"
              />
            </div>
            <div className="self-stretch h-6 justify-start items-center gap-5 inline-flex">
              <div className="grow shrink basis-0 self-stretch text-gray-800 headline-small">
                어린왕자
              </div>
            </div>
            <div className="self-stretch h-12 text-gray-300 body-small">
              생텍쥐페리생텍쥐페리생텍쥐페리 지음 | 새옴
            </div>
          </div>
          <div className="self-stretch py-[4px] bg-white rounded-[10px] border border-primary-500 justify-center items-center gap-2.5 inline-flex">
            <div className="text-primary-500 body-medium">숙제하기</div>
          </div>
        </div>
        <div className="w-[185px] flex-col justify-start items-start gap-2 inline-flex">
          <div className="self-stretch h-80 rounded-[15px] flex-col justify-center items-center gap-2 flex">
            <div className="w-[185px] h-[232px] relative">
              <div className="w-[185px] h-[232px] left-0 top-0 absolute bg-white rounded-tr-[10px] rounded-br-[10px] shadow-[0px_2px_14px_2px_rgba(33,33,33,0.03)] border border-gray-200"></div>
              <img
                className="w-[181px] h-[232px] left-0 top-0 absolute rounded-tr-[10px] rounded-br-[10px] shadow-[0px_2px_14px_2px_rgba(33,33,33,0.03)] border border-gray-200"
                src="https://placehold.co/181x232"
              />
            </div>
            <div className="self-stretch h-6 justify-start items-center gap-5 inline-flex">
              <div className="grow shrink basis-0 self-stretch text-gray-800 headline-small">
                어린왕자
              </div>
            </div>
            <div className="self-stretch h-12 text-gray-300 body-small">
              생텍쥐페리 지음 | 새옴
            </div>
          </div>
          <div className="self-stretch py-[4px] bg-white rounded-[10px] border border-gray-500 justify-center items-center gap-2.5 inline-flex">
            <div className="text-gray-500 body-medium">제출 완료</div>
          </div>
        </div>
        <div className="w-[185px] flex-col justify-start items-start gap-2 inline-flex">
          <div className="self-stretch h-80 rounded-[15px] flex-col justify-center items-center gap-2 flex">
            <div className="w-[185px] h-[232px] relative">
              <div className="w-[185px] h-[232px] left-0 top-0 absolute bg-white rounded-tr-[10px] rounded-br-[10px] shadow-[0px_2px_14px_2px_rgba(33,33,33,0.03)] border border-gray-200"></div>
              <img
                className="w-[181px] h-[232px] left-0 top-0 absolute rounded-tr-[10px] rounded-br-[10px] shadow-[0px_2px_14px_2px_rgba(33,33,33,0.03)] border border-gray-200"
                src="https://placehold.co/181x232"
              />
            </div>
            <div className="self-stretch h-6 justify-start items-center gap-5 inline-flex">
              <div className="grow shrink basis-0 self-stretch text-gray-800 headline-small">
                어린왕자
              </div>
            </div>
            <div className="self-stretch h-12 text-gray-300 body-small">
              생텍쥐페리 지음 | 새옴
            </div>
          </div>
          <div className="self-stretch py-[4px] bg-white rounded-[10px] border border-system-danger justify-center items-center gap-2.5 inline-flex">
            <div className="text-system-danger body-medium">관심 해제하기</div>
          </div>
        </div>
        <div className="w-[185px] flex-col justify-start items-start gap-2 inline-flex">
          <div className="self-stretch h-80 rounded-[15px] flex-col justify-center items-center gap-2 flex">
            <div className="w-[185px] h-[232px] relative">
              <div className="w-[185px] h-[232px] left-0 top-0 absolute bg-white rounded-tr-[10px] rounded-br-[10px] shadow-[0px_2px_14px_2px_rgba(33,33,33,0.03)] border border-gray-200"></div>
              <img
                className="w-[181px] h-[232px] left-0 top-0 absolute rounded-tr-[10px] rounded-br-[10px] shadow-[0px_2px_14px_2px_rgba(33,33,33,0.03)] border border-gray-200"
                src="https://placehold.co/181x232"
              />
            </div>
            <div className="self-stretch h-6 justify-start items-center gap-5 inline-flex">
              <div className="grow shrink basis-0 self-stretch text-gray-800 headline-small">
                어린왕자
              </div>
            </div>
            <div className="self-stretch h-12 text-gray-300 body-small">
              생텍쥐페리 지음 | 새옴
            </div>
          </div>
        </div>
        <div className="w-[185px] flex-col justify-start items-start gap-2 inline-flex">
          <div className="self-stretch h-80 rounded-[15px] flex-col justify-center items-center gap-2 flex">
            <div className="w-[185px] h-[232px] relative">
              <div className="w-[185px] h-[232px] left-0 top-0 absolute bg-white rounded-tr-[10px] rounded-br-[10px] shadow-[0px_2px_14px_2px_rgba(33,33,33,0.03)] border border-gray-200"></div>
              <img
                className="w-[181px] h-[232px] left-0 top-0 absolute rounded-tr-[10px] rounded-br-[10px] shadow-[0px_2px_14px_2px_rgba(33,33,33,0.03)] border border-gray-200"
                src="https://placehold.co/181x232"
              />
            </div>
            <div className="self-stretch h-6 justify-start items-center gap-5 inline-flex">
              <div className="grow shrink basis-0 self-stretch text-gray-800 headline-small">
                어린왕자
              </div>
            </div>
            <div className="self-stretch h-12 text-gray-300 body-small">
              생텍쥐페리 지음 | 새옴
            </div>
          </div>
        </div>
      </div>
      <div />

      <div className="w-[1064px] h-[360px] justify-between items-start inline-flex">
        <div className="w-[185px] flex-col justify-start items-start gap-2 inline-flex">
          <div className="self-stretch h-80 rounded-[15px] flex-col justify-center items-center gap-2 flex">
            <div className="w-[185px] h-[232px] relative">
              <div className="w-[185px] h-[232px] left-0 top-0 absolute bg-white rounded-tr-[10px] rounded-br-[10px] shadow-[0px_2px_14px_2px_rgba(33,33,33,0.03)] border border-gray-200"></div>
              <img
                className="w-[181px] h-[232px] left-0 top-0 absolute rounded-tr-[10px] rounded-br-[10px] shadow-[0px_2px_14px_2px_rgba(33,33,33,0.03)] border border-gray-200"
                src="https://placehold.co/181x232"
              />
            </div>
            <div className="self-stretch h-6 justify-start items-center gap-5 inline-flex">
              <div className="grow shrink basis-0 self-stretch text-gray-800 headline-small">
                어린왕자
              </div>
            </div>
            <div className="self-stretch h-12 text-gray-300 body-small">
              생텍쥐페리생텍쥐페리생텍쥐페리 지음 | 새옴
            </div>
          </div>
          <div className="self-stretch py-[4px] bg-white rounded-[10px] border border-primary-500 justify-center items-center gap-2.5 inline-flex">
            <div className="text-primary-500 body-medium">숙제하기</div>
          </div>
        </div>
        <div className="w-[185px] flex-col justify-start items-start gap-2 inline-flex">
          <div className="self-stretch h-80 rounded-[15px] flex-col justify-center items-center gap-2 flex">
            <div className="w-[185px] h-[232px] relative">
              <div className="w-[185px] h-[232px] left-0 top-0 absolute bg-white rounded-tr-[10px] rounded-br-[10px] shadow-[0px_2px_14px_2px_rgba(33,33,33,0.03)] border border-gray-200"></div>
              <img
                className="w-[181px] h-[232px] left-0 top-0 absolute rounded-tr-[10px] rounded-br-[10px] shadow-[0px_2px_14px_2px_rgba(33,33,33,0.03)] border border-gray-200"
                src="https://placehold.co/181x232"
              />
            </div>
            <div className="self-stretch h-6 justify-start items-center gap-5 inline-flex">
              <div className="grow shrink basis-0 self-stretch text-gray-800 headline-small">
                어린왕자
              </div>
            </div>
            <div className="self-stretch h-12 text-gray-300 body-small">
              생텍쥐페리 지음 | 새옴
            </div>
          </div>
          <div className="self-stretch py-[4px] bg-white rounded-[10px] border border-gray-500 justify-center items-center gap-2.5 inline-flex">
            <div className="text-gray-500 body-medium">제출 완료</div>
          </div>
        </div>
        <div className="w-[185px] flex-col justify-start items-start gap-2 inline-flex">
          <div className="self-stretch h-80 rounded-[15px] flex-col justify-center items-center gap-2 flex">
            <div className="w-[185px] h-[232px] relative">
              <div className="w-[185px] h-[232px] left-0 top-0 absolute bg-white rounded-tr-[10px] rounded-br-[10px] shadow-[0px_2px_14px_2px_rgba(33,33,33,0.03)] border border-gray-200"></div>
              <img
                className="w-[181px] h-[232px] left-0 top-0 absolute rounded-tr-[10px] rounded-br-[10px] shadow-[0px_2px_14px_2px_rgba(33,33,33,0.03)] border border-gray-200"
                src="https://placehold.co/181x232"
              />
            </div>
            <div className="self-stretch h-6 justify-start items-center gap-5 inline-flex">
              <div className="grow shrink basis-0 self-stretch text-gray-800 headline-small">
                어린왕자
              </div>
            </div>
            <div className="self-stretch h-12 text-gray-300 body-small">
              생텍쥐페리 지음 | 새옴
            </div>
          </div>
          <div className="self-stretch py-[4px] bg-white rounded-[10px] border border-system-danger justify-center items-center gap-2.5 inline-flex">
            <div className="text-system-danger body-medium">관심 해제하기</div>
          </div>
        </div>
        <div className="w-[185px] flex-col justify-start items-start gap-2 inline-flex">
          <div className="self-stretch h-80 rounded-[15px] flex-col justify-center items-center gap-2 flex">
            <div className="w-[185px] h-[232px] relative">
              <div className="w-[185px] h-[232px] left-0 top-0 absolute bg-white rounded-tr-[10px] rounded-br-[10px] shadow-[0px_2px_14px_2px_rgba(33,33,33,0.03)] border border-gray-200"></div>
              <img
                className="w-[181px] h-[232px] left-0 top-0 absolute rounded-tr-[10px] rounded-br-[10px] shadow-[0px_2px_14px_2px_rgba(33,33,33,0.03)] border border-gray-200"
                src="https://placehold.co/181x232"
              />
            </div>
            <div className="self-stretch h-6 justify-start items-center gap-5 inline-flex">
              <div className="grow shrink basis-0 self-stretch text-gray-800 headline-small">
                어린왕자
              </div>
            </div>
            <div className="self-stretch h-12 text-gray-300 body-small">
              생텍쥐페리 지음 | 새옴
            </div>
          </div>
        </div>
        <div className="w-[185px] flex-col justify-start items-start gap-2 inline-flex">
          <div className="self-stretch h-80 rounded-[15px] flex-col justify-center items-center gap-2 flex">
            <div className="w-[185px] h-[232px] relative">
              <div className="w-[185px] h-[232px] left-0 top-0 absolute bg-white rounded-tr-[10px] rounded-br-[10px] shadow-[0px_2px_14px_2px_rgba(33,33,33,0.03)] border border-gray-200"></div>
              <img
                className="w-[181px] h-[232px] left-0 top-0 absolute rounded-tr-[10px] rounded-br-[10px] shadow-[0px_2px_14px_2px_rgba(33,33,33,0.03)] border border-gray-200"
                src="https://placehold.co/181x232"
              />
            </div>
            <div className="self-stretch h-6 justify-start items-center gap-5 inline-flex">
              <div className="grow shrink basis-0 self-stretch text-gray-800 headline-small">
                어린왕자
              </div>
            </div>
            <div className="self-stretch h-12 text-gray-300 body-small">
              생텍쥐페리 지음 | 새옴
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default StudentBookCategory;
