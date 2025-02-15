type Props = {
  month: number;
  isActive?: boolean;
  onClick?: () => void;
};

export const ReportChartMonthTile = ({ month, isActive, onClick }: Props) => {
  return (
    <div
      className={`w-[40.17px] body-medium cursor-pointer transition-colors ${
        isActive ? 'text-primary-500' : 'text-gray-800'
      } hover:text-primary-300 active:text-primary-500 text-center`}
      onClick={onClick}
    >
      {month}월
    </div>
  );
};
