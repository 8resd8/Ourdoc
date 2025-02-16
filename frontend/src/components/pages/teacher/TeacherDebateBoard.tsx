import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import Button from '../../atoms/Button';
import { DebateBoardButton } from '../../atoms/DebateBoardButton';
import { PaginationButton } from '../../atoms/PagenationButton';
import { DebateRoom, getDebatesApi } from '../../../services/debatesService';
import Modal from '../../commons/Modal';

const PAGE_SIZE = 10;

interface RoomInformationProps {
  title?: string;
  password?: string;
}

const TeacherDebateBoard = () => {
  const navigate = useNavigate();
  const [showModal, setShowModal] = useState(false);
  const [currentPage, setCurrentPage] = useState(0);
  const [totalPages, setTotalPages] = useState(1);
  const [roomInformation, setRoomInformation] =
    useState<RoomInformationProps>();

  const onPageChange = (pageNumber: number) => {
    if (pageNumber >= 0 && pageNumber < totalPages) {
      fetchDebateRooms(pageNumber);
    }
  };

  const fetchDebateRooms = async (page = 0) => {
    try {
      const params = { size: PAGE_SIZE, page };
      // await getDebatesApi(params);

      // setStudents(response.content);
      // setTotalPages(response.totalPages); // totalPages를 API 응답에 맞게 수정
      setCurrentPage(page);
    } catch (error) {
      // setStudents([]);
    }
  };

  const createDebateRoom = () => {
    // try {
    //   const response = await api.post(
    //     'http://localhost:8080/api/openvidu/create',
    //     {
    //       sessionName: roomTitle,
    //       role: 'teacher',
    //     }
    //   );
    //   navigate(`http://localhost:8080/debate/${response.data.sessionId}`);
    // } catch (error) {
    //   alert('방을 생성할 수 없습니다.');
    // }
  };

  useEffect(() => {
    fetchDebateRooms();
  }, []);

  return (
    <div className={'flex w-[846px] flex-col mx-auto py-[56px] space-y-[40px]'}>
      <Modal
        isOpen={showModal}
        title={'토론방 개설'}
        body={
          <div>
            <div>
              <div className="text-start caption-medium mb-2">방 제목</div>
              <input
                type="text"
                placeholder="방 제목을 입력하세요."
                value={roomInformation?.title ?? ''}
                onChange={(e) => {
                  setRoomInformation({
                    ...roomInformation,
                    title: e.target.value,
                  });
                }}
                className="w-full px-3 py-2 border border-gray-300 rounded-md mb-4"
              />
            </div>
            <div>
              <div className="text-start caption-medium mb-2">비밀번호</div>
              <input
                type="password"
                placeholder="방 비밀번호을 입력하세요."
                value={roomInformation?.password ?? ''}
                onChange={(e) => {
                  setRoomInformation({
                    ...roomInformation,
                    password: e.target.value,
                  });
                }}
                className="w-full px-3 py-2 border border-gray-300 rounded-md mb-4"
              />
            </div>
          </div>
        }
        confirmText={'만들기'}
        cancelText={'취소'}
        onConfirm={() => {
          createDebateRoom();
          setShowModal(false);
          setRoomInformation(undefined);
        }}
        onCancel={() => {
          setShowModal(false);
          setRoomInformation(undefined);
        }}
      />
      <div className="flex justify-between items-center mb-10">
        <h1 className="headline-medium text-gray-800">독서토론 게시판</h1>
        <Button
          title={'방 만들기'}
          type={'outlined'}
          color={'primary'}
          onClick={() => {
            setShowModal(true);
          }}
          flexible
        />
      </div>
      <div className="flex flex-wrap justify-between gap-4">
        <DebateBoardButton
          title={'은혜갚은 까치 토론방'}
          school={'성룡'}
          teacher={'김보라'}
          currentCount={4}
          maxCount={17}
          date={'1시간 전'}
        />
        <DebateBoardButton
          title={'은혜갚은 까치 토론방'}
          school={'성룡'}
          teacher={'김보라'}
          currentCount={4}
          maxCount={17}
          date={'1시간 전'}
        />
        <DebateBoardButton
          title={'은혜갚은 까치 토론방'}
          school={'성룡'}
          teacher={'김보라'}
          currentCount={4}
          maxCount={17}
          date={'1시간 전'}
        />
      </div>
      <PaginationButton
        currentPage={currentPage}
        totalPages={totalPages}
        onPageChange={onPageChange}
      />
    </div>
  );
};

export default TeacherDebateBoard;
