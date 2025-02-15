type ButtonProps = {
  id: number;
};

export const FavoriteBookButton = ({ id }: ButtonProps) => {
  return (
    <div
      onClick={() => {}}
      className={`
        x-[185px] py-1 
        rounded-[10px] 
        text-center 
        justify-center 
        items-center 
        inline-flex 
        body-medium 
        border 
        border-system-danger text-system-danger 
        `}
    >
      관심 해제하기
    </div>
  );
};
