export const TeacherAllReportListTile = ({
  no,
  title,
  studentNumber,
  name,
  submitDate,
  isApproved,
  onClick,
}: {
  no: number;
  title: string;
  studentNumber: number;
  name: string;
  submitDate: string;
  isApproved: string;
  onClick?: () => void;
}) => (
  <div
    onClick={onClick}
    className="self-stretch px-6 py-3 justify-start items-center gap-[18px] inline-flex hover:bg-primary-50 cursor-pointer"
  >
    <div className="w-[60px] text-center text-gray-800 body-medium">{no}</div>
    <div className="w-[240px] truncate text-gray-800 body-medium">{title}</div>
    <div className="w-[60px] text-center text-gray-800 body-medium truncate">
      {studentNumber}
    </div>
    <div className="w-[120px] text-center text-gray-800 body-medium truncate">
      {name}
    </div>
    <div className="w-[120px] text-center text-gray-800 body-medium truncate">
      {submitDate}
    </div>
    <div className="w-[120px] items-center">
      <div>
        <div
          className={`body-small px-3 py-1 border  rounded-[5px] ${isApproved == '있음' ? 'border-system-success text-system-success' : 'border-system-danger text-system-danger'} text-center cursor-pointer`}
        >
          {isApproved == '있음' ? '완료' : '미완료'}
        </div>
      </div>
    </div>
  </div>
);
