export const TrophyStampSection = ({ count }: { count: number }) => {
  const lineCount = Math.ceil(count / 10);

  const stampLines: boolean[][] = Array.from(
    { length: lineCount },
    (_, lineIndex) => {
      return Array.from({ length: 10 }, (_, i) => {
        const currentIndex = lineIndex * 10 + i;
        return currentIndex < count;
      });
    }
  );

  return (
    <div className="p-6 bg-gray-0 rounded-[15px] shadow-xsmall flex-col justify-start items-start gap-3 inline-flex">
      <div>
        <span className="text-gray-800 headline-medium">칭찬도장 </span>
        <span className="text-gray-700 body-medium">{count}개</span>
      </div>

      {stampLines.map((stamps, lineIndex) => (
        <div
          key={lineIndex}
          className="self-stretch justify-start items-center gap-6 inline-flex"
        >
          {stamps.map((isStamp, index) => {
            const globalIndex = lineIndex * 10 + index + 1;
            return (
              <Stamp key={globalIndex} index={globalIndex} isStamp={isStamp} />
            );
          })}
        </div>
      ))}
    </div>
  );
};

const Stamp = ({ index, isStamp }: { index: number; isStamp: boolean }) => {
  return (
    <>
      {isStamp ? (
        <img className="w-[72px] h-[72px]" src="/assets/images/goodStamp.png" />
      ) : (
        <div className="w-[72px] h-[72px] px-6 py-2.5 rounded-[40px] border border-gray-500 flex-col justify-center items-center gap-2.5 inline-flex">
          <div className="text-gray-500 headline-large">{index}</div>
        </div>
      )}
    </>
  );
};
