interface MostReadBookProps {
  title: string;
}

export const MostReadBookSection = ({ title }: MostReadBookProps) => {
  return (
    <div className="w-[522px] h-[430px] p-6 bg-gray-0 rounded-[15px] shadow-xsmall flex-col justify-start items-start gap-3 inline-flex">
      <div className="text-gray-800 headline-medium">{title}</div>
      <div className="self-stretch px-6 py-3 justify-between items-center inline-flex">
        <div className="w-[185px] flex-col justify-start items-start gap-2 inline-flex">
          <div className="self-stretch h-80 rounded-[15px] flex-col justify-center items-center gap-2 flex">
            <div className="w-[185px] h-[232px] relative">
              <div className="w-[185px] h-[232px] left-0 top-0 absolute bg-gray-0 rounded-tr-[10px] rounded-br-[10px] shadow-xxsmall border border-gray-200"></div>
              <img
                className="w-[181px] h-[232px] left-0 top-0 absolute rounded-tr-[10px] rounded-br-[10px] shadow-xxsmall border border-gray-200"
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
        </div>
        <div className="w-[200px] h-[200px] px-[69px] py-[76px] rounded-[120px] border-2 border-primary-500 flex-col justify-center items-center gap-2.5 inline-flex">
          <div>
            <span className="text-primary-500 display-medium">33</span>
            <span className="text-primary-500 body-medium">권</span>
          </div>
        </div>
      </div>
    </div>
  );
};
