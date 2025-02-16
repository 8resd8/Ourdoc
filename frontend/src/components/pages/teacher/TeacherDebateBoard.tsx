import { useEffect, useState } from 'react';
import Button from '../../atoms/Button';
import { DebateBoardButton } from '../../atoms/DebateBoardButton';
import { PaginationButton } from '../../atoms/PagenationButton';
import {
  createDebateApi,
  DebateRoom,
  getDebatesApi,
} from '../../../services/debatesService';
import Modal from '../../commons/Modal';
import { useNavigate } from 'react-router-dom';

const PAGE_SIZE = 10;

interface RoomInformationProps {
  title: string;
  password: string;
}

const TeacherDebateBoard = () => {
  const navigate = useNavigate();

  const [showModal, setShowModal] = useState(false);
  const [currentPage, setCurrentPage] = useState(0);
  const [totalPages, setTotalPages] = useState(1);
  const [debateRooms, setDebateRooms] = useState<DebateRoom[]>([]);
  const [roomInformation, setRoomInformation] = useState<RoomInformationProps>({
    title: '',
    password: '',
  });

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

  const createDebateRoom = async () => {
    if (!roomInformation.title.trim()) {
      alert('방 제목을 입력해주세요.');
      return;
    }
    if (!roomInformation.password.trim()) {
      alert('비밀번호를 입력해주세요.');
      return;
    }
    try {
      // 백엔드에 방 생성 요청
      const response = await createDebateApi({
        roomName: roomInformation.title,
        password: roomInformation.password,
      });

      alert(`"${roomInformation.title}" 방이 생성되었습니다.`);

      navigate('/debate/room', { state: { token: response } });
    } catch (error) {
      console.error('방 생성 중 오류 발생:', error);
      alert('방 생성 중 오류가 발생했습니다.');
    }

    setShowModal(false);
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
          setRoomInformation({ title: '', password: '' });
        }}
        onCancel={() => {
          setShowModal(false);
          setRoomInformation({ title: '', password: '' });
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

export default TeacherDebateBoard;
