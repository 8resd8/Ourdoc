export const TeacherBookCategoryListTile = ({
  no,
  title,
  author,
  publisher,
  publishYear,
  readerCount,
  onClick,
  deleteClick,
  showCount = true,
  showDelete = true,
}: {
  no: number;
  title: string;
  author: string;
  publisher: string;
  publishYear: string;
  readerCount: number;
  onClick?: () => void;
  deleteClick?: () => void;
  showCount?: boolean;
  showDelete?: boolean;
}) => (
  <div
    onClick={onClick}
    className="self-stretch px-6 py-3 justify-start items-center gap-[18px] inline-flex hover:bg-primary-50 cursor-pointer"
  >
    <div className="w-[60px] text-center text-gray-800 body-medium">{no}</div>
    <div className="w-[240px] truncate text-gray-800 body-medium">{title}</div>
    <div className="w-[120px] text-center text-gray-800 body-medium truncate">
      {author}
    </div>
    <div className="w-[120px] text-center text-gray-800 body-medium truncate">
      {publisher}
    </div>
    <div className="w-[120px] text-center text-gray-800 body-medium truncate">
      {publishYear}
    </div>

    {showCount && (
      <div className="w-[120px] text-center text-gray-800 body-medium truncate">
        {readerCount}명
      </div>
    )}

    {showDelete && (
      <div className="w-[120px] items-center">
        <div>
          <div
            onClick={(event) => {
              event.stopPropagation(); // 클릭 이벤트 전파 막기
              deleteClick?.();
            }}
            className="body-small px-3 py-1 border border-system-danger rounded-[5px] text-system-danger text-center cursor-pointer"
          >
            삭제
          </div>
        </div>
      </div>
    )}
  </div>
);
