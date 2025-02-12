export const WriteReportButton = ({ onClick }: { onClick: Function }) => {
  return (
    <div
      className="w-[88px] h-[88px] bg-primary-500 rounded-[50px] shadow-[0px_3px_6px_0px_rgba(33,33,33,0.23)] border border-white flex-col justify-center items-center gap-1 inline-flex cursor-pointer"
      onClick={() => onClick()}
    >
      <div data-svg-wrapper className="relative">
        <img src="/assets/images/write.png" className="h-[36px] w-[36px]" />
      </div>
      <div className="text-white text-xs font-bold font-['Pretendard'] leading-none">
        독서록 작성
      </div>
    </div>
  );
};
