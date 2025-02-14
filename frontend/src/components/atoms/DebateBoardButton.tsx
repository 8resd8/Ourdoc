interface DebateBoardButtonProps {
  title: string;
  school: string;
  teacher: string;
  currentCount: number;
  maxCount: number;
  date: string;
  onClick: () => void;
}
export const DebateBoardButton = (props: DebateBoardButtonProps) => {
  return (
    <div className="w-[413px] h-[100px] p-6 bg-gray-0 rounded-[15px] border border-gray-200 items-center justify-between flex flex-row hover:bg-primary-50 hover:border-primary-500 cursor-pointer">
      <div className="flex flex-col justify-between self-stretch">
        <div className="text-gray-800 body-medium-bold w-[250px] truncate">
          {props.title}
        </div>
        <div className="text-gray-800 caption-medium truncate">
          {props.school} 초등학교 | {props.teacher} 선생님
        </div>
      </div>
      <div className="flex flex-col w-[60px] justify-between items-end self-stretch">
        <div className="text-gray-700 body-medium">
          {props.currentCount} /{props.maxCount} 명
        </div>
        <div className="text-gray-700 caption-medium">{props.date}</div>
      </div>
    </div>
  );
};
