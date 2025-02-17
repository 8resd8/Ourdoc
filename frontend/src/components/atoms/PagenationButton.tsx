interface PaginationProps {
  currentPage: number;
  totalPages: number;
  onPageChange: (page: number) => void;
}

export const PaginationButton = (props: PaginationProps) => {
  const { currentPage, totalPages, onPageChange } = props;
  const pageRange = 5; // 한 번에 표시할 페이지 번호의 개수

  // 현재 페이지를 중심으로 표시할 페이지 범위 계산
  const startPage = Math.max(0, currentPage - Math.floor(pageRange / 2));
  const endPage = Math.min(totalPages - 1, startPage + pageRange - 1);

  return (
    <div className="flex justify-center items-center mt-8 py-4 space-x-2">
      {/* 첫 페이지로 이동 버튼 */}
      <button
        className={`px-4 py-2 rounded-lg cursor-pointer ${
          currentPage === 0
            ? 'text-gray-400 cursor-not-allowed'
            : 'hover:bg-gray-100'
        }`}
        onClick={() => onPageChange(0)}
        disabled={currentPage === 0}
      >
        &lt;&lt;
      </button>

      {/* 이전 페이지 버튼 */}
      <button
        className={`px-4 py-2 rounded-lg cursor-pointer ${
          currentPage === 0
            ? 'text-gray-400 cursor-not-allowed'
            : 'hover:bg-gray-100'
        }`}
        onClick={() => onPageChange(currentPage - 1)}
        disabled={currentPage === 0}
      >
        &lt;
      </button>

      {/* 페이지 번호 표시 */}
      {Array.from({ length: endPage - startPage + 1 }, (_, index) => (
        <button
          key={startPage + index}
          className={`px-4 py-2 body-small rounded-lg cursor-pointer ${
            currentPage === startPage + index
              ? 'bg-primary-500 text-white'
              : 'hover:bg-gray-100 text-gray-500'
          }`}
          onClick={() => onPageChange(startPage + index)}
        >
          {startPage + index + 1}
        </button>
      ))}

      {/* 다음 페이지 버튼 */}
      <button
        className={`px-4 py-2 rounded-lg cursor-pointer ${
          currentPage === totalPages - 1
            ? 'text-gray-400 cursor-not-allowed'
            : 'hover:bg-gray-100'
        }`}
        onClick={() => onPageChange(currentPage + 1)}
        disabled={currentPage === totalPages - 1}
      >
        &gt;
      </button>

      {/* 마지막 페이지로 이동 버튼 */}
      <button
        className={`px-4 py-2 rounded-lg cursor-pointer ${
          currentPage === totalPages - 1
            ? 'text-gray-400 cursor-not-allowed'
            : 'hover:bg-gray-100'
        }`}
        onClick={() => onPageChange(totalPages - 1)}
        disabled={currentPage === totalPages - 1}
      >
        &gt;&gt;
      </button>
    </div>
  );
};
