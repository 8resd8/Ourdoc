const TeacherProfileUpdate = () => {
  return (
    <div>
      <div className="m-auto">
        <img
          className="w-[196px] h-[196px] left-[622px] top-[158px] absolute rounded-full border border-[#e0e0e0]"
          src="/assets/images/tmpProfile.png"
        />
        <div className="left-[680px] top-[366px] absolute text-center text-[#2c2c2c] text-[22px] font-semibold font-['Pretendard'] leading-relaxed">
          나미소 님
        </div>
        <div className="h-[596px] left-[513px] top-[428px] absolute flex-col justify-start items-start gap-[72px] inline-flex">
          <div className="self-stretch h-[468px] flex-col justify-start items-start gap-8 flex">
            <div className="w-[414px] h-[68px] relative">
              <div className="left-[16.29px] top-[-0px] absolute text-[#8c8c8c] text-sm font-normal font-['Pretendard'] leading-tight">
                아이디
              </div>
              <div className="w-[414px] h-10 px-3 py-2 left-0 top-[28px] absolute bg-white border-b border-[#8c8c8c] justify-start items-center gap-2.5 inline-flex">
                <div className="text-[#8c8c8c] text-base font-normal font-['Pretendard'] leading-normal">
                  usertest
                </div>
              </div>
            </div>
            <div className="w-[414px] h-[68px] relative">
              <div className="left-[12px] top-0 absolute text-[#2c2c2c] text-sm font-normal font-['Pretendard'] leading-tight">
                이메일
              </div>
              <div className="w-[414px] h-10 px-3 py-2 left-0 top-[28px] absolute bg-white border-b border-[#e0e0e0] justify-start items-center gap-2.5 inline-flex">
                <div className="text-[#cccccc] text-base font-normal font-['Pretendard'] leading-normal">
                  이메일을 입력해주세요.
                </div>
              </div>
            </div>
            <div className="w-[414px] h-[68px] relative">
              <div className="left-[12px] top-0 absolute text-[#2c2c2c] text-sm font-normal font-['Pretendard'] leading-tight">
                전화번호
              </div>
              <div className="w-[414px] h-10 px-3 py-2 left-0 top-[28px] absolute bg-white border-b border-[#e0e0e0] justify-start items-center gap-2.5 inline-flex">
                <div className="text-[#cccccc] text-base font-normal font-['Pretendard'] leading-normal">
                  전화번호를 입력해주세요.
                </div>
              </div>
            </div>
            <div className="w-[414px] h-[68px] relative">
              <div className="left-[12px] top-0 absolute text-[#2c2c2c] text-sm font-normal font-['Pretendard'] leading-tight">
                소속 학교
              </div>
              <div className="w-[414px] h-10 px-3 py-2 left-0 top-[28px] absolute bg-white border-b border-[#e0e0e0] justify-start items-center gap-2.5 inline-flex">
                <div className="text-[#cccccc] text-base font-normal font-['Pretendard'] leading-normal">
                  소속 학교를 입력해주세요.
                </div>
              </div>
            </div>
            <div className="self-stretch justify-start items-start gap-8 inline-flex">
              <div className="w-[116.67px] h-[68px] relative">
                <div className="left-[3.38px] px-2 top-0 absolute text-[#2c2c2c] text-sm font-normal font-['Pretendard'] leading-tight">
                  년도
                </div>
                <div className="w-[116.67px] h-10 px-3 py-2 left-0 top-[28px] absolute bg-white border-b border-[#e0e0e0] justify-start items-center gap-2.5 inline-flex">
                  <div className="w-[92.67px] text-[#cccccc] text-base font-normal font-['Pretendard'] leading-normal">
                    년도
                  </div>
                </div>
              </div>
              <div className="w-[116.67px] h-[68px] relative">
                <div className="left-[3.38px] px-2 top-0 absolute text-[#2c2c2c] text-sm font-normal font-['Pretendard'] leading-tight">
                  학년
                </div>
                <div className="w-[116.67px] h-10 px-3 py-2 left-0 top-[28px] absolute bg-white border-b border-[#e0e0e0] justify-start items-center gap-2.5 inline-flex">
                  <div className="w-[92.67px] text-[#cccccc] text-base font-normal font-['Pretendard'] leading-normal">
                    학년
                  </div>
                </div>
              </div>
              <div className="w-[116.67px] h-[68px] relative">
                <div className="left-[10.77px] top-0 absolute text-[#2c2c2c] text-sm font-normal font-['Pretendard'] leading-tight">
                  반
                </div>
                <div className="w-[116.67px] h-10 px-3 py-2 left-0 top-[28px] absolute bg-white border-b border-[#e0e0e0] justify-start items-center gap-2.5 inline-flex">
                  <div className="w-[92.67px] text-[#cccccc] text-base font-normal font-['Pretendard'] leading-normal">
                    반
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div className="w-[414px] px-[140px] py-4 bg-[#ff6f61] rounded-[10px] justify-center items-center gap-2.5 inline-flex">
            <div className="text-center text-white text-base font-normal font-['Pretendard'] leading-normal">
              저장하기
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default TeacherProfileUpdate;
