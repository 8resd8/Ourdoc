import { useNavigate } from 'react-router-dom';

type ButtonProps = {
  id: number;
  isSubmitted: boolean;
};

export const HomeWorkButton = ({ id, isSubmitted }: ButtonProps) => {
  const navigate = useNavigate();

  return (
    <div
      onClick={
        isSubmitted
          ? undefined
          : () => {
              navigate(`/student/homework/list/?homeworkId=${id}`);
            }
      }
      className={`
        x-[185px] py-1 
        rounded-[10px] 
        text-center 
        body-medium 
        border 
        justify-center 
        items-center 
        inline-flex 
        self-stretch
        ${isSubmitted ? '' : 'cursor-pointer hover:brightness-80'}
        ${isSubmitted ? 'border-gray-500 text-gray-500' : 'border-primary-500 text-primary-500'}
        `}
    >
      {isSubmitted ? '제출 완료' : '숙제하기'}
    </div>
  );
};
