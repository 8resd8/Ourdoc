import { removeFavoriteBookApi } from '../../services/booksService';
import { notify } from '../commons/Toast';

type ButtonProps = {
  id: number;
  updateData: () => void;
};

export const FavoriteBookButton = ({ id, updateData }: ButtonProps) => {
  const favoriteBook = async () => {
    await removeFavoriteBookApi(id);

    notify({
      type: 'success',
      text: '도서 관심이 해제되었습니다.',
    });

    updateData();
  };

  return (
    <div
      onClick={favoriteBook}
      className={`
        x-[185px] py-1 
        rounded-[10px] 
        text-center 
        justify-center 
        items-center 
        inline-flex 
        body-medium
        self-stretch 
        border 
        border-system-danger text-system-danger 
        cursor-pointer 
        hover:brightness-80
        `}
    >
      관심 해제하기
    </div>
  );
};
