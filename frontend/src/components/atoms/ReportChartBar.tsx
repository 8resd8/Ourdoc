type BarProps = {
  count: number;
  top: number;
  onClick: () => void;
};
const MAX_CHART_BAR_HEIGHT = 206.97;
const BAR_HEIGHT_PER_COUNT = 8.4;

export const ReportChartBar = ({ count, top, onClick }: BarProps) => {
  return (
    <div
      className={`w-[40.17px] flex justify-center cursor-pointer transition-colors ${'bg-primary-500 hover:bg-primary-400 group'}`}
      onClick={onClick}
    >
      <div
        className={`w-[22.17px] absolute bg-primary-500 group-hover:bg-primary-400`}
        style={{
          top: `${top}px`,
          height: `${Math.min(MAX_CHART_BAR_HEIGHT, BAR_HEIGHT_PER_COUNT * count)}px`,
        }}
      />
      {count != 0 && (
        <div
          className={`w-[22.17px] absolute text-center text-primary-500 body-small group-hover:text-primary-300`}
          style={{
            top: `${top - 18.9}px`,
          }}
        >
          {count}
        </div>
      )}
    </div>
  );
};
