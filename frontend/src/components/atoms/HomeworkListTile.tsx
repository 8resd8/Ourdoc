export const HomeworkListTile = ({
  no,
  title,
  date,
  status,
  onClick,
}: {
  no: number;
  title: string;
  date: string;
  status: string;
  onClick?: () => void;
}) => (
  <div className="self-stretch px-6 py-3 justify-start items-center gap-[18px] inline-flex hover:bg-primary-50 cursor-pointer">
    <div className="w-[60px] text-center text-gray-800 body-medium">{no}</div>
    <div className="w-[332px] truncate text-gray-800 body-medium">{title}</div>
    <div className="w-[120px] text-center text-gray-800 body-medium">
      {date}
    </div>
    <div className="w-[120px] text-center text-gray-800 body-medium">
      {date}
    </div>
    <div className="w-[120px] text-center text-gray-800 body-medium">
      {date}
    </div>
    <div className="w-[120px] text-center">
      <span className="text-gray-800 headline-small">{status} </span>
      <span className="text-gray-700 body-medium">/22명</span>
    </div>
  </div>
);
