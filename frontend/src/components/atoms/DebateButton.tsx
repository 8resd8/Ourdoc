export const DebateButton = ({ onClick }: { onClick: Function }) => {
  return (
    <div className="w-[88px] h-[88px] cursor-pointer bg-white rounded-[50px] shadow-[0px_3px_6px_0px_rgba(33,33,33,0.23)] border border-[#127a5c] flex-col justify-center items-center gap-1 inline-flex">
      <div data-svg-wrapper className="relative">
        <svg
          width="36"
          height="36"
          viewBox="0 0 36 36"
          fill="none"
          xmlns="http://www.w3.org/2000/svg"
        >
          <path
            d="M6 29.25C6 28.2554 6.39509 27.3016 7.09835 26.5983C7.80161 25.8951 8.75544 25.5 9.75 25.5H30"
            stroke="#127A5C"
            strokeWidth="2"
            strokeLinecap="round"
            strokeLinejoin="round"
          />
          <path
            d="M9.75 3H30V33H9.75C8.75544 33 7.80161 32.6049 7.09835 31.9017C6.39509 31.1984 6 30.2446 6 29.25V6.75C6 5.75544 6.39509 4.80161 7.09835 4.09835C7.80161 3.39509 8.75544 3 9.75 3V3Z"
            stroke="#127A5C"
            strokeWidth="2"
            strokeLinecap="round"
            strokeLinejoin="round"
          />
        </svg>
      </div>
      <div className="text-[#127a5c] text-xs font-bold font-['Pretendard'] leading-none">
        독서토론방
      </div>
    </div>
  );
};
