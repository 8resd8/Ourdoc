export const DebateButton = ({ onClick }: { onClick: Function }) => {
  return (
    <div className="w-[88px] h-[88px] cursor-pointer bg-gray-0 rounded-[50px] shadow-small border border-secondary-500 flex-col justify-center items-center gap-1 inline-flex">
      <div data-svg-wrapper className="relative">
        <img src="/assets/images/book.png" className="h-[36px] w-[36px]" />
      </div>
      <div className="text-secondary-500 body-small font-bold">독서토론방</div>
    </div>
  );
};
