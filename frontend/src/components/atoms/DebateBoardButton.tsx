import { useNavigate } from 'react-router-dom';
import { DebateRoom, enterDebateApi } from '../../services/debatesService';
import { useEffect, useState } from 'react';
import Modal from '../commons/Modal';
import { detailDate } from '../../utils/DateFormat';

interface RoomInformationProps {
  roomId: string;
  password: string;
}
export const DebateBoardButton = ({ room }: { room: DebateRoom }) => {
  const navigate = useNavigate();
  const [showModal, setShowModal] = useState(false);
  const [roomInformation, setRoomInformation] = useState<RoomInformationProps>({
    roomId: '',
    password: '',
  });

  useEffect(() => {
    setRoomInformation({
      roomId: room.roomId,
      password: '',
    });
  }, []);

  const enterRoom = async () => {
    setShowModal(true);
    if (!roomInformation.password.trim()) {
      alert('비밀번호를 입력해주세요.');
      return;
    }

    try {
      const response = await enterDebateApi(roomInformation.roomId, {
        password: roomInformation.password,
      });

      navigate('/debate/room', { state: { token: response } });
    } catch (error) {
      console.error('error:', error);
      alert('error');
    }

    setShowModal(false);
  };

  return (
    <>
      <Modal
        isOpen={showModal}
        title={'토론방 입장'}
        body={
          <div>
            <div>
              <div className="text-start caption-medium mb-2">비밀번호</div>
              <input
                type="password"
                placeholder="비밀번호 입력하세요."
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
          enterRoom();
          setRoomInformation({ ...roomInformation, password: '' });
        }}
        onCancel={() => {
          setShowModal(false);
          setRoomInformation({ ...roomInformation, password: '' });
        }}
      />
      <div
        onClick={() => setShowModal(true)}
        className="w-[413px] h-[100px] p-6 bg-gray-0 rounded-[15px] border border-gray-200 items-center justify-between flex flex-row hover:bg-primary-50 hover:border-primary-500 cursor-pointer"
      >
        <div className="flex flex-col justify-between self-stretch">
          <div className="text-gray-800 body-medium-bold w-[250px] truncate">
            {room.title}
          </div>
          <div className="text-gray-800 caption-medium truncate">
            {room.schoolName} 초등학교 | {room.creatorName} 선생님
          </div>
        </div>
        <div className="flex flex-col w-[60px] justify-between items-end self-stretch">
          <div className="text-gray-700 body-medium">
            {room.currentPeople} /{room.maxPeople} 명
          </div>
          <div className="text-gray-700 caption-medium">
            {detailDate(room.createdAt)}
          </div>
        </div>
      </div>
    </>
  );
};
