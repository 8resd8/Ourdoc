export const ReportBookCardListTile = ({
  no,
  content,
  date,
  isApproved,
  HomeworkButton,
  onClick,
}: {
  no: number;
  content: string;
  date: string;
  isApproved: boolean;
  HomeworkButton?: React.ReactNode;
  onClick?: () => void;
}) => {
  return (
    <div
      onClick={onClick}
      className="self-stretch px-6 py-3 justify-start items-center gap-[18px] inline-flex hover:bg-primary-50 cursor-pointer"
    >
      <div className="w-[60px] text-center text-gray-800 body-medium">{no}</div>
      <div className="w-[360px] truncate text-gray-800 body-medium">
        {content}
      </div>
      <div className="w-[120px] text-center text-gray-800 body-medium truncate">
        {date}
      </div>

      <div className="w-[120px] items-center justify-center inline-flex">
        <div
          className={`px-3 py-1 rounded-[5px] text-center body-small border justify-center items-center inline-flex ${isApproved ? 'border-system-success text-system-success' : 'border-system-danger text-system-danger'}`}
        >
          {isApproved ? '있음' : '없음'}
        </div>
      </div>
      {HomeworkButton && (
        <div className="w-[120px] items-center justify-center inline-flex">
          {HomeworkButton}
        </div>
      )}
    </div>
  );
};
