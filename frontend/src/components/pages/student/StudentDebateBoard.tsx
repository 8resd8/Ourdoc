import { useEffect, useState } from 'react';
import { DebateBoardButton } from '../../atoms/DebateBoardButton';
import { PaginationButton } from '../../atoms/PagenationButton';
import { DebateRoom, getDebatesApi } from '../../../services/debatesService';

const PAGE_SIZE = 10;

const StudentDebateBoard = () => {
  const [currentPage, setCurrentPage] = useState(0);
  const [totalPages, setTotalPages] = useState(1);
  const [debateRooms, setDebateRooms] = useState<DebateRoom[]>([]);

  const onPageChange = (pageNumber: number) => {
    if (pageNumber >= 0 && pageNumber < totalPages) {
      fetchDebateRooms(pageNumber);
    }
  };

  const fetchDebateRooms = async (page = 0) => {
    try {
      const params = { size: PAGE_SIZE, page };
      const response = await getDebatesApi(params);

      setDebateRooms(response.content);
      setTotalPages(response.totalPages);
      setCurrentPage(page);
    } catch (error) {
      setDebateRooms([]);
    }
  };

  useEffect(() => {
    fetchDebateRooms();
  }, []);

  return (
    <div className={'flex w-[846px] flex-col mx-auto py-[56px] space-y-[40px]'}>
      <div className="flex justify-between items-center mb-10">
        <h1 className="headline-medium text-gray-800">독서토론 게시판</h1>
      </div>
      <div className="flex flex-wrap justify-between gap-4">
        {debateRooms.map((room, index) => {
          return <DebateBoardButton key={index} room={room} />;
        })}
      </div>
      <PaginationButton
        currentPage={currentPage}
        totalPages={totalPages}
        onPageChange={onPageChange}
      />
    </div>
  );
};

export default StudentDebateBoard;
