import { useNavigate } from 'react-router-dom';

export const StudentHomeworkListTile = ({
  no,
  title,
  date,
  status,
  publisher,
  author,
  homeworkId,
  onClick,
}: {
  homeworkId: number;
  no: number;
  title: string;
  date: string;
  publisher: string;
  author: string;
  status: boolean;
  onClick?: () => void;
}) => {
  const navigate = useNavigate();

  return (
    <div
      onClick={() => {
        navigate(`/student/homework/list/?homeworkId=${homeworkId}`);
      }}
    >
      <div className="self-stretch px-6 py-3 justify-start items-center gap-[18px] inline-flex hover:bg-primary-50 cursor-pointer">
        <div className="w-[60px] text-center text-gray-800 body-medium">
          {no}
        </div>
        <div className="w-[332px] truncate text-gray-800 body-medium">
          {title}
        </div>
        <div className="w-[120px] text-center text-gray-800 body-medium whitespace-nowrap overflow-hidden text-ellipsis">
          {author}
        </div>
        <div className="w-[120px] text-center text-gray-800 body-medium">
          {publisher}
        </div>
        <div className="w-[120px] text-center text-gray-800 body-medium">
          {date}
        </div>
        <div className="w-[120px] items-center justify-center inline-flex">
          <div
            className={`px-3 py-1 rounded-[5px] text-center body-small border justify-center items-center inline-flex ${status ? 'border-system-success text-system-success' : 'border-system-danger text-system-danger'}`}
          >
            {status ? '제출 완료' : '미제출'}
          </div>
        </div>
      </div>
    </div>
  );
};
