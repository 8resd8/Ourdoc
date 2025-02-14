import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import Button from '../../atoms/Button';
import { DebateBoardButton } from '../../atoms/DebateBoardButton';
import { PaginationButton } from '../../atoms/PagenationButton';

const TeacherDebateBoard = () => {
  const navigate = useNavigate();
  const [showModal, setShowModal] = useState(false);

  const openModal = () => setShowModal(true);
  const closeModal = () => setShowModal(false);

  return (
    <div className={'flex w-[846px] flex-col mx-auto py-[56px] space-y-[40px]'}>
      <div className="flex justify-between items-center mb-10">
        <h1 className="headline-medium text-gray-800">독서토론 게시판</h1>
        <Button
          title={'방 만들기'}
          type={'outlined'}
          color={'primary'}
          onClick={() => {}}
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
          onClick={() => {}}
        />
        <DebateBoardButton
          title={'은혜갚은 까치 토론방'}
          school={'성룡'}
          teacher={'김보라'}
          currentCount={4}
          maxCount={17}
          date={'1시간 전'}
          onClick={() => {}}
        />
        <DebateBoardButton
          title={'은혜갚은 까치 토론방'}
          school={'성룡'}
          teacher={'김보라'}
          currentCount={4}
          maxCount={17}
          date={'1시간 전'}
          onClick={() => {}}
        />
      </div>
      <PaginationButton
        currentPage={1}
        totalPages={3}
        onPageChange={function (page: number): void {
          throw new Error('Function not implemented.');
        }}
      />
    </div>
  );
};

const CreateDebateRoom = ({ closeModal }: { closeModal: () => void }) => {
  const [roomTitle, setRoomTitle] = useState('');
  const navigate = useNavigate();

  const createRoom = async () => {
    if (!roomTitle) {
      alert('방 제목을 입력해주세요.');
      return;
    }

    try {
      const response = await axios.post(
        'http://localhost:8080/api/openvidu/create',
        {
          sessionName: roomTitle,
          role: 'teacher',
        }
      );

      navigate(`http://localhost:8080/debate/${response.data.sessionId}`);
    } catch (error) {
      alert('방을 생성할 수 없습니다.');
    }
  };

  return (
    <div className="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50">
      <div className="bg-gray-0 p-6 rounded-lg shadow-small w-96">
        <h2 className="headline-medium font-semibold mb-4">토론방 개설</h2>

        <input
          type="text"
          placeholder="방 제목 입력"
          value={roomTitle}
          onChange={(e) => setRoomTitle(e.target.value)}
          className="w-full px-3 py-2 border border-gray-300 rounded-md mb-4"
        />
        <div className="flex justify-end gap-2">
          <button
            onClick={createRoom}
            className="bg-green-500 hover:bg-green-600 text-gray-0 px-4 py-2 rounded-lg"
          >
            방 만들기
          </button>
          <button
            onClick={closeModal}
            className="bg-red-500 hover:bg-red-600 text-gray-0 px-4 py-2 rounded-lg"
          >
            취소
          </button>
        </div>
      </div>
    </div>
  );
};

export default TeacherDebateBoard;
