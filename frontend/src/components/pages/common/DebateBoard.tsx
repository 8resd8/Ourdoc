import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import Button from '../../atoms/Button';

const DebateBoard = () => {
  const navigate = useNavigate();
  const [showModal, setShowModal] = useState(false);

  const openModal = () => setShowModal(true);
  const closeModal = () => setShowModal(false);

  return (
    <div className="p-6 bg-gray-100 min-h-screen text-gray-0">
      <h1 className="text-2xl font-bold mb-4">토론 게시판</h1>
      <button
        onClick={openModal}
        className="bg-blue-500 hover:bg-blue-600 text-white px-4 py-2 rounded-lg shadow-md"
      >
        토론방 개설
      </button>

      {showModal && <CreateDebateRoom closeModal={closeModal} />}
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
      <div className="bg-white p-6 rounded-lg shadow-lg w-96">
        <h2 className="text-xl font-semibold mb-4">토론방 개설</h2>

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
            className="bg-green-500 hover:bg-green-600 text-white px-4 py-2 rounded-lg"
          >
            방 만들기
          </button>
          <button
            onClick={closeModal}
            className="bg-red-500 hover:bg-red-600 text-white px-4 py-2 rounded-lg"
          >
            취소
          </button>
        </div>
      </div>
    </div>
  );
};

export default DebateBoard;
