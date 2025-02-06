import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';
import { OpenVidu } from 'openvidu-browser';

const DebateRoom = () => {
  const { sessionName } = useParams(); // URL에서 sessionName 가져오기
  const [session, setSession] = useState<any>(null);
  const [userCount, setUserCount] = useState(0);
  const [nickname, setNickname] = useState(''); // 사용자가 지정한 닉네임

  useEffect(() => {
    fetchUserCount();
  }, []);

  // 현재 참가자 수 가져오기
  const fetchUserCount = async () => {
    try {
      const response = await axios.get(
        `http://localhost:8080/api/openvidu/user-count/${sessionName}`
      );
      setUserCount(response.data.userCount);
    } catch (error) {
      console.error('참가자 수를 가져오는 중 오류 발생', error);
    }
  };

  // 세션 참가
  const joinSession = async () => {
    if (!nickname.trim()) {
      alert('닉네임을 입력해주세요!');
      return;
    }

    try {
      const response = await axios.post(
        'http://localhost:8080/api/openvidu/join',
        {
          sessionName,
          nickname, // 사용자가 지정한 닉네임 전달
        }
      );

      const { token } = response.data;
      const ov = new OpenVidu();
      const newSession = ov.initSession();

      await newSession.connect(token);
      setSession(newSession);
    } catch (error) {
      console.error('세션 참가 중 오류 발생', error);
      alert('방이 가득 찼거나 오류가 발생했습니다.');
    }
  };

  return (
    <div className="flex flex-col items-center justify-center min-h-screen bg-gray-100 p-6">
      <h1 className="text-2xl font-bold mb-4">방 제목: {sessionName}</h1>
      <p className="mb-2">현재 참가자 수: {userCount} / 5</p>

      <input
        type="text"
        placeholder="닉네임 입력"
        value={nickname}
        onChange={(e) => setNickname(e.target.value)}
        className="w-64 px-3 py-2 border border-gray-300 rounded-md mb-4"
      />

      <button
        onClick={joinSession}
        className="bg-blue-500 hover:bg-blue-600 text-white px-4 py-2 rounded-lg shadow-md"
      >
        입장
      </button>

      {session && (
        <p className="text-green-500 mt-4">세션에 성공적으로 연결됨!</p>
      )}
    </div>
  );
};

export default DebateRoom;
