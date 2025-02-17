export const TeacherBookCategoryListTile = ({
  no,
  title,
  author,
  publisher,
  publishYear,
  readerCount,
  onClick,
  deleteClick,
}: {
  no: number;
  title: string;
  author: string;
  publisher: string;
  publishYear: number;
  readerCount: number;
  onClick?: () => void;
  deleteClick?: () => void;
}) => (
  <div className="self-stretch px-6 py-3 justify-start items-center gap-[18px] inline-flex hover:bg-primary-50 cursor-pointer">
    <div className="w-[60px] text-center text-gray-800 body-medium">{no}</div>
    <div className="w-[240px] truncate text-gray-800 body-medium">{title}</div>
    <div className="w-[120px] text-center text-gray-800 body-medium">
      {author}
    </div>
    <div className="w-[120px] text-center text-gray-800 body-medium">
      {publisher}
    </div>
    <div className="w-[120px] text-center text-gray-800 body-medium">
      {publishYear}
    </div>

    <div className="w-[120px] text-center text-gray-800 body-medium">
      {readerCount}명
    </div>

    <div className="w-[120px] items-center">
      <div>
        <div
          onClick={deleteClick}
          className="body-small px-3 py-1 border border-system-danger rounded-[5px] text-system-danger text-center cursor-pointer"
        >
          삭제
        </div>
      </div>
    </div>
  </div>
);
