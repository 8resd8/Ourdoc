interface PaginationProps {
  currentPage: number;
  totalPages: number;
  onPageChange: (page: number) => void;
}

export const PaginationButton = (props: PaginationProps) => {
  return (
    <div className="flex justify-center items-center mt-8 space-x-2">
      {/* 이전 페이지 버튼 */}
      <button
        className={`px-4 py-2 rounded-lg cursor-pointer ${
          props.currentPage === 0
            ? 'text-gray-400 cursor-not-allowed'
            : 'hover:bg-gray-100'
        }`}
        onClick={() => props.onPageChange(props.currentPage - 1)}
        disabled={props.currentPage === 0}
      >
        &lt;
      </button>

      {/* 페이지 번호 표시 */}
      {Array.from({ length: props.totalPages }, (_, index) => (
        <button
          key={index}
          className={`px-4 py-2 body-small rounded-lg cursor-pointer ${
            props.currentPage === index
              ? 'bg-primary-500 text-white'
              : 'hover:bg-gray-100 text-gray-500'
          }`}
          onClick={() => props.onPageChange(index)}
        >
          {index + 1}
        </button>
      ))}

      {/* 다음 페이지 버튼 */}
      <button
        className={`px-4 py-2 rounded-lg cursor-pointer ${
          props.currentPage === props.totalPages - 1
            ? 'text-gray-400 cursor-not-allowed'
            : 'hover:bg-gray-100'
        }`}
        onClick={() => props.onPageChange(props.currentPage + 1)}
        disabled={props.currentPage === props.totalPages - 1}
      >
        &gt;
      </button>
    </div>
  );
};
